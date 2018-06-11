package com.higgs.usercase

import java.util.regex.Pattern

import com.hankcs.hanlp.HanLP
import io.vertx.core.json.JsonObject
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * Created by Jerry on 2017/7/24.
  */
object ResumeTokenizer {

  val conf = new SparkConf().setAppName("token").setMaster("local")
  val sc = new SparkContext(conf)

  def filterChinese(str: String) : Boolean =  {
    val regEx1 = "[\\u4e00-\\u9fa5]+"
    val regEx2 = "[\\uFF00-\\uFFEF]+"
    val regEx3 = "[\\u2E80-\\u2EFF]+"
    val regEx4 = "[\\u3000-\\u303F]+"
    val regEx5 = "[\\u31C0-\\u31EF]+"
    val p1 = Pattern.compile(regEx1)
    val p2 = Pattern.compile(regEx2)
    val p3 = Pattern.compile(regEx3)
    val p4 = Pattern.compile(regEx4)
    val p5 = Pattern.compile(regEx5)
    val m1 = p1.matcher(str)
    val m2 = p2.matcher(str)
    val m3 = p3.matcher(str)
    val m4 = p4.matcher(str)
    val m5 = p5.matcher(str)
    if (m1.find() || m2.find() || m3.find() || m4.find() || m5.find()) {
      return true
    } else {
      return false
    }
  }

  // 分词：
  def  token(args: Array[String]) = {

    val Array(src, termPath) = args

    val data = sc.textFile(src).map(_.split("\t"))

    val effData = data.filter(x => x.length == 2).flatMap {x =>
      val lb = new ListBuffer[(String, Int)]
      lb.+=((x(0), x(1).toInt))
      lb
    }.cache()

    val errorData = data.filter(x => x.length != 2)

    val termtotal = data.count()

    val error = errorData.count()


    val filter = effData.filter(x => x._2 >= 10)
      .map(x => x._1 + "\t" + x._2)

    val terms = filter.count()

    filter.repartition(1).saveAsTextFile(termPath)


    println("term ERROR -> " + error)
    println("terms -> " + terms)
    println("termtotal -> " + termtotal)
  }

  def tfidf (tf: Long, df: Long) : (Double, Double, Double) = {

    val docs: Long = 1234102L
    val terms:Long = 4024515618L

    val tf_prob = Math.log(tf) / Math.log(terms)

    val df_prob = Math.log(1 + docs / df)

    (tf_prob, df_prob, tf_prob * df_prob)

  }

  def calTFandDF (args: Array[String]) = {

    // val Array(src, termPath, termDict, termTotal, docTotla) = args

    val tf = "/Users/devops/workspace/shell/resume/token/tokenIm"

    val df = "/Users/devops/workspace/shell/resume/token/token"

    var res = "/Users/devops/workspace/shell/resume/token/dict"

    val tfData = sc.textFile(tf).map(_.split("\t")).filter(x => x.length ==2)
      .flatMap {x =>
        val lb = new ListBuffer[(String, Long)]
        lb.+=((x(0), x(1).toLong))
        lb
      }.filter(x => x._2 >= 10).cache()

    val tfcount = tfData.count()


    val dfData =  sc.textFile(df).map(_.split("\t"))
      .flatMap {x =>
        val lb = new ListBuffer[(String, Long)]
        lb.+=((x(0), x(1).toLong))
        lb
      }.cache()


    val dfcount = dfData.count()

    val join = tfData.leftOuterJoin(dfData)
      .map(x => (x._1 , x._2._1, x._2._2.getOrElse(1L))).map {x =>
      val jsonObject = new JsonObject()
      val r = tfidf(x._2, x._3)
      jsonObject.put("term", x._1)
      jsonObject.put("tf", x._2)
      jsonObject.put("df", x._3)
      jsonObject.put("tf_prob", r._1)
      jsonObject.put("df_prob", r._2)
      jsonObject.put("prob", r._3)
      // jsonObject
      x._1 + "\t" + x._2 + "\t" + x._3 + "\t" + r._1 + "\t" + r._2 + "\t" + r._3
    }.repartition(1)
      .saveAsTextFile(res)


    println("tf count -> " + tfcount)
    println("df count -> " + dfcount)


  }

  def main(args: Array[String]): Unit = {
    // 分词
    // token(args)
    calTFandDF(args)

  }

}

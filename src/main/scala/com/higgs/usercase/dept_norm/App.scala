package com.higgs.usercase.dept_norm

import com.higgs.util.Utils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
// import com.higgs.web.HttpServing

import scala.collection.mutable

/**
  * Hello world!
  */
object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
  // .set("spark.driver.userClassPathFirst", "true")
  // .set("spark.executor.userClassPathFirst", "true")
     .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
     .set("spark.jars.exclude", "io.netty:netty-common")
     .setMaster("local")

  val sc = new SparkContext(conf)

  val filePath = "/Users/devops/Documents/部门归一化/dept_name_resume.txt"

  def init()  = {
    sc.textFile(filePath).map(_.split("\u0001")).filter(_.length == 2)
  }

  def filter(name:String, item: String) = {
    name.equals(item) || name.equals(item.replace("部", ""))
  }

  def getFreqOfDeptDict(): Unit = {
    val rdd = init().map(x=> (x(0),x(1))).cache()
    val out = "/Users/devops/workspace/gitlab/dept_norm/dept_dict_combine"
    Utils.deleteDir(out)
    val dict = sc.textFile("/Users/devops/workspace/gitlab/dept_norm/dept_dict.txt")
      .map(_.split("\t")(1)).collect()
    println("dict size:" + dict.size)
    var result : RDD[String] = sc.emptyRDD
    for (i <- 0 until  dict.length) {
      println("index :" + i)
      val r1 = rdd.filter(x => filter(x._1, dict.apply(i)))
      .map(x=> x._2 + "\t" + x._1)
      println("r1 size:" + r1.count())
      result = result.++(r1)
    }
    result.repartition(1).saveAsTextFile(out)
  }

  def filterEndWithDept(): Unit = {
    // xxx 事业部， xxx 业务部
    val rdd = init()
    val out = "/Users/devops/workspace/gitlab/dept_norm/dept_name_filter_endwith"
    Utils.deleteDir(out)
    // 以部或者部门结尾的词
    val data = rdd.map { x =>
      val name = x(0)
      val frq = x(1).toInt
      if (frq >= 100 && frq < 150 && (name.endsWith("部") || name.endsWith("部门")
        )) {
        (name.replace("部门", "部").toLowerCase, frq)
      } else {
        ("",0)
      }
    }.filter(x => !"".equals(x._1)).distinct()
      .map { x =>
        x._1 + "\t" + x._2 + "\t" + x._1.replace("部", "")
      }.saveAsTextFile(out)
  }

  def combineDict(): Unit = {
    var dir = "/Users/devops/workspace/gitlab/dept_norm"
    val out = "/Users/devops/workspace/gitlab/dept_norm/dept_dict_combine"
    Utils.deleteDir(out)
    val d1 = dir + "/dept_name_filter_endwith_ge_1000.txt"
    val d2 = dir + "/dept_dict_kaka.txt"
    val d3 = dir + "/dept_name_filter_endwith_500_1000.txt"
    val d4 = dir + "/dept_name_filter_endwith_250_500.txt"
    val d5 = dir + "/dept_name_filter_endwith_150_250.txt"
    val d6 = dir + "/dept_name_filter_endwith_100_150.txt"
    val r1 = sc.textFile(d1).map(_.split("\t")).map(x=> (x(0),x(1).toInt))
    var r2 = sc.textFile(d2).map(_.split("\t")).map(x=> (x(1),100))
    var r3 = sc.textFile(d3).map(_.split("\t")).map(x=> (x(0),x(1).toInt))
    var r4 = sc.textFile(d4).map(_.split("\t")).map(x=> (x(0),x(1).toInt))
    var r5 = sc.textFile(d5).map(_.split("\t")).map(x=> (x(0),x(1).toInt))
    var r6 = sc.textFile(d6).map(_.split("\t")).map(x=> (x(0),x(1).toInt))
    r1.++(r2).++(r3).++(r4).++(r5).++(r6).distinct()
      .reduceByKey(_ + _)
      .sortBy(_._2, ascending = false)
      .map{ x =>
      x._2 + "\t" + x._1+ "\t" + x._1.replace("部", "")
    }.repartition(1)
      .saveAsTextFile(out)
  }

  def checkFaileFrq(): Unit = {
    var dir = "/Users/devops/workspace/gitlab/dept_norm"
    val out = "/Users/devops/workspace/gitlab/dept_norm/dept_dict_failed"
    Utils.deleteDir(out)
    var d = dir + "/failed_normed/1_dodistinct_dept_norm/*"
    val r = sc.textFile(d).map(x =>(x.split("\t")(0).toLowerCase,1))
      .reduceByKey(_ + _ )
      .sortBy(_._2, ascending = false).repartition(1)
      .saveAsTextFile(out)
  }

  def main(args: Array[String]): Unit = {
    // filterEndWithDept()
    combineDict()
    // checkFaileFrq()
    // getFreqOfDeptDict()
  }

}

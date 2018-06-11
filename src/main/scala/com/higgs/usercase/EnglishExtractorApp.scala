package com.higgs.usercase

import java.io.{FileOutputStream, File => JFile}
import java.util.regex.Pattern

import io.vertx.core.json.JsonObject
import org.apache.spark.{SparkConf, SparkContext}
import org.jsoup.{Connection, Jsoup}

import scala.collection.mutable.ListBuffer

/**
  * Hello world!
  */
object EnglishExtractorApp {

  //根据中文unicode范围判断u4e00 ~ u9fa5不全
  def isChinese(str: String) : Int =  {
    val regEx1 = "[\\u4e00-\\u9fa5]+"
    val  regEx2 = "[\\uFF00-\\uFFEF]+"
    val regEx3 = "[\\u2E80-\\u2EFF]+"
    val  regEx4 = "[\\u3000-\\u303F]+"
    val  regEx5 = "[\\u31C0-\\u31EF]+"
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
    if (m1.find() || m2.find() || m3.find() || m4.find() || m5.find())
      return 1
    else
      return 0
  }

  /**
    * 判断是不是英文字母
    * @param charaString
    * @return
    */
  def isEnglish(charaString: String) :Int =  {
    if (charaString.matches("^[a-zA-Z]*")) {
      return 1
    } else  {
      return 0
    }
  }

  /**
    * 中文所占的比例
    */
  def chinesePercent(context:String):Double =  {
    var counter = 0
    val formatString = filterSpecialSymbol(context)
    val length = formatString.length()
    for ( i <- 0 until  length) {
      val indexItem = String.valueOf(formatString.charAt(i))
      val result = isChinese(indexItem)
      counter += result
    }
    return counter / (1.0 * length)
  }

  def englishPercent( context: String): Double =  {
    var counter = 0
    val formatString = filterSpecialSymbol(context)
    val length = formatString.length()
    for (i <- 0 until length) {
      val indexItem = String.valueOf(formatString.charAt(i))
      val result = isEnglish(indexItem)
      counter += result
    }
    return counter / (1.0 * length)
  }

  def filterSpecialSymbol(org:String) :String = {
    val regEx = "[`~!@#$%^&*()+=|{}':',//[//]" +
      ".<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\]\\[―•▪]"
    val p = Pattern.compile(regEx)
    val m = p.matcher(org)
    m.replaceAll("").trim()
  }

  val conf = new SparkConf().setAppName("english")
     .setMaster("local")

  val sc = new SparkContext(conf)

  val fileRoot = "/home/idmg/shell/resume/english_resume"

  // val ssc = new StreamingContext(sc,Seconds(10))

  def saveResumeFile(docId: String): Unit = {
    var fileOutPut:FileOutputStream = null
    try {
      val response = Jsoup.connect("http://hg005:20199/raw/" + docId)
        .method(Connection.Method.GET)
        .ignoreContentType(true)
        .execute()
      val fileName = docId + "_" + response.headers().get("X-Filename").toString
      fileOutPut = new FileOutputStream(new JFile(fileRoot + "/" + fileName))
      fileOutPut.write(response.bodyAsBytes)
    } catch {
      case e: Exception =>
    } finally {
      if (fileOutPut != null) {
        fileOutPut.close()
      }
    }
  }

  def isEnglishResume(json: JsonObject): Boolean =  {
    var isEng = false
    val originResume = json.getString("originResumeContent", "")
    val source = json.getInteger("source", 1)
    if (!"".equals(originResume) && source != 7) {
        val percent = chinesePercent(originResume)
        val engPercent = englishPercent(originResume)
        if (percent < 0.1 && engPercent > 0.70) {
          isEng = true
        }
    }
    isEng
  }

  def main(args: Array[String]): Unit = {

    val Array(src, dst, isRaw) = args
    val data = sc.textFile(path = src).map(_.split("\t")).filter(_.length == 2)
    if ("raw".equals(isRaw)) {
      data
        // .filter{x => isEnglishResume(new JsonObject(x(1)))}
        .foreach { x =>
        val docid = x(0)
        val json = new JsonObject(x(1))
        if (!"".equals(docid)) {
          saveResumeFile(docid)
        }
      }
    } else if ("origin".equals(isRaw)) {
      data.filter{x => isEnglishResume(new JsonObject(x(1)))}.flatMap { x =>
        val lb = new ListBuffer[String]
        val docid = x(0)
        val json = new JsonObject(x(1))
        val result = new JsonObject()
        val originResume = json.getString("originResumeContent", "")
        val percent = chinesePercent(originResume)
        val engPercent = englishPercent(originResume)
        result.put("originResumeContent", originResume)
        result.put("chinesePercent", percent)
        result.put("engPercent", engPercent)
        lb.+=(docid + "\t" + result)
        lb
      }.saveAsTextFile(dst)
    } else {
      println("finished not done!!")
    }
  }

}

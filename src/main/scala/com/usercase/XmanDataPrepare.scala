package com.usercase

import java.io.{File => JFile}

import fastparse.utils.Utils
import io.vertx.core.json.{JsonArray, JsonObject}
import org.apache.spark.{SparkConf, SparkContext}
import web.client.SearchHttpClient

import scala.collection.mutable.ListBuffer
import scala.reflect.io.File
import scala.util.control.Breaks



/**
  * Created by Jerry on 2017/6/6.
  */
object XmanDataPrepare {

  val searchHttpClient = new SearchHttpClient()

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro").setMaster("local"))

  val savaFileDir = "/Users/devops/workspace/shell/web/search"

  val dataDir = "file:///Users/devops/workspace/gitlab/idmg/resume_extractor/src/cc"

  var counter = 0

  var hitCounter = 0

  val timeLb = new ListBuffer[Int]

  val tookLb = new ListBuffer[Int]

  def loadData(file:String, pos: Int)  = {

    sc.textFile(file).map(_.split("\t")(pos))

  }

  val result = new ListBuffer[String]

  def formatQuery(array: Array[String], checkSting: String) = {
    array.foreach { y =>
      val quary = y + " " + checkSting
      var resultFormat = "{\"query\":\""+ quary +"\",\"pageSize\":10,\"page\":0}"
      result.+=(resultFormat)
      if (result.length % 100 == 0) {
        resultFormat = "{\"query\":\""+ quary +"\",\"pageSize\":20,\"page\":11}"
        result.+=(resultFormat)
      }
    }

  }

  def clearVar = {
    result.clear()
    tookLb.clear()
    timeLb.clear()
    counter = 0
    hitCounter = 0
  }

  def sendRequest (des:String) = {

    val saveFilePath = savaFileDir + "/query_xmam_data" + "_" + des

    new File(new JFile(saveFilePath)).deleteRecursively()


    val resultData = sc.parallelize(result)

    resultData.saveAsTextFile("file://" + saveFilePath)

    println("result_size => " + result.size)

    val url = "http://172.16.52.103:20202/search/api"

    // "http://211.152.62.99:20202/search/api" // online

    val startedTime = System.currentTimeMillis()

    resultData.foreach { x=>
      // println("req ->" + x + ", res -> " + string)
      val string = searchHttpClient.postAndReturnString(searchHttpClient.getClient(), url, x)
      try {
        val json = new JsonObject(string)
        val msg = json.getString("msg", "failed")
        val status = json.getInteger("status", -1)
        val hits = json.getJsonObject("data").getJsonArray("hits", null)
        val time = json.getInteger("time", -1)
        val took = json.getInteger("took", -1)
        timeLb.+=(time)
        tookLb.+=(took)

        if (status == 0) {
          counter += 1
        }

        if (hits != null) {
          hitCounter += 1
        }
        if (hitCounter != 0 && hitCounter % 100 == 0){
          println("req ->" + x + ", res -> " + string)
        }
      } catch {
        case e: Exception =>
          println(e.getCause)
      }

    }

    val endedTime = System.currentTimeMillis()

    println("result_size => " + result.size + ", counter -> " + counter + ", hitCounter -> " +
      hitCounter)

    print("qp time min:" + timeLb.min + ", time max:" + timeLb.max)

    print("es took min:" + tookLb.min + ", took max:" + tookLb.max)

    // clear var
    clearVar

  }


  def main(args: Array[String]): Unit = {

    val schoolFile = s"$dataDir/school_dict.txt"

    val jobTitle = s"file:///Users/devops/workspace/shell/jd/result/*/*"

    val majorFile = s"$dataDir/major_dict.txt"

    val cityFile = s"$dataDir/city_dict.txt"

    val schoolData = loadData(schoolFile, 2).randomSplit(Array(0.9,0.1), seed = 123L)(1)

      // .collect()

    val jobData = loadData(jobTitle, 0).randomSplit(Array(0.9,0.1), seed = 456L)(1)

      // .collect()

    val majorData = loadData(majorFile, 1).randomSplit(Array(0.9,0.1), seed = 789L)(1)

      // .collect()

    val cityData = loadData(cityFile, 1).randomSplit(Array(0.9,0.1), seed = 147L)(1)

    // .collect()

    schoolData.foreach { x =>
      val array = Array("java工程师", "人事经理", "产品经理", "上海", "1年以上", "男", "女")
      val school = x
      formatQuery(array, school)
    }

    sendRequest("school")

    val resultRDD = jobData.foreach{ x=>

      val array = Array("清华大学", "北京", "上海", "1年以上", "3年以下", "男", "女")
      val job = x
      formatQuery(array, job)
    }

    sendRequest("job")

    majorData.foreach { x =>
      val array = Array("北京", "1年以上", "女", "男" ,"211", "985", "F4", "男", "女")
      val major = x
      formatQuery(array, major)
    }

    sendRequest("major")

    cityData.foreach { x=>
      val array = Array("java工程师", "人事经理", "产品经理", "1年以上", "材料与工程", "男", "女")
      val city = x
      formatQuery(array, city)
    }

    sendRequest("city")

  }

}

package com.higgs.usercase.ai_pipeline

import java.util.Properties

import org.apache.spark.sql._
import com.higgs.util.Utils
import io.vertx.core.json.JsonObject
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SQLContext}

import scala.util.control.Breaks

/**
  * User: JerryYou
  *
  * Date: 2018-10-25
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object Candi {
  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  //数据库url地址
  val url = "jdbc:mysql://xxx/ai_pipeline"
  //表名
  val table = "candidate_data"
  val properties = new Properties()
  properties.setProperty("user","xxx")
  properties.setProperty("password","xxx")

  def readMysql(): Unit = {
    val df = sqlContext.read
      .jdbc(url, table, properties)
    df.show(10)
  }

  def getevaluate(row:Row) = {
    var id = row.getLong(0)
    var contentBytes = row.get(1).asInstanceOf[Array[Byte]]
    var content = new JsonObject(new String(contentBytes))
    var eval = content.getJsonObject("basic", new JsonObject()).getString("evaluate", "-")
    new JsonObject().put("resume_id", id).put("evaluate", eval).encode()
  }

  def dumpData(): Unit = {
    var out = "/Users/devops/Documents/业务系统-ai_pipeline/resume_evaluate"
    Utils.deleteDir(out)
    val df = sqlContext.read
      .jdbc(url, table, properties)
    println("schema:" + df.schema)
    var result:RDD[String] = sc.emptyRDD
    // 204,  541803
    var totalmax = 541803
    var min = 204
    var max = 1204
    var break = new Breaks()
    break.breakable {
      while (true) {
        var temp = df.select("resume_id", "content", "id")
          .filter("id >=" +  min)
          .filter("id <" + max)
          .map{ row => getevaluate(row)}
        result = result.++(temp)
        min = max
        println("min:" + min)
        max = max + 1000
        if (min > totalmax) {
          break.break()
        }
        if (result.count() > 10000) {
          result.saveAsTextFile(out + "_"+ min)
          result = sc.emptyRDD
        }
      }
    }
    if (result.count() > 0) {
      result.saveAsTextFile(out + "_"+ min)
    }
  }


  def parse(in:String, path:String) = {
    Utils.deleteDir(path)
    sc.textFile(path).map(new JsonObject(_))
      .map(x=> x.getJsonObject("resumeJson"))
      .map(x => x.getJsonObject("\\\"basic\\\"").getString("\\\"name\\\""))
      .saveAsTextFile(path)

  }

  def main(args: Array[String]): Unit = {
    // dumpData()
    var in = "/Users/devops/workspace/shell/data/filter_resume_evaluate.txt"
    var path = "/Users/devops/workspace/shell/data/evaluate_parse"
    parse(in, path)
  }

}

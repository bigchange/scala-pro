package com.higgs.usercase.ai_pipeline

import java.util.Properties

import io.vertx.core.json.JsonObject
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SaveMode}
/**
  * User: JerryYou
  *
  * Date: 2018-11-26
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  *
  *
  */
case class Project(id:Int,project_id:Int,project_detail:String,project_usn:Int, updated_at:Int,
                   origin_created_at:Int, origin_updated_at:Int,project_status:Int,
                   publish_type:Int)

object AiProjectImport {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  //数据库url地址
  val url = "jdbc:mysql://172.16.52.52/ai_pipeline_dev?characterEncoding=utf8"
  //表名
  val table = "project_detail"
  val properties = new Properties()
  properties.setProperty("user","pipeline")
  properties.setProperty("password","Pipeline2018@higgs")


  def main(args: Array[String]): Unit = {
    var src = "/Users/devops/Documents/ai_project.json"
    val data = sqlContext.sparkContext.textFile(src).map { x =>
      var project = new JsonObject(x)
      var project_id = project.getInteger("id", 0)
      Project(0,project_id, x, 0,0,0,0,0,0)
    }
    var df = sqlContext.createDataFrame(data)
    println("count:", df.count())

    df.write.mode(SaveMode.Overwrite)
      .jdbc(url, table, properties)


  }

}

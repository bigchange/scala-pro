package com.higgs.usercase.url_list_lieluo

import java.text.SimpleDateFormat
import java.util.Date

import io.vertx.core.json.JsonObject
import java.io

import org.apache.spark.{SparkConf, SparkContext}

import scala.reflect.io.File

object Main {
  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def getDataFormator() = {
     new SimpleDateFormat("yyyy-MM-dd")
  }

  def formatDate(second:Long) = {
    val date = new Date()
    date.setTime(second * 1000)
    getDataFormator().format(date)
  }

  def deleteFile(file: String) = {
    new File(new io.File(file)).deleteRecursively()
  }

  val src = "/Users/devops/workspace/shell/click_data/lieluobo/2017-12-01--2017-12-15.txt"
  val out = "/Users/devops/workspace/shell/click_data/lieluobo_count"
  def id_3(id: Int) = {
    val data = sc.textFile(src).map(x=> new JsonObject(x))
      .filter(x => "time_c".equals(x.getString("action", "")))
      .map { x =>
        val timeAt = x.getJsonObject("timeAt")
        val dateFormat = formatDate(timeAt.getString("seconds").toLong)
        (id + "\t" + dateFormat, 1)
      }.reduceByKey(_ + _)
          .sortBy(_._1)
      .map(x => x._1 + "\t" + x._2)
      .repartition(1)

    val outDir = out + "/" + id
    deleteFile(outDir)
    data.saveAsTextFile(outDir)
  }

  def id_1(id: Int): Unit = {
    // 埋点id = 1
    val data = sc.textFile(src).map(x=> new JsonObject(x))
      .filter(x => "click_c_recommendProjectForResume".equals(x.getString("action", "")))
      .map { x =>
        val timeAt = x.getJsonObject("timeAt")
        val dateFormat = formatDate(timeAt.getString("seconds").toLong)
        (id + "\t" + dateFormat, 1)
      }.reduceByKey(_ + _)
      .sortBy(_._1)
      .map(x => x._1 + "\t" + x._2)
      .repartition(1)

    val outDir = out + "/" + id
    deleteFile(outDir)
    data.saveAsTextFile(outDir)

  }

  def main(args: Array[String]): Unit = {
    id_3(3)
    id_1(1)
  }

}

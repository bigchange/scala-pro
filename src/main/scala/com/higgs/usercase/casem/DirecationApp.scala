package com.higgs.usercase.casem

import java.util.Properties

import com.higgs.util.Utils
import io.vertx.core.json.{JsonArray, JsonObject}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * User: JerryYou
  *
  * Date: 2018-08-20
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object DirecationApp {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  //数据库url地址
  // val url
  //表名
  val table = "casem_case"
  //创建Properties 添加数据库用户名和密码
  val properties = new Properties()
  properties.setProperty("user","casem")
  properties.setProperty("password","Casem123@")

  def readMysql(url:String): Unit = {
    val df = sqlContext.read
      .jdbc(url, table, properties)
    df.show(10)
  }

  def dumpData(url:String): Unit = {
    var out = "/Users/devops/Documents/新的标注系统/tag_data/direction_tag"
    Utils.deleteDir(out)
    val df = sqlContext.read
      .jdbc(url, table, properties)
    println("schema:", df.schema)
    val result = df
      .filter("tag_status = 2")
      .filter("type = 10")
      .filter("user_id != 28")
      .filter("user_id != 29")
    result.map{ row =>
     row.getString(1) + "\t" + row.getString(3) + "\t" + row.getString(4) + "\t" + row.getInt(9) +
       "\t" + row.getInt(10) + "\t" + row.getString(13)
    }.saveAsTextFile(out)
  }

  def dumpJdTagData(url: String,out:String, pass:String) ={
    var pass = "/Users/devops/Documents/新的标注系统/tag_data/jd_keyword_tag/export_20190214_passed"
    var out = "/Users/devops/Documents/新的标注系统/tag_data/jd_keyword_tag/export_20190214"
    Utils.deleteDir(out)
    Utils.deleteDir(pass)
    val df = sqlContext.read
      .jdbc(url, table, properties)
    println("schema:", df.schema)
    val result = df
      .filter("type = 11")
      .filter("user_id != 27")
      .filter("user_id != 29")
      .filter("user_id != 30")
      .filter("user_id != 31")
      .filter("user_id != 32")
      // .filter("updated_at >= 1546790400")

    println("count:", result.count())
    result.filter("tag_status = 2").map{ row =>
      // id + tag_p
      // text
      var text = new JsonObject(row.getString(1))
      new JsonObject(row.getString(4)).put("id", row.getString(0))
        .put("origin_text", text.getString("content"))
        .put("user_id", row.getInt(9))
        .encode()

    }.saveAsTextFile(out)

    result.filter("tag_status = 4").map{ row =>
      var text = new JsonObject(row.getString(1))
      // id + tag_p
      new JsonObject(row.getString(4)).put("items", new JsonArray()).put("id", row.getString(0))
        .put("origin_text", text.getString("content"))
        .put("user_id", row.getInt(9))
        .encode()
    }.saveAsTextFile(pass)


  }

  def filterTagCompany(): Unit = {
    var input = "/Users/devops/Documents/新的标注系统/tag_data/jd_keyword_tag/export_20190214"
    var companyTag = "/Users/devops/Documents/新的标注系统/tag_data/jd_keyword_tag/export_tag_company"
    Utils.deleteDir(companyTag)

    var tagData = sc.textFile(input).flatMap{ x =>
      var tagP = new JsonObject(x)
      var items = tagP.getJsonArray("items")
      var list = new ListBuffer[(String, String)]()
      for (i <- 0 until items.size()) {
        var tagInfo = items.getJsonObject(i)
        var tag = tagInfo.getString("tag")
        var name = tagInfo.getString("name")
        list.+=((tag,name))
      }
      list
    }

    // tag == company
    tagData.filter{_._1.equals("company")}.map(x => x._1 + "," + x._2).saveAsTextFile(companyTag)
  }

  def main(args: Array[String]): Unit = {
    var url = args(0) // db url
    // dumpJdTagData(url)
    // filterTagCompany()

  }

}

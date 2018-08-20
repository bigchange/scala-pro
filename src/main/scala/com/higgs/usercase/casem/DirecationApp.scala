package com.higgs.usercase.casem

import java.util.Properties

import com.higgs.util.Utils
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

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
  val url = "jdbc:mysql://172.16.52.102:3306/casem_api_go"
  //表名
  val table = "casem_case"
  //创建Properties 添加数据库用户名和密码
  val properties = new Properties()
  properties.setProperty("user","casem_go")
  properties.setProperty("password","cjyou2017")

  def readMysql(): Unit = {
    val df = sqlContext.read
      .jdbc(url, table, properties)
    df.show(10)
  }

  def dumpData(): Unit = {
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

  def main(args: Array[String]): Unit = {
    dumpData()
  }

}

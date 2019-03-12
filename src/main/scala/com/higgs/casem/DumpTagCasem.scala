package com.higgs.casem

import java.util.Properties

import com.higgs.kb_system.DumpKBData.spark
import com.higgs.util.Utils
import io.vertx.core.json.{JsonArray, JsonObject}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

/**
  * User: JerryYou
  *
  * Date: 2019-03-06
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object DumpTagCasem {

  val spark = SparkSession.builder()
    .master("local")
    // .config("driver-class-path", "path/mysql-connector-java-8.0.15.jar")
    .getOrCreate()
  var sqlContext = spark.sqlContext

  spark.sparkContext.setLogLevel("WARN")

  //数据库url地址
  // val url = jdbc:mysql://xxx:3306/casem_api_go
  //表名
  // val table = "casem_case"
  //创建Properties 添加数据库用户名和密码
  val properties = new Properties()

  def setProperty(key:String, value:String): Unit = {
    properties.setProperty(key,value)
    properties.setProperty(key,value)
  }

  def dumpTagData(url: String,ftype:String,tagStatus:String,out:String) ={
    val dir = out + "/dump-out"
    Utils.deleteDir(dir)
    val df = sqlContext.read
      .jdbc(url, "casem_case", properties)
    println("schema:", df.schema)
    val filterExp: String = "tag_status = " + tagStatus
    val filterExp2: String = "type = " + ftype
    val result = df
       .filter(filterExp)
       .filter(filterExp2)
    // .filter("updated_at >= 1546790400")

    println("count:", result.count())
    result.rdd.map { row =>
      // text
      var text = new JsonObject(row.getString(1))
      // id + tag_p
      new JsonObject(row.getString(4)).put("id", row.getString(0))
        .put("origin_text", text.getString("content"))
        .put("user_id", row.getInt(9))
        .put("category", row.getString(16))
        .encode()
    }.saveAsTextFile(dir)

  }

  def main(args: Array[String]): Unit = {

    val (url, user, ps, ftype, ts, out) = (args(0), args(1), args(2), args(3),args(4), args(5))
    setProperty("user", user)
    setProperty("password", ps)
    dumpTagData(url, ftype, ts, out)
  }
}

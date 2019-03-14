package com.higgs.ai_pipleine

import java.util.Properties

import com.higgs.util.Utils
import io.vertx.core.json.JsonObject
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.BinaryType

/**
  * User: JerryYou
  *
  * Date: 2019-03-14
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object ai_pipeline {

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

  def dumpTagData(url: String,out:String) ={
    val dir = out + "/dump-out"
    Utils.deleteDir(dir)
    val df = sqlContext.read
      .jdbc(url, "order_data", properties)
      // .filter("updated_at > 1552555202883")
      .cache()
    println("schema:", df.schema)
    // .filter("updated_at >= 1546790400")
    // println(df.rdd.first().get(14).asInstanceOf[BinaryType].json)
    println("count:", df.count())

    var result = df.rdd
        .filter{ x=>
          // content
          val text = new JsonObject(new String(x.get(14).asInstanceOf[Array[Byte]]))
          val resumeMatch = text.getInteger("resumeProjectMatch", 0)
          if (resumeMatch > 0) {
            true
          } else {
            false
          }
        }

    println("filter count:", result.count())
    result.map { row =>
      new JsonObject().put("order_no", row.getLong(8))
        .encode()
    }.saveAsTextFile(dir)

  }

  def recover_data(input:String): Unit = {

  }

  def main(args: Array[String]): Unit = {

    val (url, user, ps, out) = (args(0), args(1), args(2), args(3))
    setProperty("user", user)
    setProperty("password", ps)
    dumpTagData(url, out)
  }
}


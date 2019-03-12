package com.higgs.kb_system

import java.util.Properties

import com.higgs.util.Utils
import io.vertx.core.json.JsonObject
import org.apache.spark.sql.SparkSession

/**
  * User: JerryYou
  *
  * Date: 2019-03-06
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object DumpKBData {

  val spark = SparkSession.builder()
    .master("local")
    .getOrCreate()

  var sqlContext = spark.sqlContext

  spark.sparkContext.setLogLevel("WARN")

  //数据库url地址
  // val url = jdbc:postgresql://xxx:5432/casem_api_go
  //表名
  // val table = "casem_case"
  //创建Properties 添加数据库用户名和密码
  val properties = new Properties()

  def setProperty(key:String, value:String): Unit = {
    properties.setProperty(key,value)
    properties.setProperty(key,value)
  }

  def dumpData(url: String,value:String,out:String) ={
    val dir = out + "/dump-out"
    Utils.deleteDir(dir)
    val ids = sqlContext.read
      .jdbc(url, "kb_attr", properties)
    println("schema:", ids.schema)
    val filterExp: String = "name = 'entity_type'"
    val filterExp2: String = "int_value = " + value
    val result = ids
       .filter(filterExp)
       .filter(filterExp2)
       .select("entity_id")

    val names = sqlContext.read
      .jdbc(url, "kb_entity", properties)

    val entities = result.join(names).where(ids("entity_id") === names("id"))
      .select(names("name"))
        .repartition(1)
        .rdd

    println("count:", entities.count())
    entities
        .map(x => x.getString(0))
        .saveAsTextFile(dir)



  }

  def main(args: Array[String]): Unit = {

    val (url, user, ps, ftype, out) = (args(0), args(1), args(2), args(3),args(4))
    setProperty("user", user)
    setProperty("password", ps)
    // setProperty("driver", "org.postgresql.Driver")
    dumpData(url, ftype, out)
  }
}

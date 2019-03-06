package com.higgs.casem

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

  val conf = new SparkConf().setAppName("boar")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)
  val spark = SparkSession.builder().getOrCreate()
  var sql = spark.sqlContext



  def main(args: Array[String]): Unit = {

  }
}

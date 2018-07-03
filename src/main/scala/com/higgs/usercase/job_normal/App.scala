package com.higgs.usercase.job_normal

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Jerry on 2017/9/14.
  */
object App {

  val conf = new SparkConf().setAppName("jobNormal")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      print("Usgae <src>")
      System.exit(-1)
    }
    val src = args(0)
    var count = sc.textFile(src).count()
    println("count:" + count)
  }

}

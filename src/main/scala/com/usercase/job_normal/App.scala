package com.usercase.job_normal

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Jerry on 2017/9/14.
  */
object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def checkCount(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/matching_result/new_sample_dict.txt"

    val count = sc.textFile(src).map(_.split("\t"))

    val c = count.map(x => ("1", x(1).toLong)).map { x =>
      var v = x._2
      if (x._2 > 5500) {
        v = 5500
      }
      ("1", v)
    }.reduceByKey(_ + _).map(x => x._2).foreach(println)

  }

  def main(args: Array[String]): Unit = {
    checkCount()

  }

}

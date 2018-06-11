package com.higgs.usercase.job_normal

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
    val src = "/Users/devops/workspace/shell/conf/mindcube/resume_info_counter/*"

    val count = sc.textFile(src).filter(!_.contains("_unk_"))
        .filter(_.split("\t").length == 2)
      .saveAsTextFile("/Users/devops/workspace/shell/conf/mindcube/sample_dict")

  }

  def checkPosition(): Unit = {
    val src = "/Users/devops/workspace/shell/conf/mindcube/resume_info_counter/*"

    val count = sc.textFile(src).filter(_.contains("_unk_"))
      .filter(_.split("\t").length == 2)
      .saveAsTextFile("/Users/devops/workspace/shell/conf/mindcube/position_dict")
  }

  def main(args: Array[String]): Unit = {
    // checkCount()
    checkPosition()

  }

}

package com.usercase.distribute

import org.apache.spark.{SparkConf, SparkContext}

object SchoolOutSideApp {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def schoolOutSideDict (): Unit = {
    var src = "/Users/devops/Downloads/海外大学.txt"
    sc.textFile(src).map(x=> x + "\t" + x).saveAsTextFile("/Users/devops/Downloads/school_outside_dict")
  }

  def main(args: Array[String]): Unit = {
    schoolOutSideDict()
  }

}

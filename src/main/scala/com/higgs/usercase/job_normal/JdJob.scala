package com.higgs.usercase.job_normal

import com.higgs.usercase.dept_norm.App.{filePath, sc}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: JerryYou
  *
  * Date: 2018-07-23
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object JdJob {

  val conf = new SparkConf().setAppName("jobNormal")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .set("spark.driver.allowMultipleContexts", "true")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def init(src: String)  = {
    sc.textFile(src).map(_.split("\t")).filter(_.length == 2)
  }
  def filterJdJob(): Unit = {
    val src = "/Users/devops/Documents/职位jd/jd_full.txt"
    val rdd = init(src)
    rdd.map { x=>
      val job = x(0)
      val feq = x(1).toInt
      if (feq > 100) {
        (job, feq)
      } else {
        ("", 0)
      }
    }.filter(x => !"".equals(x._1)).sortBy(_._2)
      .map(x=> x._1 + "\t" + x._2)
      .repartition(1)
      .saveAsTextFile("/Users/devops/Documents/职位jd/jd_filter_100")

    rdd.map { x=>
      val job = x(0)
      val feq = x(1).toInt
      if (feq > 1000) {
        (job, feq)
      } else {
        ("", 0)
      }
    }.filter(x => !"".equals(x._1)).sortBy(_._2)
      .map(x=> x._1 + "\t" + x._2)
      .repartition(1)
      .saveAsTextFile("/Users/devops/Documents/职位jd/jd_filter_1000")
  }

  def main(args: Array[String]): Unit = {
    filterJdJob()
  }

}

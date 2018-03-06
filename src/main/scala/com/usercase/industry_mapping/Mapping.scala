package com.usercase.industry_mapping

import java.io

import org.apache.spark.{SparkConf, SparkContext}

import scala.reflect.io.File


object Mapping {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def mappingIndustryCode(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/count_result/industryCodeMapping.txt"
    val des = "/Users/devops/workspace/shell/resume/count_result/industryCodeMapping_filter"

    new File(new io.File(des)).deleteRecursively()

    sc.textFile(src).filter(x => !x.startsWith("0"))
      .saveAsTextFile(des)

  }

  def main(args: Array[String]): Unit = {
    mappingIndustryCode()

  }

}

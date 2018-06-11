package com.higgs.usercase.company_id

import org.apache.spark.{SparkConf, SparkContext}

object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def countOrgId(): Unit = {
    val src = "/Users/devops/workspace/shell/e2e/sample_result_withOrgID"
    sc.textFile(src).map(_.split("\t")).filter(x => x.length == 8).map { x =>
      if (x(1).toInt > 0 ) {
        (1, 1)
      } else {
        (0, 1)
      }
    }.reduceByKey(_ + _).repartition(1)
        .saveAsTextFile("/Users/devops/workspace/shell/e2e/counter_sample_result_withOrgID")
  }


  def main(args: Array[String]): Unit = {
    countOrgId()
  }

}

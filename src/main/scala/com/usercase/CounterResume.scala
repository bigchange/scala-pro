package com.usercase

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Jerry on 2017/6/9.
  */
object CounterResume {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro")
    .set("spark.driver.maxResultSize", "2g")
    .setMaster("local")
  )

  def filter(item: String) = {
    item.forall(Character.isDigit)
  }
  def main(args: Array[String]): Unit = {

    val file = "file:///Users/devops/workspace/shell/resume/candidate/candidateIntegrity_result" +
      "/major/*"
    val data = sc.textFile(file).map(_.split("\t")).filter(x => x.length == 2 && x(1) != null &&
      filter(x(1))).map(x => (x(0), x(1).toInt))
      .sortBy(_._2, ascending = false).map(x => x._1 + "\t" + x._2).repartition(1)
      .saveAsTextFile("file:///Users/devops/workspace/shell/resume/candidate" +
        "/candidateIntegrity_result/major/sort")


  }

}

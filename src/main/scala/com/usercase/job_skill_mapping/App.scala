package com.usercase.job_skill_mapping

import org.apache.spark.{SparkConf, SparkContext}

object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def titleLoad()  = {

    val src = "/Users/devops/Downloads/skill_mapping/title.txt"

    sc.textFile(src).zipWithIndex().map(x=> (x._2, x._1))


  }

  def skillLoad() = {
    var src = "/Users/devops/Downloads/skill_mapping/skill.txt"
    sc.textFile(src).zipWithIndex().map(x=> (x._2, x._1))
  }

  def main(args: Array[String]): Unit = {

    val title = titleLoad()
    val skill = skillLoad()

    title.leftOuterJoin(skill).map(x => (x._2._1, x._2._2.get)).sortByKey(ascending = false)
      .map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("/Users/devops/Downloads/skill_mapping_result")
  }

}

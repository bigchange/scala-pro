package com.usercase.case_go

import org.apache.spark.{SparkConf, SparkContext}

object ResumeProjectMappingMain {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def mapping(): Unit = {
    var dir = "/Users/devops/Downloads/resume_project_id/20180321"
    var classFile = dir + "/class.txt"
    var resumeIdFile = dir + "/resume_id.txt"
    var projectIdFile = dir + "/project_id.txt"
    var result = dir + "/result"

    var classData = sc.textFile(classFile).zipWithIndex().map(x => (x._2, x._1))
    var resumeIdData = sc.textFile(resumeIdFile).zipWithIndex().map(x => (x._2, x._1))
    var projectIdData = sc.textFile(projectIdFile).zipWithIndex().map(x => (x._2, x._1))
    classData.leftOuterJoin(resumeIdData).leftOuterJoin(projectIdData)
        .sortBy(x => x._1, ascending = true)
      .map(x => (x._1+1) + "," + x._2._1._1 + "," + x._2._1._2.get + "," + x._2._2.get)
      .repartition(1).saveAsTextFile(result)
  }

  def cwRefuseMapping(): Unit = {
    var dir = "/Users/devops/Downloads/resume_project_id/20180411"
    // var classFile = dir + "/class.txt"
    var resumeIdFile = dir + "/resume_id.txt"
    var projectIdFile = dir + "/project_id.txt"
    var result = dir + "/result"

    // var classData = sc.textFile(classFile).zipWithIndex().map(x => (x._2, x._1))
    var resumeIdData = sc.textFile(resumeIdFile).zipWithIndex().map(x => (x._2, x._1))
    var projectIdData = sc.textFile(projectIdFile).zipWithIndex().map(x => (x._2, x._1))
    resumeIdData.leftOuterJoin(projectIdData)
      .sortBy(x => x._1, ascending = true)
      .map(x => (x._1+1) + "," + "HR拒绝" +  "," + x._2._1 + "," + x._2._2.get)
      .repartition(1).saveAsTextFile(result)
  }

  def main(args: Array[String]): Unit = {
    // mapping()
    cwRefuseMapping()
  }
}

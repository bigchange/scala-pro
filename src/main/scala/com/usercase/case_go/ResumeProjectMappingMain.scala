package com.usercase.case_go

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object ResumeProjectMappingMain {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  val hrPass = Array(22,42,51,31,32,21,41,43,52,33)
  val hrRefuse = Array(13)

  def mapping(): Unit = {
    var dir = "/Users/devops/Downloads/resume_project_id/20180413"
    var classFile = dir + "/class.txt"
    var resumeIdFile = dir + "/resume_id.txt"
    var projectIdFile = dir + "/project_id.txt"
    var result = dir + "/id_result"

    var classData = sc.textFile(classFile).zipWithIndex().map(x => (x._2, x._1.toInt))
      .map { item =>
        if (hrPass.contains(item._2)) {
          (item._1, "HR通过")
        } else if (hrRefuse.contains(item._2)) {
          (item._1, "HR拒绝")
        } else {
          (item._1, "None")
        }
      }
    val noneSize = classData.filter(x => "None".equals(x._2)).count()
    println("None size:" + noneSize)
    if (noneSize > 0) {
      sys.exit(-1)
    }
    var resumeIdData = sc.textFile(resumeIdFile).zipWithIndex().map(x => (x._2, x._1))
    var projectIdData = sc.textFile(projectIdFile).zipWithIndex().map(x => (x._2, x._1))
    classData.leftOuterJoin(resumeIdData).leftOuterJoin(projectIdData)
        .sortBy(x => x._1, ascending = true)
      .map(x => (x._1 + 1) + "," + x._2._1._1 + "," + x._2._1._2.get + "," + x._2._2.get)
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
     mapping()
    // cwRefuseMapping()
  }
}

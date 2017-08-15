package com.usercase

import java.io.{File, PrintWriter}
import java.util

import io.vertx.core.json.JsonObject
import org.apache.spark.{SparkConf, SparkContext}
import resume_extractor.FixResumeKey

/**
  * Created by Jerry on 2017/6/9.
  */
object JobGradesCheck {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro")
    .set("spark.driver.maxResultSize", "2g")
    .setMaster("local")
   )

  def main(args: Array[String]): Unit = {

    // val Array(jobMapPath, filePath) = args
    val  jobMapPath = "/Users/devops/workspace/scala-pro/src/resources/jobGrades/job_grade_map"
    val filePath = "/Users/devops/workspace/shell/resume/part-m-00002"
    val fixResumeKey = new FixResumeKey(jobMapPath)

    println(fixResumeKey.getJobGrade("电气工程师"))
    /*val data = sc.textFile(filePath).map(_.split("\t")).filter(_.length == 2)
    .map(_(1))
      .collect()
      .foreach { x=>
      val infoObject = new JsonObject(x)
      val workExprs = infoObject.getJsonArray("workExperiences", null)
      fixResumeKey.infoFormat(workExprs, 1)
    }*/



  }
}

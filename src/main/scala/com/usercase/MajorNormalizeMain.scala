package com.usercase

import java.io.{File, PrintWriter}

import grpc.client.MajorNormalizeClient
import org.apache.spark.{SparkConf, SparkContext}
import resume_extractor.SchoolNormalize

/**
  * Created by Jerry on 2017/6/13.
  */
object MajorNormalizeMain {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro").setMaster("local"))

  val testData = "/Users/devops/workspace/shell/resume/candidate/candidateIntegrity_result/major" +
    "/sort/part-00000"

  var lineNumber = 0

  var successCounter = 0

  var failedCounter = 0

  var noEffect = 0

  val majorNormalize  = new MajorNormalizeClient("hg005", 20298)

  def batch() = {

    // val schoolNormalizeClient = new SchoolNormalizeClient("hg005", 20599)
    new File("/Users/devops/workspace/shell/resume/candidate/candidateIntegrity_result/major/sort" +
      "/test_major").delete()
    val printer = new PrintWriter(
      new File("/Users/devops/workspace/shell/resume/candidate/candidateIntegrity_result/major/sort" +
      "/test_major"))

    val data = sc.textFile(testData)
      .collect()
      .foreach { x =>
        lineNumber += 1
      val major = x.split("\t")(0)
      if (major.length < 20) {
        // val result = schoolNormalizeClient.getNormalizedShool(x, lineNumber)
        val result = majorNormalize.majorNormalize(major)
        if (!result.equals(major)) {
          successCounter += 1
        } else {
          failedCounter += 1
        }
        printer.write(major + " -> " + result + "\n")
      } else {
        noEffect += 1
      }
    }
    printer.write("line:" + lineNumber + "\n")
    printer.write("success:" + successCounter + "\n")
    printer.write("failed:" + failedCounter + "\n")
    printer.write("noEffect:" + noEffect + "\n")
    printer.flush()
  }

  def main(args: Array[String]): Unit = {

     batch()

    // println(majorNormalize.majorNormalize("保密"))


  }

}

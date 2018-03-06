package com.usercase

import java.io.{File, PrintWriter}
import javafx.print.Printer

import grpc.client.SchoolNormalizeClient
import org.apache.spark.{SparkConf, SparkContext}
import resume_extractor.SchoolNormalize

/**
  * Created by Jerry on 2017/6/13.
  */
object SchoolNormalizeMain {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro").setMaster("local"))

  val srcOne = "/Users/devops/workspace/shell/school/school_crawler_tesoon"

  val srcTwo = "/Users/devops/workspace/shell/school/normal_school_dict.txt"

  val save = "/Users/devops/workspace/shell/school/combineSchool"

  val testData = "/Users/devops/workspace/shell/resume/candidate/candidateIntegrity_result/school"

  var lineNumber = 0

  var successCounter = 0

  var failedCounter = 0

  var noEffect = 0

  def combineSchool = {

    val data = sc.textFile(srcOne).map(_.split("\t")(0)).map(x => (x, 1))

    val data2 = sc.textFile(srcTwo).map(_.split("\t")(0)).map(x => (x, 1))

    data.++(data2).reduceByKey(_ + _).map(x => x._1 + "\t" + x._2)
      .repartition(1).saveAsTextFile(save)
  }

  def batch() = {

    // val schoolNormalizeClient = new SchoolNormalizeClient("hg005", 20599)
    val schoolNormalize  = new SchoolNormalize()
    new File("/Users/devops/workspace/shell/school/test_school").delete()
    val printer = new PrintWriter(new File("/Users/devops/workspace/shell/school/test_school"))

    val data = sc.textFile(testData)
      .collect()
      .foreach { x =>
        lineNumber += 1
      val school = x.split("\t")(0)
      if (school.length < 20) {
        // val result = schoolNormalizeClient.getNormalizedShool(x, lineNumber)
        val result = schoolNormalize.normalize(school, false)
        if (result.size() > 0) {
          successCounter += 1
        } else {
          failedCounter +=1
          printer.write(school + "-> " + result + "\n")
        }
        if (lineNumber % 1000 == 0)   {
          printer.flush()
        }
        // print("result -> " + result)
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

    val schoolNormalize  = new SchoolNormalize()
    schoolNormalize.normalize("上海大学", true)

    print("山东大 学".replaceAll("\\s*", ""))

    //  combineSchool
    // batch()


  }

}

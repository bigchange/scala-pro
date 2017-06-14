package com.usercase

import java.io.{File, PrintWriter}
import javafx.print.Printer

import org.apache.spark.{SparkConf, SparkContext}
import resume_extractor.SchoolNormalize

/**
  * Created by Jerry on 2017/6/13.
  */
object SchoolNormalizeMain {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro").setMaster("local"))

  val srcOne = "/Users/devops/workspace/shell/school/codes"

  val srcTwo = "/Users/devops/workspace/shell/school/normal_school_dict.txt_bak"

  val save = "/Users/devops/workspace/shell/school/combineSchool"


  def combineSchool = {

    val data = sc.textFile(srcOne).map(_.split("\t")(9)).map(x => (x, 1))

    data.reduceByKey(_ + _).filter(_._2 > 1).foreach(print)

    val data2 = sc.textFile(srcTwo).map(_.split("\t")(2)).map(x => (x, 1))

    data.++(data2).reduceByKey(_ + _).map(_._1).zipWithIndex().map(x => x._1 + "\t" + x._2)
      .repartition(1).saveAsTextFile(save)
  }

  def batch() = {
    val schoolNormalize  = new SchoolNormalize()

    // schoolNormalize.normalize("华中科技大学")

    val printer = new PrintWriter(new File("/Users/devops/workspace/shell/school/test_school"))

    val data = sc.textFile(save).map(_.split("\t")(0)).collect().foreach { x =>
      val result = schoolNormalize.normalize(x, false)
      if (result.size() == 0)
        printer.write(x + " -> " + result + "\n")
      printer.flush()
    }
    printer.flush()
  }

  def main(args: Array[String]): Unit = {

    // combineSchool

    // batch()

    val schoolNormalize  = new SchoolNormalize()
    schoolNormalize.normalize("中国科大", true)

  }

}

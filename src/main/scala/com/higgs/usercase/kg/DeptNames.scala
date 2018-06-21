package com.higgs.usercase.kg

import com.higgs.util.Utils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: JerryYou
  *
  * Date: 2018-06-21
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object DeptNames {

  val conf = new SparkConf().setAppName("deptNames")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def filterDeptName(): Unit = {
    val dir = "/Users/devops/workspace/shell/resume/dept_name"
    val src = dir + "/dept_names.txt"
    val out = dir + "/filter-out"
    Utils.deleteDir(out)
    sc.textFile(src)
      .filter(_.split("\t").length >= 2).map { x =>
      val lastIndexOf = x.lastIndexOf("\t")
      if (lastIndexOf == -1) {
        ("-", 0)
      } else {
        val keys = x.substring(0, lastIndexOf).split(":")
        val value = x.substring(lastIndexOf + "\t".length()).trim()
        if (Character.isDigit(value.charAt(0))) {
          if (keys.length > 1) {
            (keys(1).trim.toLowerCase, value.toInt)
          } else {
            ("-", 0)
          }
        } else {
          ("-", 0)
        }
      }
    } .filter(!_._1.equals("-"))
      .reduceByKey(_ + _)
      .filter(x => x._1.length > 1)
      .sortBy(x => x._2, ascending = false, 1)
      .map(x => x._1 + "," + x._2)
      .saveAsTextFile(out)
  }

  def main(args: Array[String]): Unit = {

    filterDeptName()

  }
}

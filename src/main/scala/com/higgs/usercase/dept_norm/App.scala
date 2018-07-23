package com.higgs.usercase.dept_norm

import com.higgs.util.Utils
import org.apache.spark.{SparkConf, SparkContext}
// import com.higgs.web.HttpServing

import scala.collection.mutable

/**
  * Hello world!
  */
object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
  // .set("spark.driver.userClassPathFirst", "true")
  // .set("spark.executor.userClassPathFirst", "true")
     .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
     .set("spark.jars.exclude", "io.netty:netty-common")
     .setMaster("local")

  val sc = new SparkContext(conf)

  val filePath = "/Users/devops/Documents/部门归一化/dept_name_resume.txt"

  def init()  = {
    sc.textFile(filePath).map(_.split("\u0001")).filter(_.length == 2)
  }

  def filterEndWithDept(): Unit = {
    // xxx 事业部， xxx 业务部
    val rdd = init()
    val out = "/Users/devops/workspace/gitlab/dept_norm/dept_name_filter_endwith"
    Utils.deleteDir(out)
    // 以部或者部门结尾的词
    val data = rdd.map { x =>
      val name = x(0)
      val frq = x(1).toInt
      if (frq >= 0 && frq < 150 && (name.endsWith("部") || name.endsWith("部门"))) {
         name.replace("部门", "部").toLowerCase
      } else {
        ""
      }
    }.filter(!"".equals(_)).distinct()
      .map { x =>
        200 + "\t" + x + "\t" + x.replace("部", "")
      }.saveAsTextFile(out)
  }

  def combineDict(): Unit = {
    var dir = "/Users/devops/workspace/gitlab/dept_norm"
    val out = "/Users/devops/workspace/gitlab/dept_norm/dept_dict_combine"
    Utils.deleteDir(out)
    val d1 = dir + "/dept_name_filter_endwith_ge_1000.txt"
    val d2 = dir + "/dept_dict_kaka.txt"
    val d3 = dir + "/dept_name_filter_endwith_500_1000.txt"
    val d4 = dir + "/dept_name_filter_endwith_250_500.txt"
    val d5 = dir + "/dept_name_filter_endwith_150_250.txt"
    val r1 = sc.textFile(d1).map(_.split("\t")(1).toLowerCase)
    var r2 = sc.textFile(d2).map(_.split("\t")(1).toLowerCase)
    var r3 = sc.textFile(d3).map(_.split("\t")(1).toLowerCase)
    var r4 = sc.textFile(d4).map(_.split("\t")(1).toLowerCase)
    var r5 = sc.textFile(d5).map(_.split("\t")(1).toLowerCase)
    r1.++(r2).++(r3).++(r4).++(r5).distinct().map{ x =>
      200 + "\t" + x + "\t" + x.replace("部", "")
    }.repartition(1)
      .saveAsTextFile(out)
  }

  def main(args: Array[String]): Unit = {
    filterEndWithDept()
    // combineDict()
  }

}

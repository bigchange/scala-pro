package com.higgs.usercase.dept_norm

import com.higgs.util.Utils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: JerryYou
  *
  * Date: 2018-08-02
  *
  * Copyright (c) 2018 devops
  *
  * <<licensetext>>
  */
object DeptDict {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def sortDict(): Unit = {
    var m = "/Users/devops/workspace/hbase-demo/src/main/resources/dict/dept_norm/final/dept_dict_mapping.txt"
    var d = "/Users/devops/workspace/hbase-demo/src/main/resources/dict/dept_norm/final/dept_dict_v1.txt"
    var m_out = "/Users/devops/workspace/hbase-demo/src/main/resources/dict/dept_norm" +
      "/final_sort_maping"
    var d_out = "/Users/devops/workspace/hbase-demo/src/main/resources/dict/dept_norm" +
      "/final_sort_dict"
    /*
     Utils.deleteDir(d_out)
    sc.textFile(d).map(x=> (x,x.split("\t")))
      .sortBy(x => x._2.apply(1))
      .map(x=> x._1)
      .repartition(1)
      .saveAsTextFile(d_out)
        */
    Utils.deleteDir(m_out)
    sc.textFile(m).map(x=> (x,x.split("\t")))
      .sortBy(x => x._2.apply(1))
      .map(x=> x._1)
      .repartition(1)
      .saveAsTextFile(m_out)


  }

  def finalDeptClass(): Unit = {
    var dept = "/Users/devops/workspace/hbase-demo/src/main/resources/dict/dept_dict_20180727.txt"
    var dept_out = "/Users/devops/workspace/hbase-demo/src/main/resources/dict/dept_norm" +
      "/final_dept_class"
    Utils.deleteDir(dept_out)
    sc.textFile(dept).map(x=> x.split("\t"))
      .map(x => (x.apply(3), 1))
      .sortBy(_._1)
      .reduceByKey(_ + _)
      .map(_._1)
      .repartition(1)
      .saveAsTextFile(dept_out)
  }

  def format(): Unit = {
    var src = "/Users/devops/Documents/部门归一化/dept_name_resume.txt"
    sc.textFile(src).map(_.split("\u0001"))
      .map(x => x(0) + "," + x(1))
      .saveAsTextFile("/Users/devops/Documents/部门归一化/dept_name_resume")

  }

  def main(args: Array[String]): Unit = {
    // sortDict()
    // finalDeptClass()
    format()
  }
}

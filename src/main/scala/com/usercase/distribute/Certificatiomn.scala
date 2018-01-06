package com.usercase.distribute

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Certificatiomn {


  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  // val ssc = new StreamingContext(sc,Seconds(10))

  val parameter = new mutable.HashMap[String, String]()

  // 证书列表整理
  def certifcation(): Unit = {
    val one = "/Users/devops/Downloads/c1.txt"
    val two = "/Users/devops/Downloads/c2.txt"

    val data = sc.textFile(two)

    val data1 = data.zipWithIndex().map(x => (x._2, x._1))

    val data2 = sc.textFile(one).zipWithIndex().map(x => (x._2, x._1))

    data2.leftOuterJoin(data1).map(x => x._2._1 + "\t" + x._2._2.get)
      .repartition(1).saveAsTextFile("/Users/devops/Downloads/certificate")

  }

  // 技能列表数据整理
  def skill: Unit = {
    val src = "/Users/devops/workspace/shell/conf/skill.txt"
    val data = sc.textFile(src).map( x => x).distinct()
      .map(x => x.toLowerCase + "\t" + x.toLowerCase)
      .saveAsTextFile("/Users/devops/workspace/shell/conf/skill_dict")
  }

  def main(args: Array[String]): Unit = {
     skill
    // certifcation()
  }


}

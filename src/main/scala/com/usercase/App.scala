package com.usercase

import java.io.File
import java.text.SimpleDateFormat

import com.model.TFIDF
import jxl.{CellType, DateCell, Workbook}
import org.apache.spark.{SparkConf, SparkContext}
// import web.HttpServing

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

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

  // val ssc = new StreamingContext(sc,Seconds(10))

  val parameter = new mutable.HashMap[String, String]()

  def certifcation(): Unit = {
    val one = "/Users/devops/Downloads/1.txt"
    val two = "/Users/devops/Downloads/2.txt"

    val map = new mutable.HashMap[String, String]()
    map.put("世界五百强", "1")
    map.put("中国五百强", "2")
    map.put("重点公司", "3")

    val data = sc.textFile(two)

    val data1 = data.zipWithIndex().map(x => (x._2, x._1))

    val data2 = sc.textFile(one).zipWithIndex().map(x => (x._2, x._1))

    data1.leftOuterJoin(data2).map(x => x._2._1 + "\t" + map.get(x._2._2.get).get)
      .repartition(1).saveAsTextFile("/Users/devops/Downloads/know_company_dict")

    // data1.map(x => (x._2, 1)).reduceByKey(_ + _).filter(x => x._2 > 1).foreach(println)
    //data2.leftOuterJoin(data1).map(x => x._2._1 + "\t" + x._2._2.get)
    //  .repartition(1).saveAsTextFile("/Users/devops/Downloads/certificate")

  }

  def skill: Unit = {
    val src = "/Users/devops/workspace/shell/conf/skill.txt"
    val data = sc.textFile(src).map( x => x).distinct()
      .map(x => x.toLowerCase + "\t" + x.toLowerCase)
      .saveAsTextFile("/Users/devops/workspace/shell/conf/skill_dict")
  }

  def main(args: Array[String]): Unit = {
    skill
  }

}

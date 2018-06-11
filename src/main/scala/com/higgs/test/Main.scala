package com.higgs.test
import java.io.{FileOutputStream, OutputStreamWriter, PrintWriter, File => JFile}
import java.net.URLEncoder

import io.vertx.core.json.JsonArray
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.jsoup.{Connection, Jsoup}

import scala.collection.mutable.ListBuffer

/**
  * Created by Jerry on 2017/5/22.
  */
object Main  {

  val conf = new SparkConf().setAppName("MAIN")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def test(rdd: RDD[Array[String]]): Unit = {
    rdd.map { x =>
      val min = x.minBy(x=> x.toDouble).toDouble
      val lb = new ListBuffer[Double];
      val ret = x.flatMap { x=>
        lb.+=((x.toDouble - min))
        lb
      }
      ret
    }.foreach{ x => x.foreach(print);println("----");}
  }

  def major() = {
    val src = "/Users/devops/workspace/shell/conf/major_dict"
    val des = "/Users/devops/workspace/shell/conf/major_dict_map"
    val data = sc.textFile(src).map(_.split("\t")).map(x => x(0)).distinct()
    .zipWithIndex().map(x => x._1 + "\t" + (x._2 + 1)).repartition(1).saveAsTextFile(des)

  }

  def school() = {
    val src = "/Users/devops/workspace/shell/conf/school_dict"
    val des = "/Users/devops/workspace/shell/conf/school_dict_map"
    val data = sc.textFile(src).map(_.split("\t")).map(x => x(1)).distinct().zipWithIndex()
    .map(x => x._1 + "\t" + (x._2 + 1)).repartition(1).saveAsTextFile(des)
  }

  def city() = {
    val src = "/Users/devops/workspace/shell/conf/city_dict"
    val des = "/Users/devops/workspace/shell/conf/city_dict_map"
    val data = sc.textFile(src).map(_.split("\t")).map(x => x(0)).distinct()
      .zipWithIndex().map(x => x._1 + "\t" + (x._2 + 1)).repartition(1).saveAsTextFile(des)

  }

  def sortMajor() = {
    val src = "/Users/devops/workspace/shell/conf/major_dict.txt"
    val des = "/Users/devops/workspace/shell/conf/sort_major_dict"
    val data = sc.textFile(src).map(_.split("\t")).map(x => (x(0),x(1).toInt))
      .sortBy(_._2, ascending = false).saveAsTextFile(des)

  }


  def sortSchool() = {
    val src = "/Users/devops/workspace/shell/conf/school_dict.txt"
    val des = "/Users/devops/workspace/shell/conf/sort_school_dict"
    val data = sc.textFile(src).map(_.split("\t")).map(x => (x(0),x(1).toInt))
      .sortBy(_._2, ascending = false).saveAsTextFile(des)
  }

  def sortCity() = {
    val src = "/Users/devops/workspace/shell/conf/city_dict.txt"
    val des = "/Users/devops/workspace/shell/conf/sort_city_dict"
    val data = sc.textFile(src).map(_.split("\t")).map(x => (x(0),x(1).toInt))
    .sortBy(_._2, ascending = false).saveAsTextFile(des)

  }



  def main(args: Array[String]): Unit = {
    // sortCity()
    // sortMajor()
    // sortSchool()

    /*city()
    major()
    school()*/

    // println("互联网产品经理".hashCode)
    // println("船务".hashCode)

    val rdd = sc.parallelize(Seq(Array("0.07","0.0", "0.12", "0.0","0.21")))
    test(rdd)


  }

}

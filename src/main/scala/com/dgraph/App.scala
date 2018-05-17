package com.dgraph

import java.{io, util}

import com.util.Utils
import dgraph.DgraphClient
import org.apache.spark.{SparkConf, SparkContext}

import scala.reflect.io.File

object App {

  val conf = new SparkConf().setAppName("dgraph")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def school_dict(): Unit = {
    var dir = "/Users/devops/Documents/知识图谱/school"
    // var classFile = dir + "/class.txt"
    var sname = dir + "/school_name.txt"
    var sname_en = dir + "/school_name_en.txt"
    var alias = dir + "/alias.txt"
    var result = dir + "/result"

    Utils.deleteDir(result)

    var aliasData = sc.textFile(alias).zipWithIndex().map(x => (x._2, x._1))
    var schoolName = sc.textFile(sname).zipWithIndex().map(x => (x._2, x._1))
    var schoolNameEn = sc.textFile(sname_en).zipWithIndex().map(x => (x._2, x._1))
    schoolName.leftOuterJoin(schoolNameEn).leftOuterJoin(aliasData)
      .sortBy(x => x._1, ascending = true)
      .map(x => (x._1 + 1) + "\t" + x._2._1._1 + "\t" + x._2._1._2.getOrElse("") + "\t" + x._2._2.getOrElse(""))
      .repartition(1).saveAsTextFile(result)

  }



  def main(args: Array[String]): Unit = {

    school_dict()
  }
}

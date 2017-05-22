package com.usercase

import java.util
import java.io.{File => JavaFile}

import com.hankcs.hanlp.HanLP
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer
import scala.reflect.io.File
import scala.util.control.Breaks

/**
  * Created by Jerry on 2017/5/16.
  */
object Test {

  def textFilterComposer(x:String): Boolean = {
    var check = false
    if (x.length >=2 && x.length <= 10) {
      check = true
    }
    check
  }

  def convertToLB(list: util.List[String]): ListBuffer[String] = {
    val lb = new ListBuffer[String]
    val size = list.size()
    for (i <- 0 until size) {
      lb.+=(list.get(i))
    }
    lb
  }
  def  textFormatMap (x:String) : ListBuffer[String] = {
    val list = HanLP.extractKeyword(x, 8)
    list.addAll(HanLP.extractPhrase(x, 8))
    val lb = convertToLB(list)

    // segment
    lb.++=(getSegment(x))

    lb.+=(x)

    if (x.contains(" ")) {
      lb.++=(x.split(" "))
    }
    if (x.contains("/")) {
      lb.++=(x.split("/"))
    }
    if (x.contains("&")) {
      lb.++=(x.split("&"))
    }
    if (x.contains(",")) {
      lb.++=(x.split(","))
    }
    if (x.contains(";")) {
     lb.++=:(x.split(";"))
    }
    lb
  }

  /** 词频
    * 统计
    */
  def wordFrequency = {

    val filterSrc = "/Users/devops/workspace/shell/jobtitle/JobTitle/src/*"
    val filterDes = "/Users/devops/workspace/shell/jobtitle/JobTitle/result/job_title_src_freq"
    new File(new JavaFile(filterDes)).deleteRecursively()
    val data = sc.textFile(filterSrc).flatMap { x =>
      val lb = new ListBuffer[String]
      val sp = x.split("\t")
      if (sp.length >= 1) {
        lb.+=(sp(0).trim)
      }
      lb
    }.flatMap(getSegment).map(x=> (x, 1)).reduceByKey(_ + _)
      .filter(x => x._1.length >= 2 && x._1.length <= 10)
      .sortBy(x => x._2, ascending = false, numPartitions = 1)
    data.filter(x => x._1.contains("师") || x._1.contains("员")).map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("file://" + filterDes)

  }

  /**
    * 提取核心词组
    * @param txt
    * @return
    */
  def getSegment(txt:String) = {

    val lb = new ListBuffer[String]

    lb.+=(txt)

    val keyWords = HanLP.extractKeyword(txt, 8)
    val phrases = HanLP.extractPhrase(txt, 8)
    keyWords.addAll(phrases)
    // val list = HanLP.segment(txt)
    val size = keyWords.size()
    for (i <- 0 to size - 1) {
      val item = keyWords.get(i)
      lb.+=(item)
    }
    val list = HanLP.segment(txt)
    for (j <- 0 to list.size() - 1) {
      val item = list.get(j).word
      lb.+=(item)
    }
    // 通过重复一定次数该职位，形成类似一篇文档的效果（20次）
    lb.distinct
  }

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro").setMaster("local"))
  val src = "/Users/devops/workspace/shell/jobtitle/JobTitle/src/"
  val srcResult = "/Users/devops/workspace/shell/jobtitle/JobTitle/result/src"
  sc.setLogLevel("ERROR")
  val dictPath = "/Users/devops/workspace/shell/jobtitle/dict"
  val dict = sc.textFile(dictPath).collect()
  // 判断是否是职位的词表
  def isJobTitle (data: RDD[String]): Unit = {
    data.flatMap(textFormatMap)
      .filter(textFilterComposer)
      .map(x => (x, 1))
      .reduceByKey(_ + _)
      .sortBy(x => x._1, ascending = false, numPartitions = 1)
      .map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("file://" + srcResult)
  }


  def numberFilter (string: String) = {
    var flag = true
    val break = new Breaks
    break.breakable {
      string.foreach { x =>
        if (x >= '0' && x <= '9') {
          flag = false
          break.break()
        }
      }
    }
    flag
  }

  /**
    * 职位按指定要求filter
    */
  def jobTitleFilter() = {

    val root = "/Users/devops/workspace/shell/jobtitle/JobTitle"
    val dir = "src"
    val filterSrc = root + "/" + dir
    val filterDes = "/Users/devops/workspace/shell/jobtitle/JobTitle/result/"
    // new File(new JavaFile(filterDes)).deleteRecursively()
    val data = sc.textFile(filterSrc).flatMap { x =>
      val lb = new ListBuffer[String]
      val sp = x.split("\t")
      if (sp.length >= 1) {
        lb.+=(sp(0).trim)
      }
      lb
    }.filter(x => x.length >= 2 && x.length <= 10)

    // cache
    // data.saveAsTextFile("file:///Users/devops/workspace/shell/jd/jd-cache")

    // filter step one
    val dataFilterOne = data
      .filter(x =>
        !x.contains("&")
        && !x.contains("+")
        && !x.contains("-")
        && !x.contains(";")
        && !x.contains("鼎")
        && !x.contains("男") && !x.contains("女")
          && !x.contains("聘") && !x.contains("黑龙")
          && !x.contains("黑色")
          && !x.contains("龙")
          && !x.contains("区")
          && !x.contains("全国")
        && !x.contains("小时")
          && !x.contains("公司")
          && !x.contains("校区")
          && !x.contains("额")
          && !x.contains("薪")
          && !x.contains("高中")
          && !x.contains("驻")
          && !x.contains("福利")
          && !x.contains("五险")
          && !x.contains("提成")
          && !x.contains("待遇")
          && !x.contains("洲")
          && !x.contains("州")
          && !x.contains("(") && !x.contains(")")
      ).filter(numberFilter)
      .map(x=> (x, 1)).reduceByKey(_ + _)
      .sortBy(x => x._2, ascending = false, numPartitions = 1)
      .map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("file://" + root + "/step-one")

    // get c++
    data.map(_.toLowerCase).filter(x => x.contains("c++"))
      .map(x=> (x, 1)).reduceByKey(_ + _)
      .sortBy(x => x._2, ascending = false, numPartitions = 1)
      .map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("file://" + filterDes + "/" + dir + "-step-two-c")

    // get 师
    data.map(_.toLowerCase).filter(x => x.contains("师"))
      .map(x=> (x, 1)).reduceByKey(_ + _)
      .sortBy(x => x._2, ascending = false, numPartitions = 1)
      .map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("file://" + filterDes + "/" + dir + "step-two-shi")

    // get by dict
    data.map(_.toLowerCase).filter(x => filterByDict(x, dict))
      .map(x=> (x, 1)).reduceByKey(_ + _)
      .sortBy(x => x._2, ascending = false, numPartitions = 1)
      .map(x => x._1 + "\t" + x._2)
      .saveAsTextFile("file://" + filterDes + "/" + dir + "step-two-dict")

  }

  /**
    * 按mid, last dict 过滤职位
    * @param key
    * @param array
    * @return
    */
  def filterByDict(key: String, array: Array[String]): Boolean = {
    var flag = false
    val break = new Breaks
    break.breakable {
      array.foreach { x=>
        if (key.contains(x)) {
          flag = true
          break.break()
        }
      }
    }
    flag
  }

  def main(args: Array[String]): Unit = {
    // wordFrequency
    // println(getSegment(".net开发".toLowerCase))
    jobTitleFilter()
  }

}

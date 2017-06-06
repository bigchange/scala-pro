package com.usercase

import org.apache.spark.{SparkConf, SparkContext}



/**
  * Created by Jerry on 2017/6/6.
  */
object XmanDataPrepare {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro").setMaster("local"))

  val savaFile = "file:///Users/devops/workspace/shell/web/search"

  val dataDir = "file:///Users/devops/workspace/gitlab/idmg/resume_extractor/src/cc"

  def loadData(file:String, pos: Int)  = {

    sc.textFile(file).map(_.split("\t")(pos))

  }

  def main(args: Array[String]): Unit = {

    val schoolFile = s"$dataDir/school_dict.txt"

    val jobTitle = s"file:///Users/devops/workspace/shell/jd/result-map/part-00000"

    val schoolData = loadData(schoolFile, 2)

    val jobData = loadData(jobTitle, 0)


    println(schoolData.take(1)(0))


  }

}

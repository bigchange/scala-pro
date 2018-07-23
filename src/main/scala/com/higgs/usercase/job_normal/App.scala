package com.higgs.usercase.job_normal

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Jerry on 2017/9/14.
  */
object App {

  val conf = new SparkConf().setAppName("jobNormal")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      print("Usage <src>")
      System.exit(-1)
    }
    val src = args(0)

    var rdd1 = sc.textFile(src).map { x =>
      val sp = x.split("\t")
      (sp(0), sp(1))
    }

    /*var classMap = rdd1.map(x=> (x._1, 1)).reduceByKey(_ +_ )
      .saveAsTextFile("file:////Users/devops/workspace/shell/data/job_dict_des/classMap")
    */
    var targets = rdd1.map(_._1).distinct().collect()

    println("class_size:" + targets.length)

    for (i <- 0 until 5) {

      var rDD: RDD[(String, String)] = sc.emptyRDD[(String, String)]
      println("class_index:" + i)
      var sampleRate = 0.5
      val target = targets.apply(i)
      val same = rdd1.filter(x => target.equals(x._1)).map(_._2)
      val sameSize = same.count()
      if (1000 / sameSize >= 1) {
        sampleRate = 0.5
      } else {
        sampleRate = 1000 / sameSize
      }
      val sameTargets = same.randomSplit(Array(sampleRate, 1 - sampleRate))

      val first = sameTargets.apply(0).zipWithUniqueId().map(x => (x._2, x._1))
      val second = sameTargets.apply(1) .zipWithUniqueId().map(x => (x._2, x._1))

      val sameIndex = same.zipWithUniqueId().map(x => (x._2, x._1))
      val result = first.leftOuterJoin(sameIndex)
        .map { x =>(x._2._1, x._2._2.get)
      }
      rDD = rDD.++(result)

      if (sampleRate == 0.5) {
        val secondResult = second.leftOuterJoin(sameIndex).map { x =>
          (x._2._1, x._2._2.get)
        }
        rDD = rDD.++(secondResult)

      }

      val diff = rdd1.filter(x => !target.equals(x._1)).map(_._2)

      val diffIndex = diff.zipWithUniqueId().map(x => (x._2, x._1))
      val diffSize = diff.count()
      sampleRate = 0.5
      if (2000 / diffSize >= 1) {
        sampleRate = 0.5
      } else {
        sampleRate = 2000 / diffSize
      }

      val diffTarget = diff.randomSplit(Array(sampleRate, 1 - sampleRate))
      val dfirst = diffTarget.apply(0).zipWithUniqueId().map(x => (x._2, x._1))
      val dsecond = diffTarget.apply(1).zipWithUniqueId().map(x => (x._2, x._1))

      val resultDiff = dfirst.leftOuterJoin(diffIndex).map { x =>
        (x._2._1, x._2._2.get)
      }

      // rDD = rDD.++(resultDiff)
      if (sampleRate == 0.5) {
        val secondResult = dsecond.leftOuterJoin(diffIndex).map { x =>
          (x._2._1, x._2._2.get)
        }
        // rDD = rDD.++(secondResult)
      }
      rDD.saveAsTextFile("file:////Users/devops/workspace/shell/data/job_dict_des/result/"
        + i)
    }
  }

}

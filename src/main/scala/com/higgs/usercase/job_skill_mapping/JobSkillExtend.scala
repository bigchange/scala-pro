package com.higgs.usercase.job_skill_mapping

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object JobSkillExtend {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  val jobSkillMap = new mutable.HashMap[String, mutable.Set[String]]()

  val simJob = new mutable.HashMap[String, mutable.Set[String]]()

  def loadJobSkill = {
      val src = "/Users/devops/workspace/shell/conf/job_skill.txt"
      sc.textFile(src).map(x => x.split("\t")).map(x => (x(0), x(1))).foreach { x =>
        if (jobSkillMap.contains(x._1)) {
          jobSkillMap.put(x._1.toLowerCase,jobSkillMap.get(x._1).get.+=(x._2.toLowerCase()))
        } else {
          val set = new mutable.HashSet[String]()
          set.+=(x._2.toLowerCase)
          jobSkillMap.put(x._1.toLowerCase, set)
        }
      }
  }

  def loadJobSim: Unit = {

    val src = "/Users/devops/workspace/shell/conf/sim_jobs.txt"
    sc.textFile(src).map(x => x.split("\t")).filter(x => x.length == 2).map(x => (x(0), x(1)))
      .foreach { x =>
        val simJobs = x._2.split(" ").foreach { y =>
          val key = x._1.toLowerCase
          if (!key.equals(y.toLowerCase)) {
            if (jobSkillMap.contains(key)) {
              jobSkillMap.put(key,jobSkillMap.get(key).get.++=(jobSkillMap.get(y.toLowerCase).getOrElse(new mutable.HashSet[String]())))
            } else {
              jobSkillMap.put(key, jobSkillMap.get(y.toLowerCase()).getOrElse(new mutable.HashSet[String]()))
            }
          }
        }
      }

  }


  def main(args: Array[String]): Unit = {

     loadJobSkill

    println("jobSkillMap Size :" + jobSkillMap.size)

    // sc.parallelize(jobSkillMap.toSeq).map(x => (x._1, x._2.size)).sortBy(_._2, ascending = false).foreach(println)

    // sc.parallelize(jobSkillMap.toSeq).map(x => x._1+ "\t" + x._2.mkString(",")).saveAsTextFile("/Users/devops/workspace/shell/conf/job_kill_map")

     loadJobSim

    println("jobSkillMap Size :" + jobSkillMap.size)

    // sc.parallelize(jobSkillMap.toSeq).map(x => (x._1, x._2.size)).sortBy(_._2, ascending = false).foreach(println)

    sc.parallelize(jobSkillMap.toSeq).map(x => x._1+ "\t" + x._2.mkString(",")).saveAsTextFile("/Users/devops/workspace/shell/conf/job_extend_with_sim")

  }


}

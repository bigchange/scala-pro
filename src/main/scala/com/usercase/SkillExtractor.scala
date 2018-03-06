package com.usercase

import java.util
import java.util.regex.Pattern

import com.hankcs.hanlp.HanLP
import io.vertx.core.json.JsonObject
import org.apache.spark.{SparkConf, SparkContext}
import resume_extractor.SkillExtactorer

import scala.collection.mutable.ListBuffer

/**
  * Created by Jerry on 2017/7/28.
  */
object SkillExtractor {

  val conf = new SparkConf().setAppName("skill").setMaster("local")
  val sc = new SparkContext(conf)

  def loadData() = {
    val src = "/Users/devops/workspace/shell/resume/skills/skills/*"

    val data = sc.textFile(src).map(_.split("\t")).map(x => x(1))
    .map {x =>
      val jsn = new JsonObject(x)
      val introduce = jsn.getString("introduce", "")
      introduce
    }
    data
  }

  def extractorSkill (args: Array[String]): Unit = {

    val src = "/Users/devops/workspace/shell/resume/skills/skills/*"

    val des = "/Users/devops/workspace/shell/resume/skills/tmp_introduce"

    val data = sc.textFile(src).map(_.split("\t")).map(x => x(1))

    data.map {x =>
      val lb = new ListBuffer[String]
      val ret = new JsonObject()
      val jsn = new JsonObject(x)
      val introduce = jsn.getString("introduce", "")
      val keyWords = HanLP.extractKeyword(introduce, 3)
      val segment = HanLP.segment(introduce)
      introduce
    }.saveAsTextFile(des)


  }

  def skill_result(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/skills_result/skills/*"

    val des = "/Users/devops/workspace/shell/resume/skills_result/rank"

    val data = sc.textFile(src).map(_.split("\t")).filter(x => x.length == 2)
      .map { x => (x(0), x(1).toInt)}
      .sortBy(_._2, ascending =  false)
      .repartition(1)
    .saveAsTextFile(des)

  }

  val pattern = Pattern.compile("[a-zA-Z]*");
  // 过滤英文记录
  def filterEnglish(key: String): Boolean = {
    pattern.matcher(key).matches() && key.length > 1
  }

  def filterDigit(key :String): Boolean = {
    key forall(x => x.isDigit)
  }

  def skill_token(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/count_result/skills/skills"

    val des = "/Users/devops/workspace/shell/resume/count_result/skills/zh"

    val data = sc.textFile(src).map(_.split("\t")).filter(x => x.length == 2)

      .map { x => (x(0), x(1).toInt)}

      .reduceByKey(_ + _)

      .filter(x => x._1.length >= 2)

      // .filter(x => filterEnglish(x._1))

       .filter(x => !filterEnglish(x._1) && x._1.length > 2 && !filterDigit(x._1))

      .sortBy(_._2, ascending =  false)

      .repartition(1)

      .map(x => x._1 + "\t" + x._2)

      .saveAsTextFile(des)

  }

  def profession_skills(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/profession_skills_split/skills/*"

    val des = "/Users/devops/workspace/shell/resume/profession_skills_split/eng_key"

    val data = sc.textFile(src).map(_.split("\t")).filter(x => x.length == 2)

      .map { x => (x(0), x(1).toInt)}

      .filter(x => filterEnglish(x._1) && x._2 > 200)

      // .filter(x => !filterEnglish(x._1) && !filterDigit(x._1) && x._1.length > 2 && x._1.length< 5 && x._2 > 200)

      .sortBy(_._2, ascending =  false)

      .repartition(1)

      .map(x => x._1 + "\t" + x._2)

      .saveAsTextFile(des)

  }

  def skillDictCombine() = {

    val src = "/Users/devops/workspace/shell/resume/skills_dict/eng_dict/*"

    val des = "/Users/devops/workspace/shell/resume/skills_dict/eng_ranking"

    val data = sc.textFile(src).map(_.split("\t")).filter(x => x.length == 2)

      .map { x => (x(0), x(1).toInt)}

      .filter(x => x._1.length >= 2)

      .reduceByKey(_ + _)

      .sortBy(_._2, ascending =  false)

      .repartition(1)

      .map(x => x._1 + "\t" + x._2)

      .saveAsTextFile(des)

  }

  def test (args: Array[String]): Unit = {
    val data = loadData()
    data.foreach { x =>
      val result = new util.HashSet[String]()
      val skillExtractor = new SkillExtactorer()
      println(x)
      skillExtractor.getSkills(x, result)
      println("------------------------")
    }
  }

  def main(args: Array[String]): Unit = {

    // extractorSkill(args)

    // test(args)

    // skill_result()

     skill_token()

    // profession_skills()

    // skillDictCombine()

  }

}

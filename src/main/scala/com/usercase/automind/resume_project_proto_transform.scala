package com.usercase.automind

import io.vertx.core.json.{JsonArray, JsonObject}
import org.apache.spark.{SparkConf, SparkContext}

object resume_project_proto_transform {

  val conf = new SparkConf().setAppName("resume_project_proto_transform")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def main(args: Array[String]): Unit = {

    var src = ""
    var data = sc.textFile(src).map { x =>}

  }

  // 转成resume
  def mappingIndexToMapResumeProto(lines: Array[String]): JsonObject = {
    var reusmeJson =  new JsonObject()
    var basic = new JsonObject()
    var resumeExperiences = new JsonObject()
    var resumeProjects = new JsonObject()
    var resumeEducations = new JsonObject()

    basic.put("id", lines(0).toInt)
    basic.put("name", lines(6))
    if (!"NULL".equals(lines(8)))
      basic.put("gender", lines(8).toInt)
    basic.put("startedWorkAt", lines(14))
    basic.put("introduce", lines(15))
    var expectLocations = new JsonArray()
    basic.put("expectLocations", expectLocations.add(lines(20).split(",").map(x => x.toInt)))
    var expect_salary = new JsonObject(lines(21))
    basic.put("expectSalaryType", expect_salary.getInteger("salaryType"))
    basic.put("createdBy", lines(24).toInt)
    basic.put("updatedBy", lines(25).toInt)
    basic.put("createdAt", lines(26))
    basic.put("updatedAt", lines(27))
    basic.put("degree", lines(35).toInt)
    basic.put("orgName", lines(46))
    basic.put("position", lines(47))

    //
    reusmeJson.put("basic", basic)
    reusmeJson.put("resumeExperiences", resumeExperiences)
    reusmeJson.put("resumeProjects", resumeProjects)
    reusmeJson.put("resumeEducations", resumeEducations)
    return reusmeJson
  }
  // 转成project
  def mappingIndexToMapAiProjectProto(lines: Array[String]) : JsonObject = {
    var projectJson =  new JsonObject()
    projectJson.put("jobDuty", lines(15))
    projectJson.put("title",lines(7))
    projectJson.put("id", lines(3))
    projectJson.put("orgName","")
    projectJson.put("status",lines(6))
    projectJson.put("orgExternalName","")
    var requiredOthers = new JsonArray()
    projectJson.put("requiredOthers",requiredOthers)
    var addressCodes = new JsonArray()
    addressCodes.add(lines(9).split(",").map(x => x.toInt))
    projectJson.put("addressCodes",addressCodes)
    var alterAge = new JsonObject()
    var age = new JsonObject()
    if (!"NULL".equals(lines(19))) {
      alterAge.put("ageLower", lines(19).toInt)
      age.put("ageLower", lines(19).toInt)
    }
    if (!"NULL".equals(lines(20))) {
      alterAge.put("ageUpper", lines(20).toInt)
      age.put("ageUpper", lines(20).toInt)
    }
    projectJson.put("alterAge", alterAge)
    projectJson.put("age", alterAge)
    if (!"NULL".equals(lines(21))) {
      var gender = new JsonObject()
      gender.put("required", lines(21).toInt)
      projectJson.put("gender", gender)
    }
    var salary = new JsonObject()
    if (!"NULL".equals(lines(13)))
      salary.put("salaryLower",lines(13).toDouble)
    if (!"NULL".equals(lines(14)))
      salary.put("salaryUpper",lines(14).toDouble)
    projectJson.put("salary", salary)

    var educationExperience = new JsonObject()
    var educationExperienceRequired = new JsonObject()
    educationExperience.put("required", educationExperienceRequired)
    projectJson.put("educationExperience", educationExperience)

    var workExperience = new JsonObject()
    var workExperienceRequired = new JsonObject()
    workExperience.put("required", workExperienceRequired)
    projectJson.put("workExperience", workExperience)
    return projectJson
  }

}

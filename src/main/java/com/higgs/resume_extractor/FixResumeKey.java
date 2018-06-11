package com.higgs.resume_extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import scala.Serializable;

/**
 * Created by Jerry on 2017/6/9.
 */
public class FixResumeKey  implements Serializable {

  private Logger logger = LoggerFactory.getLogger(FixResumeKey.class);
  private HashMap<Integer, JsonArray> jobGradesMap = new HashMap<>();

  private PrintWriter printer;

  public FixResumeKey(String file) {
    loadFile(file);
    String save = "/Users/devops/workspace/shell/resume/job_grades";
    new File(save).delete();
    try {
      printer = new PrintWriter(new File(save));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 1 = workExprs
   * 2 = eduExprs
   * 去掉首尾空格
   *
   * @return jobTitle list
   */
  public ArrayList infoFormat(JsonArray jsonArray, int type) {
    ArrayList<String> jobs = new ArrayList<>();
    if (jsonArray != null) {
      int size = jsonArray.size();
      for (int i = 0; i < size; i++) {
        JsonObject finalResult = jsonArray.getJsonObject(i);
        // work expr
        if (type == 1) {
          if (finalResult.containsKey("org")) {
            JsonObject org = finalResult.getJsonObject("org");
            String company = org.getString("name", "").trim();
            org.put("name", company);
          }
          if (finalResult.containsKey("jobTitle")) {
            String jobTitle = finalResult.getString("jobTitle", "").trim();
            finalResult.put("jobTitle", jobTitle);
            if (!"".equals(jobTitle)) {
              jobs.add(jobTitle);
            }
          }
        } else if (type == 2) { // edu expr
          if (finalResult.containsKey("school")) {
            JsonObject school = finalResult.getJsonObject("school");
            String title = school.getString("title", "").trim();
            school.put("title", title);
          }
          if (finalResult.containsKey("major")) {
            JsonObject major = finalResult.getJsonObject("major");
            String title = major.getString("title", "").trim();
            major.put("title", title);
          }
        }
      }
    }
    ArrayList<String> jobGrades = new ArrayList<>();

    for (String jobTitle: jobs) {
      String res = jobTitle + " -> " + getJobGrade(jobTitle);
      jobGrades.add(res);
      if (jobTitle.length() > 50)
        continue;
      printer.write(res + "\n");
    }
    printer.flush();


    return jobGrades;
  }


  public void loadFile(String file) {
    String configStr = null;
    try {
      configStr = new String(Files.readAllBytes(Paths.get(file)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    JsonObject config = new JsonObject(configStr);
    for (int i = 1; i<=7; i++) {
      jobGradesMap.put(i, config.getJsonArray(String.valueOf(i)));
    }

    logger.info("jobGradesMap size => " + jobGradesMap.size());
  }

  /**
   * 下属级别职级过滤
   * @param jobTitle
   * @return
   */
  private int filterJob (String jobTitle, int i) {
    if (jobTitle.contains("助理") || jobTitle.contains("秘书")
        || jobTitle.contains("文秘")) {
      return 6;
    } else if (jobTitle.contains("司机")) {
      return 1;
    } else if (jobTitle.contains("主任")) {
      return 2;
    } else {
      return i;
    }
  }

  /**
   * TODO 关键词过滤
   * 根据职位确定职级
   * 职级表：职级(1:专员,2:主管,3:经理,4:高级经理,5:总监,6:董秘,7:总裁)
   * @param title jobTitle
   * @return
   */
  public int getJobGrade(String title) {

    if (title.length() > 60) {
      return 0;
    }
    String jobTitle = title.toLowerCase();

    for (int i = 7; i >= 1; i -- ) {
      JsonArray jobGrades = jobGradesMap.getOrDefault(i, null);
      for (int j = 0; j<jobGrades.size(); j++) {
          if (jobTitle.contains(jobGrades.getString(j))) {
            if (i == 7 || i == 5 ) {
              return filterJob(jobTitle, i);
            }
            if (i == 4 || i == 3) {
              return filterJob(jobTitle, i);
            }
          }
      }
    }
    return 1;
  }

}

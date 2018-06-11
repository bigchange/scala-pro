package com.higgs.resume_extractor;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Jerry on 2017/7/31.
 */
public class SkillExtactorer {

  public SkillExtactorer() {

  }

  private static ArrayList<String> words = new ArrayList<>();

  static {
    words.add("熟练掌握");
    words.add("熟练使用");
    words.add("熟练运用");
    words.add("熟练");
    words.add("掌握");
    words.add("熟悉与精通");
    words.add("熟悉");
    words.add("精通");
    words.add("懂");
    words.add("了解");
    // 熟练掌握、熟练使用、熟练运用、掌握
    // 熟悉与精通、熟悉、精通
    // 懂、了解
  }



  public void iterator(String tmpStr, String item, HashSet<String> result) {

    int indexOfItem = tmpStr.indexOf(item);

    if (indexOfItem == -1) {
      return;
    }

    int indexOfDot = tmpStr.indexOf(".", indexOfItem);
    int indexOfSem = tmpStr.indexOf(";", indexOfItem);
    int indexOfBlank = tmpStr.indexOf(",", indexOfItem);

    int itemLength = item.length();

    if (indexOfDot != -1) {
      String ret = tmpStr.substring(indexOfItem, indexOfDot);
      result.add(ret);
    } else if (indexOfSem != -1) {
      String ret = tmpStr.substring(indexOfItem, indexOfSem);
      result.add(ret);
    } else if (indexOfBlank != -1) {
      String ret = tmpStr.substring(indexOfItem, indexOfBlank);
      result.add(ret);
    }

    tmpStr = tmpStr.substring(indexOfItem + itemLength);

    iterator(tmpStr, item, result);

  }

  private void split(String target, HashSet<String> result) {
    String [] sp = target.split("_unk_");
    if (sp.length < 2) {
      return;
    }
    for (String proSkil: sp) {
      String [] skils = proSkil
          .replaceAll(",","_unk_").replaceAll(",", "_unk_")
          .replaceAll(" ", "_unk_").replaceAll(":", "_unk_")
          .split("_unk_");

      for (String i: skils) {
        if (!"".equals(i)) {
          result.add(i + "_end_");
        }
      }
    }
  }

  public void getSkills(String target, HashSet<String> result) {
    int wordSize = words.size();
    for (String item: words) {
      if (!target.contains(item)) {
        continue;
      }
      // iterator(target, item, result);

      target = target.replaceAll(item, "_unk_");

    }

    split(target, result);

  }


}

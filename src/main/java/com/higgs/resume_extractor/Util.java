package com.higgs.resume_extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scala.Serializable;

/**
 * Created by Jerry on 2017/6/30.
 */
public class Util implements Serializable {

  public Util() {

  }

  //根据中文unicode范围判断u4e00 ~ u9fa5不全
  public static int isChinese(String str) {
    String regEx1 = "[\\u4e00-\\u9fa5]+";
    String regEx2 = "[\\uFF00-\\uFFEF]+";
    String regEx3 = "[\\u2E80-\\u2EFF]+";
    String regEx4 = "[\\u3000-\\u303F]+";
    String regEx5 = "[\\u31C0-\\u31EF]+";
    Pattern p1 = Pattern.compile(regEx1);
    Pattern p2 = Pattern.compile(regEx2);
    Pattern p3 = Pattern.compile(regEx3);
    Pattern p4 = Pattern.compile(regEx4);
    Pattern p5 = Pattern.compile(regEx5);
    Matcher m1 = p1.matcher(str);
    Matcher m2 = p2.matcher(str);
    Matcher m3 = p3.matcher(str);
    Matcher m4 = p4.matcher(str);
    Matcher m5 = p5.matcher(str);
    if (m1.find() || m2.find() || m3.find() || m4.find() || m5.find())
      return 1;
    else
      return 0;
  }

  /**
   * 判断是不是英文字母
   * @param charaString
   * @return
   */
  public  static int isEnglish(String charaString) {
    if (charaString.matches("^[a-zA-Z]*")) {
      return 1;
    } else  {
      return 0;
    }
  }

  /**
   * 中文所占的比例
   */
  public static double chinesePercent(String context) {
    int counter = 0;
    String formatString = filterSpecialSymbol(context);
    int length = formatString.length();
    for (int i = 0; i < length; i++) {
      String indexItem = String.valueOf(formatString.charAt(i));
      int result = isChinese(indexItem);
      counter += result;
    }
    return counter / (1.0 * length);
  }

  public static  double englishPercent(String context) {
    int counter = 0;
    String formatString = filterSpecialSymbol(context);
    int length = formatString.length();
    for (int i = 0; i < length; i++) {
      String indexItem = String.valueOf(formatString.charAt(i));
      int result = isEnglish(indexItem);
      counter += result;
    }
    return counter / (1.0 * length);
  }

  public  static  String filterSpecialSymbol(String org) {
    String regEx = "[`~!@#$%^&*()+=|{}':;',//[//]" +
        ".<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\]\\[―•▪]";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(org);
    return m.replaceAll("").trim();
  }

  public static void main(String[] args) {
    System.out.println("\r\n使用正则表达式判断:");
    int counter = 0;
    String context4 = "?\\n应聘职位:水暖主管\\n工作地点:济南\\n中文\\nid:jr000449786r9\\nzhaopin.com male " +
     "|\\nborn in 1981/9 | hukou: shandong jinan | location shandong jinan\\n7years' experience |" +
      " citizenship | personal id card: 37010419810919191415866608008 mobile  | 13573791094 " +
       "company \\nemail: dukedadai@163.com career objective\\ndesired type of employment: " +
        "full-time\\ndesired position: building/decoration/urban construction civil " +
         "servant/public organizations/science research institute senior management " +
          "education/training\\ndesired industry: real estate/architectural/building " +
           "materials/construction\\ndesired location: jinan\\nexpected salary before tax : " +
            "4001-6000rmb/month\\ncurrent situation: in1month评语\\n评语内容\\n时间\\n评审人";
    String formatString  = filterSpecialSymbol(context4);
    System.out.println(formatString);
    System.out.println(formatString.length());
    for (int i=0; i < formatString.length(); i++) {
       String indexItem = String.valueOf(formatString.charAt(i));
       int result = isChinese(indexItem);
       counter += result;

    }
    System.out.println(counter);
    System.out.println(counter / (formatString.length() * 1.0));

    System.out.println(englishPercent(formatString));

  }
}

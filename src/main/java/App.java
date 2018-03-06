import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

import utils.MysqlUtil;

/**
 * Created by Jerry on 2017/8/22.
 */
public class App {

  private static Pattern pattern = Pattern.compile("[a-zA-Z_.\\s\\(\\)-]*");

  private static String whiteSpaceAbandon(String src) {
    return src.replaceAll("\\s*", "");
  }
  public static void main(String[] args) {
    String fileName = "YUAN Jing Sheng_-(cn).pdf";
    if (pattern.matcher(fileName).matches()) {
      System.out.println("this is true");
    } else {
      System.out.println("this is false");
    }
  }
}

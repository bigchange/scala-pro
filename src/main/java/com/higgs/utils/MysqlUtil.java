package com.higgs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jerry on 2017/8/22.
 */
public class MysqlUtil {

  private static final String DBDRIVER = "com.mysql.jdbc.Driver";//驱动类类名

  private static final String DBNAME = "casem";//数据库名

  private static final String DBURL ="jdbc:mysql://172.16.52.52:3306/" + DBNAME;//连接URL

  private static final String DBUSER ="casem";//数据库用户名

  private static final String DBPASSWORD = "Casem123@";//数据库密码

  private static Connection conn = null;

  private static PreparedStatement ps = null;

  private static ResultSet rs = null;

  // 获取数据库连接

  public static Connection getConnection() {

    try {

      Class.forName(DBDRIVER); //注册驱动

      conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); //获得连接对象

    } catch (ClassNotFoundException e) { //捕获驱动类无法找到异常

      e.printStackTrace();

    } catch (SQLException e) { //捕获SQL异常

      e.printStackTrace();

    }

    return conn;

  }

  public static void init() {
    conn = getConnection();
  }

  // 查询数据
  public static  PreparedStatement select(String sql) throws Exception {

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      // rs = ps.executeQuery(sql);

      return ps;

    } catch (SQLException sqle) {

      throw new SQLException("select data Exception: " + sqle.getMessage());

    } catch (Exception e) {

      throw new Exception("System error: " + e.getMessage());

    }

  }
}

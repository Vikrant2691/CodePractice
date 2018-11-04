package com.test;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.DriverManager;

  

public class HiveDemo{
  private static String driver = "org.apache.hive.jdbc.HiveDriver";

  public static void main(String[] args) throws SQLException {
      try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    Connection connect = DriverManager.getConnection("jdbc:hive2://35.154.223.51:10000/default", "", "");
    Statement state = connect.createStatement();
    String tableName = "test";
    //state.executeQuery("drop table " + tableName);
    ResultSet res = state.executeQuery("select * from lookalike_scorecard limit 10");
    ResultSetMetaData rsmd = res.getMetaData();
    int columnsNumber = rsmd.getColumnCount();
    while (res.next()) {
        for (int i = 1; i <= columnsNumber; i++) {
            if (i > 1) System.out.print(",  ");
            String columnValue = res.getString(i);
            System.out.print(columnValue + " " + rsmd.getColumnName(i));
        }
        System.out.println("");
    }
    System.out.println(columnsNumber);
   /*
   // Query to show tables
    String show = "show tables";
    System.out.println("Running show");
    res = state.executeQuery(show);
    if (res.next()) {
      System.out.println(res.getString(1));
    }

    // Query to describe table
    String describe = "describe " + tableName;
    System.out.println("Running describe");
    res = state.executeQuery(describe);
    while (res.next()) {
      System.out.println(res.getString(1) + "\t" + res.getString(2));
    }
*/
  }
}
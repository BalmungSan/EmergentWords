package com.eafit.lmejias3.wordsfinder.DataBase;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 *This class manage all the operations with the database
 */
public class DataBaseManager {

  //Conection with the DB
  private Connection con;

  //Database path
  private final String DB = "//localhost/WordsFinder";

  //User name to login in local host with phpMyAdmin
  private final String USER = "root";

  //Password for user to login in local host with phpMyAdmin
  private final String PASSWORD = "a120020254B.";

  //Query to update the table
  private final String update = "INSERT INTO `words` (Word, ?) VALUES (?, ?)"
    + " ON DUPLICATE KEY UPDATE ?=?";

  /**
   * Constructor of the class, gets a connection with the database
   */
  public DataBaseManager () {
    try {
      System.out.println("Conecting with the database ");
      Class.forName("com.mysql.jdbc.Driver");

      con = DriverManager.getConnection("jdbc:mysql:" + DB, USER, PASSWORD);

      System.out.println("Connection succesfully");
    }
    catch(SQLException e){
      System.out.println("MySQL error " + e.getMessage());
      System.exit(1);
    }
    catch(ClassNotFoundException e){
      e.printStackTrace();
      System.exit(1);
    }
    catch (Exception e) {
      System.out.println("Unexpected error:  " + e.getMessage());
      System.exit(1);
    }
  }

  /**
   * Get all the words where the user selected column is not false
   * @param column name of the column to match
   * @return a List with all the words
   */
  public List<String[]> getall (String column) {
    String[] data;
    List<String[]> result = new ArrayList<>();
    Statement st = null;
    ResultSet rs = null;

    try {
      //Get the words of all rows where the column is not 'FALSE'
      st  = con.createStatement();
      rs = st.executeQuery("select Word, " + column +
                           " from words where " + column +
                           "<>'FALSE'");

      //Save the information
      while (rs.next()) {
        data = new String[2];
        data[0] = rs.getString(1); //word
        data[1] = rs.getString(2); //column
        result.add(data);
      }
    } catch (SQLException ex) {
      System.err.println("Unexpected error ocurred with the database");
      System.err.println(ex.getMessage());
      System.exit(1);
    } finally {
      try {
        if (st != null) st.close();
        if (rs != null) rs.close();
      } catch (SQLException ex) {
        System.err.println("Unexpected error ocurred with the database");
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    }

    return result;
  }

  /**
   * Add data to the database
   * Add a new row if word not exist
   * If exist, update the row
   * @param word primary key
   * @param column name of the column where the information is inserted
   * @param data information to store in column
   */
  public void updatedata (String word, String column, String data) {
    PreparedStatement pst = null;

    try {
      pst = con.prepareStatement(update);
      pst.setString(1, column);
      pst.setString(2, word);
      pst.setString(3, data);
      pst.setString(4, column);
      pst.setString(5, data);
      pst.executeUpdate();
    } catch (SQLException ex) {
      System.err.println("Unexpected error ocurred with the database");
      System.err.println(ex.getMessage());
      System.exit(1);
    } finally {
      try {
        if (pst != null) pst.close();
      } catch (SQLException ex) {
        System.err.println("Unexpected error ocurred with the database");
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    }
  }

  /**
   * Close the connection with the database
   */
  public void close () {
    try {
      con.close();
      System.out.println("Connection with the database closed");
    } catch (SQLException ex) {
      System.err.println("Unexpected error ocurred with the database");
      System.err.println(ex.getMessage());
      System.exit(1);
    }
  }
}

package com.eafit.lmejias3.wordsfinder.DataBase;

import java.sql.*;

/**
 * @class This class manage all the operations with the database
 */
public class DataBaseManager {

  //Conection with the DB
  private Connection con;

  //User name to login in local host with phpMyAdmin
  private String USER = "root";

  //Password for user to login in local host with phpMyAdmin
  private String PASSWORD = "a120020254B.";

  /**
   * @method Constructor of the class, It connects to a specific database
   * @param Name of the database to connect
   */
  public DataBaseManager (String DBName) {

    try {
      System.out.println("Conecting with the database " + DBName);
      Class.forName("com.mysql.jdbc.Driver");

      con = DriverManager.getConnection("jdbc:mysql://localhost/wordsfinder/"
                                        + DBName, USER, PASSWORD);

      System.out.println("Cenection succesfully");
    }
    catch(SQLException e){
      System.out.println("MySQL error");
    }
    catch(ClassNotFoundException e){
      e.printStackTrace();
    }
    catch (Exception e) {
      System.out.println("Unexpected error:  " + e.getMessage());
    }
  }
}

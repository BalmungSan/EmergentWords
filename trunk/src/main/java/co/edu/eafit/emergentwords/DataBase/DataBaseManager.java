package co.edu.eafit.emergentwords.DataBase;

import java.sql.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

/**
 *This class manage all the operations with the database
 */
public class DataBaseManager {

  //Conection with the DB
  private Connection con;

  //Database path
  private final String DB = "//SQLSERVER/WordsFinder";

  //User name to login in local host with phpMyAdmin
  private final String USER = "SQLUSER";

  //Password for user to login in local host with phpMyAdmin
  private final String PASSWORD = "SQLPASSWORD";

  //Querry to get all the table
  private final String all = "SELECT * FROM words";

  //Prepared statements for update Labels column
  private final String updateLabels = "INSERT INTO words (Word, Labels) " +
    "VALUES (?, CONCAT(?, ', ')) ON DUPLICATE " +
    "KEY UPDATE Labels = CONCAT(Labels, ?, ', ')";

  private final String eraseLabels = "UPDATE words SET Labels=? " +
    "WHERE Word=?";

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
   * Get all the data in the database
   * @return a List with all the rows of the table
   */
  public List<String[]> getTable () {
    String data[];
    List<String[]> result = new ArrayList<>();
    Statement st = null;
    ResultSet rs = null;


    try {
      st  = con.createStatement();
      rs = st.executeQuery(all);

      //Save the information
      while (rs.next()) {
        data = new String[4];

        data[0] = rs.getString(1); //word
        data[1] = rs.getString(2); //excluded
        data[2] = rs.getString(3); //find
        data[3] = StringUtils.replace(rs.getString(4), ",", ""); //label

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
   * Get all the words where the user selected column is not false
   * @param column name of the column to match
   * @return a List with all the words
   */
  public List<String[]> getAll (String column) {
    String[] data;
    List<String[]> result = new ArrayList<>();
    Statement st = null;
    ResultSet rs = null;

    try {
      //Get the words of all rows where the column is not 'FALSE'
      st  = con.createStatement();
      rs = st.executeQuery("SELECT Word, " + column +
                           " FROM words WHERE " + column +
                           "<>'FALSE'");

      //Save the information
      while (rs.next()) {
        data = new String[2];
        data[0] = rs.getString(1); //word
        data[1] = StringUtils.replace(rs.getString(2), ",", ""); //column
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
   * Get all the different values of a column sorted alphabetically
   * @param column name of the column to match
   * @return a List with all the different values of the column
   */
  public Set<String> getDifferent (String column) {
    Set<String> result = new TreeSet<>();
    Statement st = null;
    ResultSet rs = null;

    try {
      st  = con.createStatement();
      rs = st.executeQuery("SELECT DISTINCT " + column + " FROM words");

      //Save the information
      while (rs.next()) {
        for (String s : rs.getString(1).split(", ")) {
          if (!s.equals("")) result.add(s);
        }
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
   * Only for Excluded and Find Columns
   * for Labels clomun see {@link #updateDataLabels}
   * @param word primary key
   * @param column name of the column where the information is inserted
   * @param data information to store in column
   */
  public void updateData (String word, String column, String data) {
    //Query to update the table
    PreparedStatement pst = null;
    String update = "INSERT INTO words (Word, " + column + ") VALUES " +
      "(?, ?) ON DUPLICATE KEY UPDATE " + column + "=?";

    try {
      pst = con.prepareStatement(update);
      pst.setString(1, word);
      pst.setString(2, data);
      pst.setString(3, data);
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
   * Update the word's labels column
   * Works like {@link #updateData} but for the labels column
   * @param word primary key
   * @param data information to modify in lables column
   * @param insert flag that determine if data is added or is erased
   */
  public void updateDataLabels (String word, String data, boolean insert) {
    PreparedStatement pst = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      if (insert) {
        //If insert is 'true' append the data
        pst = con.prepareStatement(updateLabels);
        pst.setString(1, word);
        pst.setString(2, data);
        pst.setString(3, data);
        pst.executeUpdate();
      } else {
        //If not, erase the data

        //Get the actual data
        String query = "SELECT Labels FROM words WHERE Word='" + word +"'";
        st = con.createStatement();
        rs = st.executeQuery(query);
        rs.next();
        String result = rs.getString(1);

        //Erase the data
        result = StringUtils.remove(result, data + ", ");

        //Update the information
        pst = con.prepareStatement(eraseLabels);
        pst.setString(1, result);
        pst.setString(2, word);
        pst.executeUpdate();
      }
    } catch (SQLException ex) {
      System.err.println("Unexpected error ocurred with the database");
      System.err.println(ex.getMessage());
      System.exit(1);
    } finally {
      try {
        if (pst != null) pst.close();
        if (st != null) st.close();
        if (rs != null) rs.close();
      } catch (SQLException ex) {
        System.err.println("Unexpected error ocurred with the database");
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    }
  }

  /**
   * Close the connection with the database and erase innecesary data
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

  /**
   * Erase the words where its rows are
   * Word Excluded Find  Labels
   *  *    FAlSE   FALSE  ''
   * Because that means that word is unused
   */
  public void eraseData () {
    String erase = "DELETE FROM words " +
      "WHERE Excluded='FALSE' AND Find='FALSE' AND Labels=''";
    Statement st = null;

    try {
      st = con.createStatement();
      st.execute(erase);
    } catch (SQLException ex) {
      System.err.println("Unexpected error ocurred with the database");
      System.err.println(ex.getMessage());
      System.exit(1);
    } finally {
      try {
        if (st != null) st.close();
      } catch (SQLException ex) {
        System.err.println("Unexpected error ocurred with the database");
        System.err.println(ex.getMessage());
      }
    }
  }
}

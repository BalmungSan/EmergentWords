package com.eafit.lmejias3.wordsfinder.WordsFinder;

import javax.swing.table.DefaultTableModel;
import java.util.*;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * This class opens the file selected by the user and read it word by word
 */
public class WordsFinder {

  //WordsManager Class reference to send all the found words
  private WordsManager mywm;

  //Table model to store the results and prit them in ResultsInterface
  private DefaultTableModel results;

  //Conection with database
  private DataBaseManager database;

  /**
   * Constructor of the class
   * @param database Reference to a instance of DataBaseManager
   * @see DataBaseManager
   */
  public WordsFinder (DataBaseManager database) {
    this.database = database;
  }

  /**
   * Here start all the logic of the program
   * @param mode Flag that indicate the mode selected by the user
   * @param filename Path to file the user chose
   */
  public void findWords (char mode, String filename) {
    //Configure app to work with user selected mode
    switch (mode) {
    case 'f':
      //The mode is to find specific words
      mywm = new WMFind(database);
      break;
    case 'e':
      //The mode is to find all words
      mywm = new WMExclude(database);
      break;
    case 'l':
      //The mode is to find labels
      mywm = new WMLabels(database);
      break;
    }

    //Open the file
    openfile(filename);

    //Sort the results
    sort(mywm.getfound());
  }

  /**
   * Return the results of the search
   * @return TableModel with the results
   * @see DefaultTableModel
   */
  public DefaultTableModel getResults() {
    return results;
  }

  /**
   * Detect what type of file is ('.doc', '.docx', '.pdf' or binary text)
   * @param filename Path to file the user chose
   */
  private void openfile (String filename) {
    if (filename.endsWith(".doc")) {
      //Is a word file ('.doc')
      opendoc(filename);
    } else if (filename.endsWith(".docx")) {
      //Is a word file ('.docx')
      opendocx(filename);
    } else if (filename.endsWith(".pdf")) {
      //Is a pdf file
      openpdf(filename);
    } else {
      //Is a binary file with any extension
      openbinary(filename);
    }
  }

  /**
   * Open a .doc file
   * @param filename Path to file the user chose
   */
  private void opendoc (String filename) {
  }

  /**
   * Open a .docx file
   * @param filename Path to file the user chose
   */
  private void opendocx (String filename) {
  }

  /**
   * Open a .pdf file
   * @param filename Path to file the user chose
   */
  private void openpdf (String filename) {
  }

  /**
   * Open a binary file
   * @param filename Path to file the user chose
   */
  private void openbinary (String filename) {
  }

  /**
   * Breaks all the content of the file in words separated by blank spaces
   * @param contents All the data in the file
   */
  private void getwords (String contents) {
  }

  /**
   * Sort a map<String key, Integer value> by value
   * remark: This code was taken and adapted from:
   *         http://www.mkyong.com/java/how-to-sort-a-map-in-java/
   * @param unsortMap Map to sort by value
   * @see Map
   */
  private void sort (Map<String, Integer> unsortMap) {

    // Convert Map to List
    List<Map.Entry<String, Integer>> list =
      new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2) {
          return (o1.getValue()).compareTo(o2.getValue());
        }
      });

    // Save the sorted list in results table
    results = new DefaultTableModel();
    results.addColumn("WORD");
    results.addColumn("TIMES");
    Object[] row;
    for (Map.Entry<String, Integer> entry : list) {
      row = new Object[2];
      row[0] = entry.getKey();
      row[1] = entry.getValue();
      results.addRow(row);
    }
  }
}

package com.eafit.lmejias3.wordsfinder.WordsFinder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;
import org.apache.commons.io.FilenameUtils;

/**
 * This class opens the file selected by the user and read it word by word
 */
public class WordsFinder {

  //Variables for the progress bar
  ProgressMonitor pbar;
  JFrame parent;
  int counter;

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
  public WordsFinder (DataBaseManager database, JFrame parent) {
    this.database = database;
    this.parent = parent;
  }

  /**
   * Here start all the logic of the program
   * @param mode Flag that indicate the mode selected by the user
   * @param filename Path to file the user chose
   */
  public void findWords (String mode, String filename)
    throws userCancellationException {

    //Configure app to work with user selected mode
    switch (mode) {
    case "F":
      //The mode is to find specific words
      mywm = new WMFind(database);
      break;
    case "E":
      //The mode is to find all words
      mywm = new WMExclude(database);
      break;
    case "L":
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
   * Clear the data on WordsManager to free space
   */
  public void clear () {
    mywm.clear();
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
  private void openfile (String filename) throws userCancellationException {

    String ext = FilenameUtils.getExtension(filename).toLowerCase();

    switch (ext) {
    case "doc":
      //Is a word file ('.doc')
      opendoc(filename);
      break;
    case "docx":
      //Is a word file ('.docx')
      opendocx(filename);
      break;
    case "pdf":
      //Is a pdf file
      openpdf(filename);
      break;
    default :
      //Is a binary file with any extension
      openbin(filename);
      break;
    }
  }

  /**
   * Open a .doc file
   * @param filename Path to file the user chose
   */
  private void opendoc (String filename) throws userCancellationException {
  }

  /**
   * Open a .docx file
   * @param filename Path to file the user chose
   */
  private void opendocx (String filename) throws userCancellationException {
  }

  /**
   * Open a .pdf file
   * @param filename Path to file the user chose
   */
  private void openpdf (String filename) throws userCancellationException {
  }

  /**
   * Open a binary file usually .txt
   * @param filename Path to file the user chose
   */
  private void openbin (String filename) throws userCancellationException {
  }

  /**
   * Breaks all the content of the file in words separated by blank spaces
   * sents that words to words managar class
   * and show a progres monitor for the user view the amount of data porcesed
   * @param contents All the data in the file
   * @see ProgressMonitor
   */
  private void getwords (String contents) throws userCancellationException {
    System.out.println(contents);
    counter = 0;
    pbar = new ProgressMonitor(parent, "Searching words progress",
                               "Initializing . . .", 0, contents.length());

    while (contents.length() > 0) {
    }

    pbar.close();
  }

  /**
   * Update the progress of the progress monitor
   * @param pro the length of the word operated
   * @see ProgressMonitor
   */
  private void update (int pro) throws userCancellationException {
    if (pbar.isCanceled()) {
      pbar.close();
      throw new userCancellationException("You cancelled the searching");
    }

    pbar.setProgress(counter);
    pbar.setNote("Operation is " + counter + "% complete");
    counter += pro;
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

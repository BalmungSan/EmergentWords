package co.edu.eafit.emergentwords.WordsFinder;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.eafit.emergentwords.WordsFinder.WordsManager.*;
import co.edu.eafit.emergentwords.DataBase.DataBaseManager;
import co.edu.eafit.emergentwords.Interface.ResultInterface;

/**
 * This class takes all the words in the file for analysis
 * according to the mode slected by the user
 * and show a progres monitor for the user view the  data porcesed
 * @see SwingWorker
 */
public class WordsFinder extends SwingWorker<Void, Void> {
  //WordsManager Class reference to send all the found words
  private WordsManager mywm;

  //Table model to store the results and prit them in ResultsInterface
  private final DefaultTableModel results;

  //The message of the search
  private String ms;

  //The words of the file
  private final String words[];

  //Variables for the progress bar
  private final ProgressMonitor pbar;
  private final int total;
  private int counter;

  /**
   * Constructor of the class
   * @param database Reference to a instance of DataBaseManager
   * @param mode Flag that indicate the mode selected by the user
   * @param words An array with all the words in the file
   * @param filename The name of the file selected by the user
   * @see DataBaseManager
   */
  public WordsFinder (DataBaseManager database, String mode, String[] words,
                      String filename) {

    //Initilize worker variables
    mywm = null;
    this.words = words;
    results = new DefaultTableModel();

    //Initialize progress monitor bar variables
    total = words.length;
    counter = 0;

    //Initialization of progress monitor from 0 to the length of the text
    pbar = new ProgressMonitor(null, "Searching words progress",
                               "Initializing", 0, total);

    //Add the file name to the message
    ms = filename;

    //Configure app to work with user selected mode
    switch (mode) {
    case "F":
      //The mode is to find specific words
      mywm = new WMFind(database);
      ms = ms + " - FIND";
      break;
    case "E":
      //The mode is to find all words
      mywm = new WMExclude(database);
      ms = ms + " - EXCLUDED";
      break;
    case "L":
      //The mode is to find labels
      mywm = new WMLabels(database);
      ms = ms + " - LABELS";
      break;
    }
  }

  @Override
  public synchronized Void doInBackground() {
    //For every word in the text
    //the words are separated by a blank space [ \t\n\x0B\f\r]
    for (String word : words) {
      if (!isCancelled()) {
        //If the user have not cancelled the execution, do...

        //Send the word to the instance of the words manager class
        mywm.addWord(word);

        //Update the progress monitor
        update();
      } else {
        //If the user have cancelled the execution, break
        break;
      }
    }

    pbar.close();
    return null;
  }

  @Override
  public synchronized void done() {
    if(!isCancelled()) {
      //Sort the results
      sort(mywm.getfound());

      //Show the results in the interface
      ResultInterface ri = new ResultInterface(results, ms);
      ri.setVisible(true);
    }

    //Clear the data and free space
    mywm.clear();
  }

  /**
   * Update the progress of the progress monitor
   * @see ProgressMonitor
   */
  private synchronized void update () {
    //Check if the user has pressed the button 'Cancel'
    if (pbar.isCanceled()) {
      //If was pressed, cancel the execution
      this.cancel(true);

      System.err.println("You have cancelled the searching");

      //custom title, warning icon
      JOptionPane.showMessageDialog(null, "You have cancelled the searching",
                                    "Warning", JOptionPane.WARNING_MESSAGE);
    } else {
      //If not, update the progress
      pbar.setProgress(counter);
      pbar.setNote("Operation is " + counter * 100 / total + "% complete");
      counter ++;
    }
  }


  /**
   * Sort a map<String key, Integer value> by value (high to low)
   * remark: This code was taken and adapted from:
   *         http://www.mkyong.com/java/how-to-sort-a-map-in-java/
   * @param unsortMap Map to sort by value
   * @see Map
   */
  private void sort (Map<String, Integer> unsortMap) {

    // Convert Map to List
    List<Map.Entry<String, Integer>> list =
      new LinkedList<>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2) {
          return (o2.getValue()).compareTo(o1.getValue());
        }
      });

    // Save the sorted list in results table
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

package co.edu.eafit.emergentwords.WordsFinder.WordsManager;

import java.util.*;
import co.edu.eafit.emergentwords.DataBase.DataBaseManager;

/**
 * Implementation of WordsManager to find all words
 * and classify them in labels
 * @see WordsManager
 */
public class WMLabels extends  WordsManager {

  //Map <Word, Label>
  private final Map<String, List<String>> labels;

  /**
   * Constructor of the class
   * get the words's labels from the database
   * @param database Reference to DataBaseManager
   * @see DataBaseManager
   */
  public WMLabels (DataBaseManager database) {
    this.database = database;
    labels = new HashMap<>();

    //row = {'word', 'labels'} | labels = "label1 label2 ..."
    for (String[] row : database.getAll("Labels")) {
      //Add to labels the pair wors -> List<labesl>
      labels.put(row[0].toLowerCase(), Arrays.asList(row[1].split(" ")));
    }

    for (String l : database.getDifferent("Labels")) {
      //Add every label with a counter of 0
      found.put(l, 0);
    }
  }

  /**
   * Increment the counter of found times for the label the word belongs
   * @param word String with the word found
   */
  @Override
  public void addWord (String word) {
    if (labels.get(word) != null) {
      //If word has a label defined by the user
      //Increase the counter
      for (String label : labels.get(word)) {
        int counter = found.get(label) + 1;
        found.replace(label, counter);
      }
    }
  }

  /**
   * Clear found an labels
   */
  @Override
  public void clear () {
    found.clear();
    labels.clear();
  }
}

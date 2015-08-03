package co.edu.eafit.emergentwords.WordsFinder.WordsManager;

import co.edu.eafit.emergentwords.DataBase.DataBaseManager;

/**
 * Implementation of WordsManager to find specific words in a file
 * @see WordsManager
 */
public class WMFind extends WordsManager {

  /**
   * Constructor of the class
   * Get the set of words to find from the database
   * @param database Reference to DataBaseManager
   * @see DataBaseManager
   */
  public WMFind (DataBaseManager database) {
    this.database = database;

    //row = {'word', "TRUE"}
    for (String[] row : database.getAll("Find")) {
      //Add the word with a counter of 0
      found.put(row[0].toLowerCase(), 0);
    }
  }

  /**
   * Increment the counter of found times for word
   * if the word is one of the words the user want to find
   * @param word String with the word found
   */
  @Override
  public void addWord (String word) {
    if (found.get(word) != null) {
      //If word is one of the words to find
      //Increase the counter
      int times = found.get(word) + 1;
      found.replace(word, times);
    }
  }

  /**
   * clear found
   */
  @Override
  public void clear () {
    found.clear();
  }
}

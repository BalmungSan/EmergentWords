package co.edu.eafit.wordsfinder.WordsFinder.WordsManager;

import java.util.*;
import co.edu.eafit.wordsfinder.DataBase.DataBaseManager;

/**
 * Implementation of WordsManager to find all words in a file
 * @see WordsManager
 */
public class WMExclude extends WordsManager {

  //Set of excluded words
  private final Set<String> excluded;

  /**
   * Constructor of the class
   * get the set of Excluded words from the database
   * @param database Reference to DataBaseManager
   * @see DataBaseManager
   */
  public WMExclude (DataBaseManager database) {
    this.database = database;
    excluded = new HashSet<>();

    //row = {'word', "TRUE"};
    for (String[] row : database.getAll("Excluded")) {
      //Add word to the excluded set
      excluded.add(row[0].toLowerCase());
    }
  }

  /**
   * Increment the counter of found times for word
   * if the word not is one of the excluded words
   * @param word String with the word found
   */
  @Override
  public void addWord (String word) {
    if (!excluded.contains(word)) {
      //If word is not a excluded word

      if (found.get(word) != null) {
        //If word has been already found
        //Increase the counter
        int times = found.get(word) + 1;
        found.replace(word, times);
      } else {
        //If not, add to the map with count of 1
        found.put(word, 1);
      }
    }
  }

  /**
   * Clear found and excluded
   */
  @Override
  public void clear () {
    found.clear();
    excluded.clear();
  }
}

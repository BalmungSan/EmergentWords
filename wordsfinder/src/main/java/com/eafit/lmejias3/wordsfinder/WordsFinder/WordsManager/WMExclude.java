package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * @class: Implementation of WordsManager to find all words
 */
public class WMExclude implements WordsManager {

  //Conection with DataBase
  private DataBaseManager database;

  //Set of excluded words
  private Set<String> excludedwords;

  /**
   * @method: Constructor of the class
   *          Create a new instance of database, conecting to ExcludedWords DB
   *          Get the set of Excluded words
   */
  public WMExclude () {
    database = new DataBaseManager("ExcludedWords");
    getexcluded();
  }

  /**
   * @method: Increment the counter of found times for word
   *          if the word not is one of the excluded words
   * @param: word
   */
  @Override
  public void addword (String word) {
  }

  /**
   * @method: Return the found words
   * @return: Map with the found words and the amount of times
   */
  @Override
  public Map<String, Integer> getfound() {
    return found;
  }

  /**
   * @method: Get all the words to exclude from the database
   */
  private void getexcluded() {
    excludedwords = new HashSet<>();
  }
}

package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * @class: Implementation of WordsManager to find all words
 *         and classify them in labels
 */
public class WMLabels implements WordsManager {

  //Conection with DataBase
  private DataBaseManager database;

  //Map <Word, Label>
  private Map<String, String> labels;

  /**
   * @method: Constructor of the class
   *          Create a new instance of database, conecting to Labes DB
   *          Get the set of Excluded words
   */
  public WMLabels () {
    database = new DataBaseManager("ExcludedWords");
    getlabels();
  }

  /**
   * @method: Increment the counter of found times for the label
   *          the word belongs
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
   * @method: Get all the words and the label it belongs from the database
   */
  private void getlabels() {
    labels = new HashMap<>();
  }
}

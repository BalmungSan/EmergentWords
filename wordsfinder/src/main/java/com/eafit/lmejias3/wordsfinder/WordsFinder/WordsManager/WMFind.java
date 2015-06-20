package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * @class: Implementation of WordsManager to find specific words
 */
public class WMFind implements WordsManager {

  //Conection with DataBase
  private DataBaseManager database;

  /**
   * @method: Constructor of the class
   *          Create a new instance of database, conecting to FindWords DB
   *          Get the set of Excluded words
   */
  public WMFind () {
    database = new DataBaseManager("FindWords");
    getfind();
  }

  /**
   * @method: Increment the counter of found times for word
   *          if the word is one of the words the user want to find
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
   * @method: Get all the words to find from the database
   */
  private void getfind() {
  }
}

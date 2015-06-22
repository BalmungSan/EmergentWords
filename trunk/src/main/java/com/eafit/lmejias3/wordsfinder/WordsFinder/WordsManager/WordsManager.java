package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.Map;
import java.util.HashMap;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * Abstract class Wordsmanager
 * with out the implementation of the addword method
 */
public abstract class WordsManager {

  //Conection with database
  private DataBaseManager database = new DataBaseManager();

  //Map <Words, Times> to save the searching results
  private Map<String, Integer> found = new HashMap();

  /**
   * Add the word to the found map
   * @param word to analyze
   */
  abstract void addword (String word);

  /**
   * Return the found words
   * @return Map with the found words and the amount of times
   */
  public Map<String, Integer> getfound() {
    return found;
  }
}

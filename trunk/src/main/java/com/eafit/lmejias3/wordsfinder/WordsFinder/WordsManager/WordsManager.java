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
  DataBaseManager database;

  //Map <Words, Times> to save the searching results
  Map<String, Integer> found = new HashMap();

  /**
   * Add the word to the found map
   * @param word to analyze
   */
  public abstract void addWord (String word);

  /**
   * Clear the data to free space
   */
  public abstract void clear ();

  /**
   * Return the found words
   * @return Map with the found words and the amount of times
   */
  public Map<String, Integer> getfound() {
    return found;
  }
}

package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.Map;
import java.util.HashMap;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * @interface: Interface for all the WordsManager classes
 */
public interface WordsManager {

  //Map <Words, Times> to save the searching results
  Map<String, Integer> found = new HashMap<>();

  /**
   * @method: Add the word to the found map
   * @param: word to analyze
   */
  public void addword (String word);

  /**
   * @method: Return the found words
   * @return: Map with the found words and the amount of times
   */
  public Map<String, Integer> getfound();
}

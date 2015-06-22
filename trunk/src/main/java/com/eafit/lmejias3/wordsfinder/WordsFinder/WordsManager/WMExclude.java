package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * Implementation of WordsManager to find all words in a file
 * @see WordsManager
 */
public class WMExclude extends WordsManager {

  //Set of excluded words
  private Set<String> excludedwords;

  /**
   * Constructor of the class
   * get the set of Excluded words from the database
   */
  public WMExclude () {
    excludedwords = new HashSet<>();
  }

  /**
   * Increment the counter of found times for word
   * if the word not is one of the excluded words
   * @param word String with the word found
   */
  @Override
  public void addword (String word) {
  }
}

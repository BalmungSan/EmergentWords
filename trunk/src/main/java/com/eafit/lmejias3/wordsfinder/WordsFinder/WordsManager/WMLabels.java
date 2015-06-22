package com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager;

import java.util.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * Implementation of WordsManager to find all words
 * and classify them in labels
 * @see WordsManager
 */
public class WMLabels extends  WordsManager {

  //Map <Word, Label>
  private Map<String, String> labels;

  /**
   * Constructor of the class
   * get the words's labels from the database
   */
  public WMLabels () {
    labels = new HashMap<>();
  }

  /**
   * Increment the counter of found times for the label the word belongs
   * @param word String with the word found
   */
  @Override
  public void addword (String word) {
  }
}

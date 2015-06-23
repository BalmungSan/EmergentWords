package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsFinder;

/**
 * Interface for the user to select a file and the mode of searching
 * @see JFrame
 * @see ActionListener
 */
public class WordsFinderInterface extends JFrame implements ActionListener {

  //WordsFinder class to pass the file selected by the user
  WordsFinder wordsfinder;

  /**
   * Constructor of the Interface using a GridBagLayout
   * @param wf Reference to WordsFinder instance
   * @see GridBagLayout
   * @see WordsFinder
   */
  public WordsFinderInterface (WordsFinder wf) {
    //Initializate wordsfinder
    wordsfinder = wf;

    //Configure the interface with a GribBagLayout -------------------------
    setTitle("SETTINGS");
    setSize(400, 150);
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    //----------------------------------------------------------------------
  }

  /**
   * Here are handled all the events activated in this interface
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
  }
}

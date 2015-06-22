package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsFinder;

/**
 * Interface for the user to select a file and the mode of searching
 * @see JFrame
 * @see ActionListener
 */
public class WordsFinderInterface extends JFrame implements ActionListener {

  /**
   * Constructor of the Interface using a GribBagLayout
   * @see GribBagLayout
   */
  public WordsFinderInterface () {
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
   */
  @Override
  public void actionPerformed(java.awt.event.ActionEvent evt) {
  }
}

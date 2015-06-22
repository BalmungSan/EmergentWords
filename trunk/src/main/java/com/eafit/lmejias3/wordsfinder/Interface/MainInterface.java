package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Main Interfce, the program starts here
 * @see JFrame
 * @see ActionListener
 */
public class MainInterface extends JFrame implements ActionListener {

  /**
   * Constructor of the Interface using a GribBagLayout
   * @see GribBagLayout
   */
  public MainInterface () {
    //Configure the interface with a GribBagLayout -------------------------
    setTitle("WORDSFINDER");
    setSize(400, 150);
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

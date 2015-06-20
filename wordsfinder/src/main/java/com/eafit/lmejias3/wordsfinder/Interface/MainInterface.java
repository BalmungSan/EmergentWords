package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @class: Main Interfce, the program starts here
 */
public class MainInterface extends JFrame implements ActionListener {

  /**
   * @method Constructor of the Interface
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
   * @method: Here are handled all the events activated in this interface
   */
  @Override
  public void actionPerformed(java.awt.event.ActionEvent evt) {
  }
}

package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * @class: Interface for the user to view or modify the information
 *         in the databases
 */
public class DataBaseInterface extends JFrame implements ActionListener {

  //Names of the databases used by the program
  private final String databases[] = {"ExcludedWords", "FindWords", "Labels"};

  /**
   * @method Constructor of the Interface
   */
  public DataBaseInterface () {
    //Configure the interface with a GribBagLayout -------------------------
    setTitle("DATABASE");
    setSize(400, 150);
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

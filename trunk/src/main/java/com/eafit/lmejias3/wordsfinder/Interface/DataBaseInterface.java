package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * Interface for the user to view or modify the information on the database
 * @see JFrame
 * @see ActionListener
 */
public class DataBaseInterface extends JFrame implements ActionListener {

  //Names of the rows of the database used by the program
  private final String databases[] = {"ExcludedWords", "FindWords", "Labels"};

  //Conection with the database
  private DataBaseManager database;

  /**
   * Constructor of the Interface with a GribBagLayout
   * Create a new instance of DataBaseManager
   * @see GribBagLayout
   * @see DataBaseManager
   */
  public DataBaseInterface () {
    //Initializate database
    database = new DataBaseManager();

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
   * Here are handled all the events activated in this interface
   */
  @Override
  public void actionPerformed(java.awt.event.ActionEvent evt) {
  }
}

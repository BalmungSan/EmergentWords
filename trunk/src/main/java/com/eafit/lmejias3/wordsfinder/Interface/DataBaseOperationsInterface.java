package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * Interface for the user to add or remove rows to the selected DB
 * @see JFrame
 * @see ActionListener
 */
public class DataBaseOperationsInterface extends JFrame
  implements ActionListener {

  //Conection to the database selected by the user
  private DataBaseManager database;

  /**
   * Constructor of the Interface with a GribBagLayout
   * @param database Reference to a DataBaseManager instance
   * @see GribBagLayout
   * @see DataBaseManager
   */
  public DataBaseOperationsInterface (DataBaseManager database) {
    this.database = database;

    //Configure the interface with a GribBagLayout -------------------------
    setTitle("DATABASE OPERATIONS");
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

package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;

/**
 * @class: Interface for the user to add or remove rows to the selected DB
 */
public class DataBaseOperationsInterface extends JFrame
  implements ActionListener {

  //Conection to the database selected by the user
  private DataBaseManager database;

  /**
   * @method Constructor of the Interface
   * @param database: Reference to a DataBaseManager instance
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
   * @method: Here are handled all the events activated in this interface
   */
  @Override
  public void actionPerformed(java.awt.event.ActionEvent evt) {
  }
}

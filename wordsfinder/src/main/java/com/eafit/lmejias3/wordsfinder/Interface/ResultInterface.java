package com.eafit.lmejias3.wordsfinder.Interface;

import java.awt.BorderLayout;
import java.awt.Label;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 * @class Interface to show the user the results of the searching
 *        Either from the database or a file
 */

public class ResultInterface extends javax.swing.JFrame {

  /**
   * @method: Constructor of the class
   * @param: model: Model that contains the data of the table
   * @param: message: Text to show to the user
   */
  public ResultInterface (DefaultTableModel model, String message) {
    setTitle("RESULTS");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setSize(350, 120);
    add(new Label(message), BorderLayout.NORTH);
    add(new JTable(model), BorderLayout.CENTER);
  }
}

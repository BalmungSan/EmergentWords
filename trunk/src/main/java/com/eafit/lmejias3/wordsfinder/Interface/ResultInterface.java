package com.eafit.lmejias3.wordsfinder.Interface;

import java.awt.BorderLayout;
import java.awt.Label;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 * Interface to show the user the results of the searching
 * either from the database or a file
 * @see JFrame
 */

public class ResultInterface extends javax.swing.JFrame {

  /**
   * Constructor of the class using a BorderLayout
   * create a JTable with the data to show at the user
   * @param model Model that contains the data of the table
   * @param message Text to show to the user
   * @see BorderLayout
   * @see JTable
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

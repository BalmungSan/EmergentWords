package com.eafit.lmejias3.wordsfinder.Interface;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Vector;
import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * Interface to show the user the results of the searching
 * either from the database or a file
 * @see JFrame
 */
public class ResultInterface extends JFrame implements ActionListener {

  //Model of table to save the results
  DefaultTableModel model;
  String message;

  /**
   * Constructor of the class using a BorderLayout
   * create a JTable with the data to show at the user
   * @param model Model that contains the data of the table
   * @param message Text to show to the user
   * @see BorderLayout
   * @see JTable
   */
  public ResultInterface (DefaultTableModel model, String message) {
    this.model = model;
    this.message = message;

    setTitle("RESULTS");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(420, 250);

    add(new Label(message), BorderLayout.NORTH);

    JScrollPane scrollTable = new JScrollPane(new JTable(model));
    scrollTable.setFont(new Font("Serif", Font.ITALIC, 12));
    add(scrollTable, BorderLayout.CENTER);

    JButton save = new JButton ("Save results");
    save.setActionCommand("Save");
    save.addActionListener(this);
    add(save, BorderLayout.SOUTH);
  }

  /**
   * Here are handled all the events activated in this interface
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    String Action = evt.getActionCommand();

    if (Action.equals("Save")) {
      //Save the information

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Specify a file to save");
      int userSelection = fileChooser.showSaveDialog(this);

      if (userSelection == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        FileWriterWithEncoding fw = null;

        try {
          //Create a new file with encoding UTF-8 to append the text
          fw = new FileWriterWithEncoding(file, Charset.forName("UTF-8"),
                                          true);

          //Write the tittle
          fw.write("\n\r----------------- RESULTS ----------------- \n\r");
          fw.write(message + "\n\r");

          //Write the data in the table
          Iterator i = model.getDataVector().iterator();
          while (i.hasNext()) {
            fw.write(i.next().toString() + "\n\r");
          }

          fw.flush();
        } catch (NullPointerException ex) {
          System.err.println("Unexpected error ocurred " + ex.getMessage());
        } catch (IOException  ioex) {
          System.err.println("Error ocurred: " + ioex.getMessage());
        } finally {
          try {
            if (fw != null) fw.close();
          } catch (IOException  ioex) {
            System.err.println("Error ocurred: " + ioex.getMessage());
          }
        }
      }
    }
  }

  class MyAdjustmentListener implements AdjustmentListener {
    public void adjustmentValueChanged(AdjustmentEvent e) {
      repaint();
    }
  }
}

package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsFinder;

/**
 * Main Interfce, the program starts here
 * @see JFrame
 * @see ActionListener
 */
public class MainInterface extends JFrame implements ActionListener {

  //Names of the columns of the database used by the program
  private final String databases[] = {"ExcludedWords", "FindWords", "Labels"};

  //Conection with the database
  private DataBaseManager database;

  //Interfaces
  private WordsFinderInterface wfi;
  private DataBaseOperationsInterface dbo;

  /**
   * Constructor of the Interface with a BorderLayout
   * Create a new instance of DataBaseManager
   * Create a new instance of WordsFinder
   * Create a new instance of DataBaseOperationsInterface
   * Create a new instance of WordsFinderInterface
   * @see BorderLayout
   * @see DataBaseManager
   * @see WordsFinder
   * @see DataBaseOperationsInterface
   * @see WordsFinderInterface
   */
  public MainInterface () {
    //Initializate classes used in the program --------------------------
    //Database
    database = new DataBaseManager();

    //Interfaces
    dbo = new DataBaseOperationsInterface(database);
    wfi = new WordsFinderInterface(new WordsFinder(database));
    //--------------------------------------------------------------------

    //Configure the interface with a BorderLayout -------------------------
    setTitle("WORDSFINDER");
    setSize(400, 150);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  /**
   * Here are handled all the events activated in this interface
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
  }

  /**
   * Close operations
   * close the connection with the database
   * and dispose the interfaces
   */
  public void close () {
    dbo.setVisible(false);
    dbo.dispose();
    wfi.setVisible(false);
    wfi.dispose();
    database.close();
    System.out.println("Goodbye, see you soon");
  }
}

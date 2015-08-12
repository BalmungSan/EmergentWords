package co.edu.eafit.emergentwords.Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import co.edu.eafit.emergentwords.DataBase.DataBaseManager;
import co.edu.eafit.emergentwords.WordsFinder.WordsFinder;

/**
 * Main Interfce, the program starts here
 * @see JFrame
 * @see ActionListener
 */
public class MainInterface extends JFrame implements ActionListener {

  //Names of the columns of the database used by the program
  private final String names[] = {"All", "Excluded", "Find", "Labels"};
  private final JComboBox columns;

  //Conection with the database
  private final DataBaseManager database;

  //Interfaces
  private final WordsFinderInterface wfi;
  private final DataBaseOperationsInterface dbo;
  private final ScannerInterface sci;

  /**
   * Constructor of the Interface with a GribBagLayout
   * Create a new instance of DataBaseManager
   * Create a new instance of DataBaseOperationsInterface
   * Create a new instance of WordsFinderInterface
   * Create a new instance of ScannerInterface
   * @see DataBaseManager
   * @see DataBaseOperationsInterface
   * @see WordsFinderInterface
   * @see ScannerInterface
   * @see GridBagLayout
   * @see GridBagConstraints
   */
  public MainInterface (DataBaseManager database) {
    //Initializate classes used in the program ---------------------------
    //Database
    this.database = database;

    //Interfaces
    dbo = new DataBaseOperationsInterface(database);
    wfi = new WordsFinderInterface(database);
    sci = new ScannerInterface();
    //---------------------------------------------------------------------

    //Configure the interface with a GribBagLayout ------------------------
    setTitle("EMERGENTWORDS");
    setSize(450, 150);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    //---------------------------------------------------------------------

    //Add components ------------------------------------------------------
    String welcome = "Welcome to EmergentWords, " +
      "Select the action you want to perform";
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(new JLabel(welcome), c);

    JButton dboButton = new JButton("Database\nOperations");
    dboButton.setActionCommand("Database");
    dboButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(dboButton, c);

    columns = new JComboBox(names);
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(columns, c);

    JButton vdbButton = new JButton("View Database");
    vdbButton.setActionCommand("Result");
    vdbButton.addActionListener(this);
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(vdbButton, c);

    JButton findButton = new JButton("Words Finder");
    findButton.setActionCommand("Find");
    findButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(findButton, c);

    JButton sciButton = new JButton("Text Scanner");
    sciButton.setActionCommand("Scanner");
    sciButton.addActionListener(this);
    c.gridx = 1;
    c.gridy = 3;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(sciButton, c);
    //---------------------------------------------------------------------
  }

  /**
   * Here are handled all the events activated in this interface
   * @param evt the event
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    String Action = evt.getActionCommand();
    switch (Action){
    case "Find":
      wfi.setVisible(true);
      break;
    case "Database":
      dbo.setVisible(true);
      break;
    case "Scanner":
      sci.setVisible(true);
      break;
    case "Result":
      getResults();
      break;
    }
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
    sci.setVisible(false);
    sci.dispose();
    database.close();
    System.out.println("Goodbye, see you soon");
  }

  /**
   * Get all the information about the user's selected column
   * from the database,  and show it in a new ResultInterface
   */
  private void getResults () {
    List<String[]> rows;
    DefaultTableModel information = new DefaultTableModel();
    information.addColumn("WORD");

    if (columns.getSelectedItem().toString().equals("All")) {
      information.addColumn("EXCLUDED");
      information.addColumn("FIND");
      information.addColumn("LABELS");

      //rows = List<{'word', 'excluded', 'find', 'lables'}>
      rows = database.getTable();
    } else {
      String column = columns.getSelectedItem().toString();
      information.addColumn(column);

      //rows = List<{'word', 'data'}> where the selected column is <> 'FALSE'
      rows = database.getAll(column);
    }

    for (String[] row : rows) {
      //Add every row in the database to information
      information.addRow(row);
    }

    ResultInterface results = new ResultInterface(information, "DATABASE");
    results.setVisible(true);
  }
}

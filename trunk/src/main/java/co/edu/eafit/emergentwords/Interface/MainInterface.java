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
    //Initializate classes used in the program ---------------------------
    //Database
    database = new DataBaseManager();

    //Interfaces
    dbo = new DataBaseOperationsInterface(database);
    wfi = new WordsFinderInterface(new WordsFinder(database));
    //---------------------------------------------------------------------

    //Configure the interface with a BorderLayout -------------------------
    setTitle("EMERGENTWORDS");
    setSize(420, 120);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLayout(new BorderLayout());
    //---------------------------------------------------------------------

    //Add components ------------------------------------------------------
    String welcome = "Welcome to WordsFinder, " +
      "Select the action you want to perform";
    add(new JLabel(welcome), BorderLayout.NORTH);

    JButton findButton = new JButton("Words Finer");
    findButton.setActionCommand("Find");
    findButton.addActionListener(this);
    add(findButton, BorderLayout.EAST);

    JButton dboButton = new JButton("Database Operations");
    dboButton.setActionCommand("Database");
    dboButton.addActionListener(this);
    add(dboButton, BorderLayout.WEST);

    columns = new JComboBox(names);
    add(columns, BorderLayout.CENTER);

    JButton vdbButton = new JButton("View Database");
    vdbButton.setActionCommand("Result");
    vdbButton.addActionListener(this);
    add(vdbButton, BorderLayout.SOUTH);
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
    database.close();
    System.out.println("Goodbye, see you soon");
  }

  /**
   * Get all the information about the user's selected column
   *from the database,  and show it in a new ResultInterface
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

package com.eafit.lmejias3.wordsfinder.Interface;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsFinder;

/**
 * Interface for the user to select a file and the mode of searching
 * @see JFrame
 * @see ActionListener
 */
public class WordsFinderInterface extends JFrame implements ActionListener {

  //WordsFinder class to pass the file selected by the user
  WordsFinder wordsfinder;

  //Components of the interface
  JTextField path;
  ButtonGroup modes;

  //Filters of extensions used by the program
  FileNameExtensionFilter filter;

  //File selected by the user
  File file;

  /**
   * Constructor of the Interface using a GridBagLayout
   * @param wf Reference to WordsFinder instance
   * @see GridBagLayout
   * @see GridBagConstrains
   * @see WordsFinder
   */
  public WordsFinderInterface (WordsFinder wf) {
    //Initializate wordsfinder
    wordsfinder = wf;

    //Initializate the extensions filter
    filter = new FileNameExtensionFilter("Text documents", "doc", "docx",
                                         "pdf", "txt");

    //Configure the interface with a GribBagLayout -------------------------
    setTitle("SETTINGS");
    setSize(400, 150);
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    //----------------------------------------------------------------------

    //Add componets --------------------------------------------------------
    JLabel file = new JLabel("File");
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(file, c);

    path = new JTextField("");
    path.setEditable(false);
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 4;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(path, c);

    JButton open = new JButton("Open");
    open.setActionCommand("Open");
    open.addActionListener(this);
    c.gridx = 4;
    c.gridy = 1;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(open, c);

    JLabel mode = new JLabel("Mode");
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mode, c);


    //Button Group -----------------------------------------------
    JRadioButton find = new JRadioButton("Find", true);
    find.setActionCommand("F");
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(find, c);

    JRadioButton excluded = new JRadioButton("Excluded", false);
    excluded.setActionCommand("E");
    c.gridx = 2;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(excluded, c);

    JRadioButton labels = new JRadioButton("Labels", false);
    labels.setActionCommand("L");
    c.gridx = 3;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(labels, c);

    modes = new ButtonGroup();
    modes.add(find);
    modes.add(excluded);
    modes.add(labels);
    //------------------------------------------------------------

    JButton search = new JButton("Search");
    search.setActionCommand("Search");
    search.addActionListener(this);
    c.gridx = 4;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(search, c);
    //----------------------------------------------------------------------
  }

  /**
   * Here are handled all the events activated in this interface
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    String Action = evt.getActionCommand();
    switch (Action){
    case "Open":
      //Open a file choser for the user select the file
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        file = chooser.getSelectedFile();
        path.setText(file.getName());
      }
      break;
    case "Search":
      wordsfinder.findWords(modes.getSelection().getActionCommand(),
                            file.getAbsolutePath());

      String ms = "The results of the searching are the following";
      ResultInterface ri = new ResultInterface(wordsfinder.getResults(), ms);
      ri.setVisible(true);
      break;
    }
  }
}

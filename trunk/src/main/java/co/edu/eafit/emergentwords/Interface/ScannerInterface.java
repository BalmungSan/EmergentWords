package co.edu.eafit.emergentwords.Interface;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import co.edu.eafit.emergentwords.WordsFinder.WordsScanner;
import co.edu.eafit.emergentwords.WordsFinder.FileOpener;

/**
 * Interface for the user to select a file and the mode of searching
 * @see JFrame
 * @see ActionListener
 */
public class ScannerInterface extends JFrame implements ActionListener {

  //Components of the interface
  private final JTextField path, target, range;

  //Filters of extensions used by the program
  private final FileNameExtensionFilter filter;

  //File selected by the user
  private File file;

  /**
   * Constructor of the Interface using a GridBagLayout
   * @see GridBagLayout
   * @see GridBagConstraints
   * @see WordsScanner
   * @see FileOpener
   */
  public ScannerInterface () {
    //Initializate the extensions filter
    filter = new FileNameExtensionFilter("Text documents", "doc", "docx",
                                         "pdf", "txt");

    //Configure the interface with a GribBagLayout -------------------------
    setTitle("WORDS SCANNER");
    setSize(400, 150);
    setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    //----------------------------------------------------------------------

    //Add componets --------------------------------------------------------
    JLabel filePath = new JLabel("File");
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(filePath, c);

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
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 2;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(open, c);

    JLabel targetLabel = new JLabel("Word to search");
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(targetLabel, c);

    target = new JTextField("");
    path.setEditable(true);
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(target, c);

    JLabel rangeLabel = new JLabel("Range of extraction");
    c.gridx = 2;
    c.gridy = 2;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(rangeLabel, c);

    range = new JTextField("");
    path.setEditable(true);
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(range, c);

    JButton search = new JButton("Search");
    search.setActionCommand("Search");
    search.addActionListener(this);
    c.gridx = 4;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 2;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(search, c);
    //----------------------------------------------------------------------
  }

  /**
   * Here are handled all the events activated in this interface
   * @param evt The event
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
      chooser.setDialogTitle("Select the file to analyze");
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        file = chooser.getSelectedFile();
        path.setText(file.getName());
      }
      break;
    case "Search":
      WordsScanner scanner
        = new WordsScanner(file.getName(), FileOpener.open(file),
                           target.getText(), range.getText());

      scanner.execute();
      break;
    }
  }
}

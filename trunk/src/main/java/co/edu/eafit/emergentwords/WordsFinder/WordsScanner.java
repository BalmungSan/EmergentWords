package co.edu.eafit.emergentwords.WordsFinder;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.eafit.emergentwords.Interface.ResultInterface;

/**
 * This class scans the text of the file to find the excerpts where a word
 * specified by the user appear
 */
public class WordsScanner extends SwingWorker<Void, Void> {
  //The range of extraction
  private final int range;

  //The word to search and extract the text
  private final String target;

  //The vector with all the words in the file
  private final Vector<String> text;

  //Table model to store the results and prit them in ResultsInterface
  private final DefaultTableModel results;

  //The message of the search
  private final String ms;

  //Variables for the progress bar
  private final ProgressMonitor pbar;
  private final int total;
  private int counter;

  /**
   * Constructor of the class, initialize everything
   * @param filename The name of the file selected by the user
   * @param words An array with all the words in the file
   * @param target The word to find
   * @param range The amount of words to extract
   */
  public WordsScanner (String filename, String[] words, String target,
                       String range) throws NumberFormatException{

    //Initilize worker variables
    this.target = target.toLowerCase();
    this.range = Integer.parseInt(range);
    results = new DefaultTableModel();
    results.addColumn("Excerpts");

    //Create a vector from the words array
    text = new Vector<>(Arrays.asList(words));

    //Set the message
    ms = filename + " - SCANNER (" + target + " # " + range + ")";

    //Initialize progress monitor bar variables
    counter = 0;
    total = text.size();

    //Initialization of progress monitor from 0 to the length of the text
    pbar = new ProgressMonitor(null, "Searching words progress",
                               "Initializing", 0, total);
  }

  @Override
  public synchronized Void doInBackground() {
    int index = text.indexOf(target);
    int lowerbound;
    int upperbound;

    while (index != -1) {
      if (!isCancelled()) {
        //Get the lower bound to extract, making sure is grater than 0
        lowerbound = index - range;
        if (lowerbound < 0) lowerbound = 0;

        //Get the upper bound to extract, making sure is lower than the size
        upperbound = index + range + 1;
        if (upperbound > total) upperbound = total - 1;

        //Get the excerpt found and add it to the results
        Object row [] = {text.subList(lowerbound, upperbound).toString()};
        results.addRow(row);

        //Update the progress monitor
        update(index);

        //Get the next index
        index = text.indexOf(target, upperbound);
      } else {
        //If the user have cancelled the execution, break
        break;
      }
    }

    pbar.close();
    return null;
  }

  @Override
  public synchronized void done() {
    if(!isCancelled()) {
      //Show the results in the interface
      ResultInterface ri = new ResultInterface(results, ms);
      ri.setVisible(true);
    }
  }

  /**
   * Update the progress of the progress monitor
   * @see ProgressMonitor
   * @param index The current position of the text analyser
   */
  private synchronized void update (int index) {
    //Check if the user has pressed the button 'Cancel'
    if (pbar.isCanceled()) {
      //If was pressed, cancel the execution
      this.cancel(true);

      System.err.println("You have cancelled the searching");

      //custom title, warning icon
      JOptionPane.showMessageDialog(null, "You have cancelled the searching",
                                    "Warning", JOptionPane.WARNING_MESSAGE);
    } else {
      //If not, update the progress
      counter = index * 100 / total;
      pbar.setProgress(counter);
      pbar.setNote("Operation is " + counter + "% complete");
    }
  }
}

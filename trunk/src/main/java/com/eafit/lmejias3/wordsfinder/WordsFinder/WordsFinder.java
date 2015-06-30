package com.eafit.lmejias3.wordsfinder.WordsFinder;

import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.Charset;
import com.eafit.lmejias3.wordsfinder.WordsFinder.WordsManager.*;
import com.eafit.lmejias3.wordsfinder.DataBase.DataBaseManager;
import org.apache.commons.io.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * This class opens the file selected by the user and read it word by word
 */
public class WordsFinder {

  //WordsManager Class reference to send all the found words
  private WordsManager mywm;

  //Table model to store the results and prit them in ResultsInterface
  private DefaultTableModel results;

  //Conection with database
  private DataBaseManager database;

  //Reserved chars to remove from every word
  private final String rc[]
    = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "\"", "\'", "\\",
       "(", ")", "{", "}", "[", "]", ",", ";", ".", ":", "&", "|", "°", "¬",
       "_", "-", "!", "¡", "?", "¿", "#", "$", "%", "~", "`", "´", "¨", "^",
       "<", ">", "+", "*", "/", "="};

  /**
   * Constructor of the class
   * @param database Reference to a instance of DataBaseManager
   * @see DataBaseManager
   */
  public WordsFinder (DataBaseManager database) {
    this.database = database;
  }

  /**
   * Here start all the logic of the program
   * @param mode Flag that indicate the mode selected by the user
   * @param file File the user chose
   */
  public void findWords (String mode, File file)
    throws userCancellationException {

    //Configure app to work with user selected mode
    switch (mode) {
    case "F":
      //The mode is to find specific words
      mywm = new WMFind(database);
      break;
    case "E":
      //The mode is to find all words
      mywm = new WMExclude(database);
      break;
    case "L":
      //The mode is to find labels
      mywm = new WMLabels(database);
      break;
    }

    //Open the file
    openfile(file);

    //Sort the results
    sort(mywm.getfound());
  }

  /**
   * Clear the data on WordsManager to free space
   */
  public void clear () {
    mywm.clear();
  }

  /**
   * Return the results of the search
   * @return TableModel with the results
   * @see DefaultTableModel
   */
  public DefaultTableModel getResults() {
    return results;
  }

  /**
   * Detect what type of file is ('.doc', '.docx', '.pdf' or binary text)
   * @param file File the user chose
   */
  private void openfile (File file) throws userCancellationException {

    String ext = FilenameUtils.getExtension(file.getName()).toLowerCase();
    String text = "";
    InputStream is = null;

    try {
      is = FileUtils.openInputStream(file);

      switch (ext) {
      case "doc":
        //Is a word file ('.doc')
        text = opendoc(is);
        break;
      case "docx":
        //Is a word file ('.docx')
        text = opendocx(is);
        break;
      case "pdf":
        //Is a pdf file
        text = openpdf(is);
        break;
      default :
        //Is a binary file with any extension
        text = openbin(is);
        break;
      }

      getWords(text);
    } catch (IOException ioex) {
      System.err.println("Can not open file: " + file.getAbsolutePath());
    }
  }

  /**
   * Open a .doc file
   * @param is Input Stream from the file the user chose
   * @return All the text in the file
   */
  private String opendoc (InputStream is) throws IOException {
    HWPFDocument doc = new HWPFDocument(is);
    WordExtractor extractor = new WordExtractor(doc);

    return extractor.getText();
  }

  /**
   * Open a .docx file
   * @param is Input Stream from the file the user chose
   * @return All the text in the file
   */
  private String opendocx (InputStream is) throws IOException {
    XWPFDocument docx = new XWPFDocument(is);
    XWPFWordExtractor extractor = new XWPFWordExtractor(docx);

    return extractor.getText();
  }

  /**
   * Open a .pdf file
   * @param is Input Stream from the file the user chose
   * @return All the text in the file
   */
  private String openpdf (InputStream is) throws IOException {
    PDFParser parser = new PDFParser(is);
    parser.parse();

    COSDocument cosDoc = parser.getDocument();
    PDFTextStripper pdfStripper = new PDFTextStripper();
    PDDocument pdDoc = new PDDocument(cosDoc);

    pdfStripper.setStartPage(1);
    pdfStripper.setEndPage(pdDoc.getNumberOfPages());

    return pdfStripper.getText(pdDoc);
  }

  /**
   * Open a binary file usually .txt
   * @param is Input Stream from the file the user chose
   * @return All the text in the file
   */
  private String openbin (InputStream is) throws IOException {
    return IOUtils.toString(is, Charset.forName("UTF-8"));
  }

  /**
   * Breaks all the content of the file in words separated by blank spaces
   * sents that words to words managar class
   * and show a progres monitor for the user view the  data porcesed
   * @param contents All the data in the file
   * @see ProgressMonitor
   */
  private void getWords (String contents) throws userCancellationException {

    //Remove every reserved char from the text
    for (String r : rc) contents = StringUtils.replace(contents, r, " ");

    //Create a new task and execute it
    Task task = new Task();
    task.setText(contents);
    task.execute();

    //BAD BLOCK
    synchronized(task) {
      try {
        //Wait until task is finished
        task.wait();
      } catch (InterruptedException ex) {
        //If the task have been cancelled by the user
        throw new userCancellationException("You have canceled " +
                                            "the searching");
      }
    }
  }

  /**
   * Sort a map<String key, Integer value> by value
   * remark: This code was taken and adapted from:
   *         http://www.mkyong.com/java/how-to-sort-a-map-in-java/
   * @param unsortMap Map to sort by value
   * @see Map
   */
  private void sort (Map<String, Integer> unsortMap) {

    // Convert Map to List
    List<Map.Entry<String, Integer>> list =
      new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2) {
          return (o1.getValue()).compareTo(o2.getValue());
        }
      });

    // Save the sorted list in results table
    results = new DefaultTableModel();
    results.addColumn("WORD");
    results.addColumn("TIMES");
    Object[] row;
    for (Map.Entry<String, Integer> entry : list) {
      row = new Object[2];
      row[0] = entry.getKey();
      row[1] = entry.getValue();
      results.addRow(row);
    }
  }

  /**
   * Class for the progress monitor
   * this class will be executed in a different thread
   */
  class Task extends SwingWorker<Void, Void> {

    //Variables for the progress bar
    private ProgressMonitor pbar;
    private int counter;
    private int total;

    //Text to analyze
    private String text;

    @Override
    public Void doInBackground() {
      counter = 0;
      total = text.length();

      //Initialization of progress monitor from 0 to the length of the text
      pbar = new ProgressMonitor(null, "Searching words progress",
                                 "Initializing", 0, total);

      //For every word in the text
      //the words are separated by a blank space [ \t\n\x0B\f\r]
      for (String word : text.split("\\s+")) {
        if (!isCancelled()) {
          //If the user have not cancelled the execution, do...

          System.out.println(word);

          //Send the word to the instance of the words manager class
          mywm.addWord(word.toLowerCase());

          //Update the progress monitor
          update(word.length());

          //Sleep for one second, to check the progress monitor
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ex) {}
        } else {
          //If the user have cancelled the execution, break
          break;
        }
      }

      pbar.close();
      notifyAll();
      return null;
    }

    @Override
    public void done() {
      pbar.setProgress(0);
      notifyAll();
    }

    /**
     * Update the progress of the progress monitor
     * @param pro the length of the word operated
     * @see ProgressMonitor
     */
    private void update (int pro) {
      //Check if the user has pressed the button 'Cancel'
      if (pbar.isCanceled()) {
        //If was pressed, cancel the execution
        pbar.close();
        this.cancel(true);
        notifyAll();
      } else {
        //If not, update the progress
        pbar.setProgress(counter);
        pbar.setNote("Operation is " + counter + "% complete");
        counter += pro * 100 / total;
      }
    }

    /**
     * Set the texto to process
     */
    public void setText (String text) {
      this.text = text;
    }
  }
}

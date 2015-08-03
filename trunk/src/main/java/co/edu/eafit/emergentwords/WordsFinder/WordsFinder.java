package co.edu.eafit.emergentwords.WordsFinder;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.Charset;
import co.edu.eafit.emergentwords.WordsFinder.WordsManager.*;
import co.edu.eafit.emergentwords.DataBase.DataBaseManager;
import co.edu.eafit.emergentwords.Interface.ResultInterface;
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
  private final DataBaseManager database;

  //The message of the search
  private String ms;

  //Reserved chars to remove from every word
  private final String rc[]
    = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "\"", "\'", "\\",
       "(", ")", "{", "}", "[", "]", ",", ";", ".", ":", "&", "|", "°", "¬",
       "_", "-", "!", "¡", "?", "¿", "#", "$", "%", "~", "`", "´", "¨", "^",
       "<", ">", "+", "*", "/", "=", "@", "ł", "€", "¶", "ŧ", "←", "↓", "→",
       "ø", "þ", "æ", "ß", "đ", "ŋ", "ħ", "«", "»", "“", "”", "µ"};

  /**
   * Constructor of the class
   * @param database Reference to a instance of DataBaseManager
   * @see DataBaseManager
   */
  public WordsFinder (DataBaseManager database) {
    this.database = database;
  }

  /**
   * Clear the data on WordsManager to free space
   */
  public void clear () {
    mywm.clear();
  }

  /**
   * Here start all the logic of the program
   * @param mode Flag that indicate the mode selected by the user
   * @param file File the user chose
   */
  public void findWords (String mode, File file) {

    //Add the file name to the message
    ms = file.getName();

    //Configure app to work with user selected mode
    switch (mode) {
    case "F":
      //The mode is to find specific words
      mywm = new WMFind(database);
      ms = ms + " - FIND";
      break;
    case "E":
      //The mode is to find all words
      mywm = new WMExclude(database);
      ms = ms + " - EXCLUDED";
      break;
    case "L":
      //The mode is to find labels
      mywm = new WMLabels(database);
      ms = ms + " - LABELS";
      break;
    }

    //Open the file
    openfile(file);
  }

  /**
   * Detect what type of file is ('.doc', '.docx', '.pdf' or binary text)
   * @param file File the user chose
   */
  private void openfile (File file) {

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
   * Create a {@link HWPFDocument} from the InputStream of the file
   * and then create a {@link WordExtractor}
   * finally gets a String with all the data in the file with
   * {@link  WordExtractor#getText getText} method
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
   * Create a {@link XWPFDocument} from the InputStream of the file
   * and then create a {@link XWPFWordExtractor}
   * finally gets a String with all the data in the file with
   * {@link  XWPFWordExtractor#getText getText} method
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
   * First create a {@link PDFParser} with the InoutStream of the file
   * Then create a {@link COSDocument} from the parser, and a
   * {@link PDDocument} from this.
   * Finally create a {@link PDFTextStripper} and get a String of the file
   * with {@link PDFTextStripper#getText getText} method
   * @param is Input Stream from the file the user chose
   * @return All the text in the file
   * @see PDFBox
   */
  private String openpdf (InputStream is) throws IOException {
    PDFParser parser = new PDFParser(is);
    parser.parse();

    COSDocument cosDoc = parser.getDocument();
    PDDocument pdDoc = new PDDocument(cosDoc);

    PDFTextStripper pdfStripper = new PDFTextStripper();
    pdfStripper.setStartPage(1);
    pdfStripper.setEndPage(pdDoc.getNumberOfPages());

    return pdfStripper.getText(pdDoc);
  }

  /**
   * Open a binary file usually .txt
   * Converts the InputStream of the file to a string using
   * {@link IOUtils#toString toString} with UTF-8 encoding
   * @param is Input Stream from the file the user chose
   * @return All the text in the file
   * @see IOUtils
   * @see Charset
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
  private void getWords (String contents) {

    //Remove every reserved char from the text
    for (String r : rc) contents = StringUtils.replace(contents, r, " ");

    //Create a new task and execute it
    Task task = new Task(contents, this);
    task.execute();
  }

  /**
   * Call when the task has done
   */
  private void finish () {
    //Sort the results
    sort(mywm.getfound());

    //Show the results in the interface
    ResultInterface ri = new ResultInterface(results, ms);
    ri.setVisible(true);

    clear();
  }

  /**
   * Sort a map<String key, Integer value> by value (high to low)
   * remark: This code was taken and adapted from:
   *         http://www.mkyong.com/java/how-to-sort-a-map-in-java/
   * @param unsortMap Map to sort by value
   * @see Map
   */
  private void sort (Map<String, Integer> unsortMap) {

    // Convert Map to List
    List<Map.Entry<String, Integer>> list =
      new LinkedList<>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2) {
          return (o2.getValue()).compareTo(o1.getValue());
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
    private final String text;

    //Reference to my my father
    private final WordsFinder father;

    /**
     * Constructor of this task
     * Set the text to process
     * @param text String with all the data in the file
     * @param father Reference to the instance of {@link WordsFinder}
     * that have call us
     */
    public Task (String text, WordsFinder father) {
      this.text = text;
      this.father  = father;
    }

    @Override
    public synchronized Void doInBackground() {
      String words[] = text.split("\\s+");
      counter = 0;
      total = words.length;

      //Initialization of progress monitor from 0 to the length of the text
      pbar = new ProgressMonitor(null, "Searching words progress",
                                 "Initializing", 0, total);

      //For every word in the text
      //the words are separated by a blank space [ \t\n\x0B\f\r]
      for (String word : words) {
        if (!isCancelled()) {
          //If the user have not cancelled the execution, do...

          //Send the word to the instance of the words manager class
          mywm.addWord(word.toLowerCase());

          //Update the progress monitor
          update();
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
      //Tell WordsFinder the task has been completed or cancelled
      if(!isCancelled()) father.finish();
      else father.clear();
    }

    /**
     * Update the progress of the progress monitor
     * @see ProgressMonitor
     */
    private synchronized void update () {
      //Check if the user has pressed the button 'Cancel'
      if (pbar.isCanceled()) {
        //If was pressed, cancel the execution
        this.cancel(true);
        System.err.println("You have cancelled the searching");
      } else {
        //If not, update the progress
        pbar.setProgress(counter);
        pbar.setNote("Operation is " + counter * 100 / total + "% complete");
        counter ++;
      }
    }
  }
}

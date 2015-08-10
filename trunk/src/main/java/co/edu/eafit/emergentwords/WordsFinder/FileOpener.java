package co.edu.eafit.emergentwords.WordsFinder;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.nio.charset.Charset;
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
 * This class opens the file selected by the user
 * and tranforms its contents into an arry of strings with all the words
 */
public class FileOpener {
  //Reserved chars to remove from every word
  private final static String rc[]
    = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "\"", "\'", "\\",
       "(", ")", "{", "}", "[", "]", ",", ";", ".", ":", "&", "|", "°", "¬",
       "_", "-", "!", "¡", "?", "¿", "#", "$", "%", "~", "`", "´", "¨", "^",
       "<", ">", "+", "*", "/", "=", "@", "ł", "€", "¶", "ŧ", "←", "↓", "→",
       "ø", "þ", "æ", "ß", "đ", "ŋ", "ħ", "«", "»", "“", "”", "µ"};


  /**
   * Detect what type of file is ('.doc', '.docx', '.pdf' or binary text)
   * Breaks all the content of the file in words separated by blank spaces
   * @param file File the user chose
   * @return An arry with all the words
   */
  public static String[] open (File file) {

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
    } catch (IOException ioex) {
      System.err.println("Can not open file: " + file.getAbsolutePath());
    }

    //Remove every reserved char from the text
    for (String r : rc) text = StringUtils.replace(text, r, " ");


    return text.toLowerCase().split("\\s+");
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
  private static String opendoc (InputStream is) throws IOException {
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
  private static String opendocx (InputStream is) throws IOException {
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
  private static String openpdf (InputStream is) throws IOException {
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
  private static String openbin (InputStream is) throws IOException {
    return IOUtils.toString(is, Charset.forName("UTF-8"));
  }
}

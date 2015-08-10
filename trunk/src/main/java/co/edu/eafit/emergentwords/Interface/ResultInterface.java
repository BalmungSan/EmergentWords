package co.edu.eafit.emergentwords.Interface;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

/**
 * Interface to show the user the results of the searching
 * either from the database or a file
 * @see JFrame
 */
public class ResultInterface extends JFrame implements ActionListener {

  //Model of table to save the results
  private final DefaultTableModel model;
  private final String message;

  //Extension filter for the file to save the results
  private final FileNameExtensionFilter filter;

  /**
   * Constructor of the class using a BorderLayout
   * create a JTable with the data to show at the user
   * @param model Model that contains the data of the table
   * @param message Text to show to the user
   * @see BorderLayout
   * @see JTable
   */
  public ResultInterface (DefaultTableModel model, String message) {
    filter = new FileNameExtensionFilter("Excel Documents", "xlsx");

    //Get the actual date and hour
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    this.message = message + " - " + dateFormat.format(date);
    this.model = model;

    setTitle("RESULTS");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(500, 500);

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
   * @param evt the event
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    String Action = evt.getActionCommand();

    if (Action.equals("Save")) {
      //Save the information

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(filter);
      fileChooser.setDialogTitle("Specify a file to save");
      int userSelection = fileChooser.showSaveDialog(this);

      if (userSelection == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        FileOutputStream outFile = null;
        XSSFWorkbook workbook = null;

        try {
          //Open the excel file to store the results

          //Get the workbook instance for XLS file
          workbook = new XSSFWorkbook(new FileInputStream(file));
          //Get first sheet from the workbook
          XSSFSheet sheet = workbook.getSheetAt(0);

          //Variables to use
          XSSFRow row = null;
          XSSFCell cell = null;
          int rowNum = 0, cellNum = 0;

          //Iterate for every row to find the first blank row
          for (Row r : sheet) rowNum++;
          row = sheet.createRow(rowNum);

          //Now save all the results in very cell of the row
          cell = row.createCell(cellNum++, 1);
          cell.setCellValue(message);
          Iterator i = model.getDataVector().iterator();
          while (i.hasNext()) {
            cell = row.createCell(cellNum++, 1);
            cell.setCellValue(i.next().toString());
          }

          outFile = new FileOutputStream(file);
          workbook.write(outFile);
          System.out.println("Excel written successfully..");
        } catch (NullPointerException ex) {
          System.err.println("Unexpected error ocurred " + ex.getMessage());
        } catch (IOException  ioex) {
          System.err.println("Error ocurred: " + ioex.getMessage());
        } finally {
          try {
            if (workbook != null) workbook.close();
            if (outFile != null) outFile.close();
          } catch (IOException ioex) {
            System.err.println("Error ocurred: " + ioex.getMessage());
          }
        }
      }
    }
  }

  class MyAdjustmentListener implements AdjustmentListener {
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
      repaint();
    }
  }
}

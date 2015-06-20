package com;

import com.eafit.lmejias3.wordsfinder.Interface.MainInterface;
import javax.swing.*;
import javax.swing.UIManager.*;

/**
 * @project: WordsFinder; Java application to analyze texts
 *           to find the most repetitive words
 * @version: 0.1.0 (20/06/2015) "SANPSHOT"
 * @author: Luis Miguel Mejía Suárez "BalmungSan" (github.com/BalmungSan)
 *
 * @class: Main class all the code logic start here
 */
public class MainWordsFinder {

  /**
   * @method main method all the code logic runs here
   * @param args: arguments passed to the program
   */
  public static void main (String[] args) {
    //Enabling the Nimbus Look and Feel------------------------------------
    try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException | InstantiationException |
             IllegalAccessException | UnsupportedLookAndFeelException e) {
      System.out.println("Can not Enable Nimbus Interface Style");
    }
    //---------------------------------------------------------------------

    MainInterface program = new MainInterface();
    program.setVisible(true);
  }
}
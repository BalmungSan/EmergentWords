/**   Copyright 2015 EAFIT (http://www.eafit.edu.co/Paginas/index.aspx)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package co;

import co.edu.eafit.emergentwords.Interface.MainInterface;
import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.event.*;

/**
 *Project: EmergentWords; Java application to analyse texts
 *
 *Version: 1.1.2-(3_08_2015)-GA
 *Author and Researcher: Luis Miguel Mejía Suárez "BalmungSan"
 *                       (https://github.com/BalmungSan)
 * Main Researcher: Juan Carlos Montalvo Rodrigez
 * (http://scienti1.colciencias.gov.co:8081/cvlac/visualizador/generarCurriculoCv.do?cod_rh=0001021150)
 *
 * Main class all the code logic start here
 */
public class MainWordsFinder {

  /**
   * Main method all the code logic runs here
   * @param args arguments passed to the program
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
    //Create our own WindowAdpater to override the method windowClosing
    program.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent event) {
          program.close();
          program.dispose();
          System.exit(0);
        }
      });
    //----------------------------------------------------------------------
    program.setVisible(true);
  }
}

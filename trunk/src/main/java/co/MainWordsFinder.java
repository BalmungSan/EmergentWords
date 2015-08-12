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
import co.edu.eafit.emergentwords.DataBase.DataBaseManager;
import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project: EmergentWords; Java application to analyse texts
 * (https://github.com/BalmungSan/EmergentWords/tree/office-version)
 *
 * Version: 1.1.6-(11_08_2015)-Amended
 *
 * Author and Researcher: Luis Miguel Mejía Suárez "BalmungSan"
 * (https://github.com/BalmungSan)
 * Main Researcher: Juan Carlos Montalvo Rodrigez
 * (http://scienti1.colciencias.gov.co:8081/cvlac/visualizador/generarCurriculoCv.do?cod_rh=0001021150)
 *
 * Main class all the code logic start here
 * Ask the user for the MySQL Server, User and Password
 * And create a new instance of DataBaseManager connected to that database
 * To create a new Instance of MainInterface to start the program
 *
 * @see JFrame
 * @see DataBaseManager
 * @see MainInterface
 */
public class MainWordsFinder extends JFrame implements ActionListener {

  //Text fields for the user input the mysql login options -----------------
  private final JTextField mysqlServer;
  private final JTextField mysqlUser;
  private final JPasswordField mysqlPassword;
  //------------------------------------------------------------------------

  /**
   * Constructor of the Interface with a GribBagLayout
   * @see GridBagLayout
   * @see GridBagConstraints
   */
  public MainWordsFinder () {
    //Configure the interface with a GridBagLayout --------------------------
    setTitle("SETTINGS");
    setSize(350, 200);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    //----------------------------------------------------------------------

    //Add components -------------------------------------------------------
    String message = "Login to EmergentWords";
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 3;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(new JLabel(message), c);

    JLabel mysqlServerLabel
      = new JLabel("Please input the IP address of your MySQL server");
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mysqlServerLabel, c);

    mysqlServer = new JTextField("localhost");
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 3;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mysqlServer, c);

    JLabel mysqlUserLabel
      = new JLabel("Please input the user of the MySQL server to login");
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mysqlUserLabel, c);

    mysqlUser = new JTextField("");
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 3;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mysqlUser, c);

    JLabel mysqlPasswordLabel = new JLabel("Please input the password");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mysqlPasswordLabel, c);

    mysqlPassword = new JPasswordField();
    mysqlPassword.setEchoChar('*');
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 3;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(mysqlPassword, c);

    JButton loginButton = new JButton("Login");
    loginButton.setActionCommand("Login");
    loginButton.addActionListener(this);
    c.gridx = 2;
    c.gridy = 7;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(loginButton, c);
    //----------------------------------------------------------------------
  }

  /**
   * Main method all the code logic runs here
   * @param args arguments passed to the program
   */
  public static void main (String[] args) {
    //Enabling the Nimbus Look and Feel-------------------------------------
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
    //----------------------------------------------------------------------

    MainWordsFinder main = new MainWordsFinder();
    main.setVisible(true);
  }

  /**
   * Here are handled all the events activated in this interface
   * Attempt to login in to the database and show the user interface
   * @param evt the event
   * @see ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    String Action = evt.getActionCommand();
    switch (Action){
    case "Login":
      DataBaseManager database
        = new DataBaseManager(mysqlServer.getText(), mysqlUser.getText(),
                              new String(mysqlPassword.getPassword()));

      MainInterface program = new MainInterface(database);
      //Create our own WindowAdpater to override the method windowClosing
      program.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent event) {
            program.close();
            program.dispose();
            System.exit(0);
          }
        });
      //--------------------------------------------------------------------

      this.setVisible(false);
      program.setVisible(true);
      break;
    }
  }
}

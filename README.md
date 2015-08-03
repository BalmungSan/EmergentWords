   Copyright 2015 EAFIT (http://www.eafit.edu.co/Paginas/index.aspx)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this project except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

Project: WordsFinder; Java application to analyse texts
Version: 1.1.1-(30_07_2015)-GA
Author: Luis Miguel Mejía Suárez "BalmungSan" (https://github.com/BalmungSan)

Description:
   WordsFinder is a simple program with a intuitive graphical interface which allow the user to analyse a text file
   Supports plain text, doc or docx files and pdf files.

   The program have the following usage:
    *The user can find the most repetitive words in the file, excluding some words the user don't want
    *The user can search for specific words in the file
    *The user can analyse the file in base of some labels
	*The user can extract pieces of text in base of a specific word
    *The user can personalize the database which the program works

   This project use the following dependencies:
    Apache Maven to compile the program (https://maven.apache.org/)
    Apache POI to work with doc and docx files (https://poi.apache.org/)
    Apache PDFBox to work with pdf files (https://pdfbox.apache.org/)
    Apache CommonsIO to work IO Operations (https://commons.apache.org/proper/commons-io/)
    Apache Commons Lang for String operations (https://commons.apache.org/proper/commons-lang/)
    MySQL Java Connector to connect with the database (http://dev.mysql.com/doc/connector-j/en/)

Install:
   Note:
    In order to install wordsfiner on your computer you must have installed Maven, Java Runtime Environment (jre) 8 and sql server running.
    And Internet connection.

   Installation steps (Linux):
    1 Import the words.sql file into any sql database

    2 With any text editor, open the file 'trunk/src/main/java/co/edu/eafit/wordsfinder/DataBase/DataBaseManager.java'
      And modify the lines 16, 19, 22 with your own path, user and password for the database respectively. 

    3 Change to the directory 'trunk' (where is the 'pom.xml' file) and execute the comand 'mvn package'
      This will create a Jar file in the directory 'trunk/target', the file will have the following name 'wordsfinder-version-(date)-name.jar'.

    4 Finally copy the Jar wherever you want. 
	
	Note: For Windows machines you could use the installer provided in this repository (WindowsInstaler.vbs)

   ENJOY
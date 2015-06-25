Project: WordsFinder; Java application to analyze texts for the most repetitive words
Version: 0.1.5 (25/06/2015) "SNAPSHOT"
Author: Luis Miguel Mejía Suárez "BalmungSan" (https://github.com/BalmungSan)

Description:
   WordsFinder is a simple program with a intuitive graphical interface which allow the user to analyze a text file
   Supports plain text, doc or docx files and pdf files.

   The program have the following usage:
    *The user can find the most repetitive words in the file, excluding some words the user don't want
    *The user can search for specific words in the file
    *The user can analyze the file in base of some labels
    *The user can personalize the database which the program works

   This project use the following dependencies:
    Apache Maven to compile the program
    Apache POI to work with doc and docx files
    Apache PDFBox to work with pdf files
    Apache CommonsIO to work IO Operations
    Apache Commons Lang for String operations
    PHPMyAdmin to the database management

Install:
   Note:
    In order to install wordsfiner on your computer you must have installed Maven, Java Runtime Environment (jre) 8 and PhpMyAdmin.
    And Internet connection.

   Installation steps:
    1 Open PhpMyAdmin on any internet browser, it could be by localhost or any other server that you preffer
      chose or create a database and export the file 'words.sql' to create the table 'words'.

    2 With any text editor, open the file 'trunk/src/main/java/com/eafit/lmejias3/wordsfinder/DataBase/DataBaseManager.java'
      And modify the lines 16, 19, 22 with your own path, user and password for the database respectively. 

    3 Change to the directory 'trunk' (where is the 'pom.xml' file) and execute the comand 'mvn package'
      This will create a Jar file in the directory 'trunk/target', the file will have the following name 'wordsfinder-version-(date)-name.jar'.

    4 Finally copy the Jar wherever you want. 

   ENJOY

License:
   Copyright 2015 Luis Miguel Mejía Suárez "BalmungSan" (https://github.com/BalmungSan)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this project except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.


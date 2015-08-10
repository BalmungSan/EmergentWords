'   Copyright 2015 EAFIT (http://www.eafit.edu.co/Paginas/index.aspx)
'
'   Licensed under the Apache License, Version 2.0 (the "License");
'   you may not use this file except in compliance with the License.
'   You may obtain a copy of the License at
'
'       http://www.apache.org/licenses/LICENSE-2.0
'
'   Unless required by applicable law or agreed to in writing, software
'   distributed under the License is distributed on an "AS IS" BASIS,
'   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
'   See the License for the specific language governing permissions and
'   limitations under the License.

'Project: EmergentWords; Java application to analyse texts
'Version: 1.1.5-(09_08_2015)-GA
'Author and Researcher: Luis Miguel Mejía Suárez "BalmungSan" (https://github.com/BalmungSan)
'Main Researcher: Juan Carlos Montalvo Rodrigez (http://scienti1.colciencias.gov.co:8081/cvlac/visualizador/generarCurriculoCv.do?cod_rh=0001021150)

' Visual Basic Script to install wordsfinder in Windows platforms 

'Launch the script as admin
If Not WScript.Arguments.Named.Exists("elevate") Then
  CreateObject("Shell.Application").ShellExecute WScript.FullName _
    , WScript.ScriptFullName & " /elevate", "", "runas", 1
  WScript.Quit
End If

'Variables of the program ----------------------------------------------------------------------------------------------------------------------------------------------------------------------
'Shell Object
Set objShell = Wscript.CreateObject("Wscript.Shell")

'Determine the registry path for MySQL
Dim WshProcEnv
Dim regpath
Dim process_architecture

Set WshProcEnv = objShell.Environment("Process")

process_architecture = WshProcEnv("PROCESSOR_ARCHITECTURE") 

If process_architecture = "x86" Then    
    system_architecture = WshProcEnv("PROCESSOR_ARCHITEW6432")
	
    If system_architecture = "" Then    
        'system_architecture = "x86"
		regpath = "SOFTWARE\"
	Else 
		'system_architecture = "64"
		regpath = "SOFTWARE\Wow6432Node\"
    End if    
Else    
	'system_architecture = "64"
	regpath = "SOFTWARE\Wow6432Node\"
End If


const HKEY_LOCAL_MACHINE = &H80000002
strComputer = "."
Set objReg=GetObject("winmgmts:{impersonationLevel=impersonate}!\\"&_ 
    strComputer & "\root\default:StdRegProv")
strKeyPath = regpath & "MYSQL AB"
objReg.EnumKey HKEY_LOCAL_MACHINE, strKeyPath, arrSubKeys

'sql variables
SQLPath = Chr(34) & objShell.RegRead("HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\MYSQL AB\" & arrSubKeys(0) & "\Location") & "bin\mysql.exe" & Chr(34)
SQLServer = InputBox("Please input the IP address of your MySQL server" & vbNewLine & "Note: If the server runs in the machine use 'localhost'", "MySQL Server Address", "localhost")
SQLUser = InputBox("Please input the user of the MySQL server to login" & vbNewLine & "Note: The most common user is 'root'", "MySQL User", "root")
SQLPassword = InputBox("Please input the password for the user " + SQLUser, "MySQL Password")

'Current directory path and DataBaseManager.java file path
ActualPath = Left(WScript.ScriptFullName,InStrRev(WScript.ScriptFullName,"\"))
FilePath = ActualPath & "trunk\src\main\java\co\edu\eafit\emergentwords\DataBase\DataBaseManager.java"
ActualPath = Chr(34) & ActualPath & Chr(34)
'-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

'Edit the DataBaseManager.java file ------------------------------------------------------------------------------------------------------------------------------------------------------------
Const ForReading = 1
Const ForWriting = 2

Set objFSO = CreateObject("Scripting.FileSystemObject")
Set objFile = objFSO.OpenTextFile(FilePath, ForReading)

strText = objFile.ReadAll
objFile.Close

strText = Replace(strText, "SQLSERVER", SQLServer)
strText = Replace(strText, "SQLUSER", SQLUSER)
strText = Replace(strText, "SQLPASSWORD", SQLPassword)	

Set objFile = objFSO.OpenTextFile(FilePath, ForWriting)
objFile.WriteLine strText
objFile.Close
'-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

'Commands to execute in the cmd as admin -------------------------------------------------------------------------------------------------------------------------------------------------------
'Move cmd to the current path
Commands = "cmd.exe /C " &  Chr(34) & "cd " & ActualPath & Chr(34)
'Create the wordsfider's database 
Commands = Commands & Chr(34) & "& " & SQLPath & " -h " & SQLServer & " -u " & SQLUser & " -p" & SQLPassword & " -e ""CREATE DATABASE EmergentWords;""" & Chr(34)
'Import the words.sql file to the database
Commands = Commands & Chr(34) & "& " & SQLPath & " -h " & SQLServer & " -u " & SQLUser & " -p" & SQLPassword & " EmergentWords < words.sql" & Chr(34)
'Compile the project
Commands = Commands & Chr(34) & "& mvn -f trunk/pom.xml package" & Chr(34)
'Copy the generated Jar to the current folder
Commands = Commands & Chr(34) & "& copy /v /y ""trunk\target\EmergentWords-1.1.5-(09_08_2015)-GA.jar"" EmergentWords.jar" & Chr(34)
'Clean the mvn generated files
Commands = Commands & Chr(34) & "& mvn -f trunk/pom.xml clean" & Chr(34)
'-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

'Execute the command as admin
objShell.run Commands, 1, True
set objShell = Nothing

'Show a message to the user telling that the installation finished
Wscript.Echo "Installation Finished"

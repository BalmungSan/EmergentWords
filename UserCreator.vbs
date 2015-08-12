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

'Project: EmergentWords; Java application to analyse texts (https://github.com/BalmungSan/EmergentWords/tree/office-version)
'Version: 1.1.6-(11_08_2015)-Amended
'Author and Researcher: Luis Miguel Mejía Suárez "BalmungSan" (https://github.com/BalmungSan)
'Main Researcher: Juan Carlos Montalvo Rodrigez (http://scienti1.colciencias.gov.co:8081/cvlac/visualizador/generarCurriculoCv.do?cod_rh=0001021150)

' Visual Basic Script to install mysql users in windows platforms

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

'MySQl variables
dim MySQLUser
MySQLPath = Chr(34) & objShell.RegRead("HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\MYSQL AB\" & arrSubKeys(0) & "\Location") & "bin\mysql.exe" & Chr(34)
MySQLServer = InputBox("Please input the IP address of your MySQL server" & vbNewLine & "Note: If the server runs in the machine use 'localhost'", "MySQL Server Address", "localhost")
MySQLUser = InputBox("Please input the user of the MySQL server to install EmergentWords database", "MySQL User", "root")
MySQLPassword = InputBox("Please input the password for the user " & MySQLUser, "MySQL Password")

'Current directory path and DataBaseManager.java file path
ActualPath = Chr(34) & Left(WScript.ScriptFullName,InStrRev(WScript.ScriptFullName,"\")) & Chr(34)
'-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

'Commands to execute in the cmd as admin -------------------------------------------------------------------------------------------------------------------------------------------------------
'Move cmd to the current path
Commands = "cmd.exe /C " &  Chr(34) & "cd " & ActualPath & Chr(34)

'Create the new user if it's different from root
If StrComp(MySQLUser, "root") <> 0 Then
	MySQLRootPassword = InputBox("Please input the password for the root user", "MySQL ROOT Password")
	Commands = Commands & Chr(34) & " & " & MySQLPath & " -h " & MySQLServer & " -u  root -p" & MySQLRootPassword & " -e ""CREATE USER " & Chr(39) & MySQLUser & Chr(39) & "@" & Chr(39) & MySQLServer & Chr(39) & "IDENTIFIED BY " & Chr(39) & MySQLPassword & Chr(39) & "; GRANT ALL PRIVILEGES ON *.* TO" & Chr(39) & MySQLUser & Chr(39) & "@" & Chr(39) & MySQLServer & Chr(39) & ";""" & Chr(34)
End If

'Create the EmergentWords's database 
Commands = Commands & Chr(34) & "& " & MySQLPath & " -h " & MySQLServer & " -u " & MySQLUser & " -p" & MySQLPassword & " -e ""CREATE DATABASE EmergentWords" & MySQLUser & ";""" & Chr(34)
'Import the words.sql file to the database
Commands = Commands & Chr(34) & "& " & MySQLPath & " -h " & MySQLServer & " -u " & MySQLUser & " -p" & MySQLPassword & " EmergentWords" & MySQLUser & " < words.sql" & Chr(34)
'-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

'Execute the command as admin
objShell.run Commands, 1, True

'Show a message to the user telling that the installation finished
set objShell = Nothing
Wscript.Echo "Installation Finished"

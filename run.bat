@echo off
set JDK_BIN=C:\Program Files\Microsoft\jdk-17.0.20.101-hotspot\bin
"%JDK_BIN%\java.exe" -cp "out;lib\jfreechart-1.5.3.jar" quetzal.Main

@echo off
set JDK_BIN=C:\Program Files\Microsoft\jdk-17.0.20.101-hotspot\bin
if not exist out mkdir out
"%JDK_BIN%\javac.exe" -encoding UTF-8 -cp "lib\jfreechart-1.5.3.jar" -d out -sourcepath src src\quetzal\Main.java src\quetzal\modelo\*.java src\quetzal\juego\*.java src\quetzal\ui\*.java src\quetzal\reporte\*.java
if errorlevel 1 (
  echo ERROR compilacion
  exit /b 1
)
echo OK compilado en out\

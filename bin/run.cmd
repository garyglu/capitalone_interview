@echo off

setlocal
set BASEDIR=%~dp0
set BRANCH_HOME=%BASEDIR%\..

java -version >nul 2>&1 && ( echo ) || (
echo "Can't  find java, please add java to the environment path
exit /b
)


cd %BRANCH_HOME%

set CLASSPATH=.;lib\*
set MAIN_CLASS=com.glu.capitalone.interview.Main

IF [%1]==[] GOTO :JAVA
IF ("%1"=="test") GOTO :TEST

:TEST
set MAIN_CLASS=org.junit.runner.JUnitCore test.com.glu.capitalone.interview.AppTest

:JAVA
echo java -cp %CLASSPATH% %MAIN_CLASS%

java -cp %CLASSPATH% %MAIN_CLASS%

endlocal
@echo off

setlocal
set BASEDIR=%~dp0
set BRANCH_HOME=%BASEDIR%\..

IF NOT DEFINED JAVA_HOME (
    echo JAVA_HOME is not defined
    exit /b
)

set CLASSPATH=".;..\lib\*"
set MAIN_CLASS="com.glu.capitalone.interview.Main"

java -cp %CLASSPATH% %MAIN_CLASS%

endlocal
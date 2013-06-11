@ECHO OFF

echo BUILDING ENDPOINT CLIENT...
echo.
echo.

set BASEDIR=%~dp0..

%~d0

cd %BASEDIR%

call %BASEDIR%\bin\setenv.bat

cd %BASEDIR%

set _CMD=bin\endpoints.cmd
::set _CMD=%_CMD% gen-api-config
set _CMD=%_CMD% gen-client-lib
set _CMD=%_CMD% --war=./target/quilook
set _CMD=%_CMD% --classpath=./target/quilook/WEB-INF/classes
set _CMD=%_CMD% --output=src/client
set _CMD=%_CMD% --language=ANDROID
set _CMD=%_CMD% it.mate.quilook.server.endpoints.QuEndpoint

@echo on

%_CMD%




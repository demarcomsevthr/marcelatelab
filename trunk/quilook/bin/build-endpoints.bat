@ECHO OFF

echo.
echo BUILDING ENDPOINT CLIENT...
echo.
echo.

set BASEDIR=%~dp0..

%~d0

cd %BASEDIR%

call %BASEDIR%\bin\setenv.bat

cd %BASEDIR%

::del /Q src\main\webapp\WEB-INF\*.api

set _CMD=CALL bin\endpoints.cmd
set _CMD=%_CMD% gen-api-config
set _CMD=%_CMD% --war=./target/quilook
set _CMD=%_CMD% --classpath=./target/quilook/WEB-INF/classes
set _CMD=%_CMD% --output=src/main/webapp/WEB-INF
set _CMD=%_CMD% it.mate.quilook.server.endpoints.QuEndpoint
%_CMD%
echo.
echo.

set _CMD=CALL bin\endpoints.cmd
set _CMD=%_CMD% gen-discovery-doc
set _CMD=%_CMD% --output=src/main/webapp/WEB-INF
set _CMD=%_CMD% --format=rest
set _CMD=%_CMD% src/main/webapp/WEB-INF/qu-v1.api
%_CMD%
echo.
echo.

REM PAUSE
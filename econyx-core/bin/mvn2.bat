@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

SET CURDRIVE=%~d0
SET CURPATH=%~dp0
SET MVNCMD=%M2_HOME%\bin\mvn
SET BASEDIR=%CURPATH%..

@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] BUILDING COMMAND:
@echo [INFO] %MVNCMD% %*
@echo [INFO] ------------------------------------------------------------------------

%MVNCMD% %*

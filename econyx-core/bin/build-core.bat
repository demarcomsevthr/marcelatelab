@ECHO OFF

SET CURPATH=%~dp0
SET BASEDIR=%CURPATH%..

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

set DEP_CLEAN=clean

if "%SKIP_DEPENDENCIES_CLEAN%"=="true" set DEP_CLEAN=

cd %BASEDIR%\..\mwtportlets
call %~dp0\mvn2 %DEP_CLEAN% compile package install

cd %BASEDIR%\..\gwtcommons
call %~dp0\mvn2 %DEP_CLEAN% compile datanucleus:enhance package install

:NO_DEPENDENCIES

cd %BASEDIR%
call %~dp0\mvn2 %*

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause

:NO_PAUSE

:END
cd %CURPATH%
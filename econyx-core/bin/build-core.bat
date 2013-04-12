@ECHO OFF

SET CURPATH=%~dp0
SET BASEDIR=%CURPATH%..

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

SET DEP_CLEAN=clean
SET MVN2CMD=%~dp0\mvn2.bat

if "%SKIP_DEPENDENCIES_CLEAN%"=="true" set DEP_CLEAN=

cd %BASEDIR%\..\mwtportlets
call %MVN2CMD% %DEP_CLEAN% compile package install

cd %BASEDIR%\..\gwtcommons
call %MVN2CMD% %DEP_CLEAN% compile datanucleus:enhance package install

:NO_DEPENDENCIES

cd %BASEDIR%
call %MVN2CMD% %*

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause

:NO_PAUSE

:END
cd %CURPATH%
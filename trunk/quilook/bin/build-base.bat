@ECHO OFF

echo BUILDING APP %APPNAME%...
echo.
echo.

title BUILDING APP %APPNAME% [%*]

%~d0

call %~dp0\setenv.bat

SET BASEAPPDIR=%~dp0\..
SET MVN2CMD=%BASEAPPDIR%\bin\mvn2.bat

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

set DEP_CLEAN=clean

if "%SKIP_DEPENDENCIES_CLEAN%"=="true" set DEP_CLEAN=

set SAVE_SKIP_PAUSE=%SKIP_PAUSE%
set SKIP_PAUSE=true

cd %BASEAPPDIR%\..\gwtcommons
call %MVN2CMD% %DEP_CLEAN% compile package install

cd %BASEAPPDIR%\..\gaecommons
call %MVN2CMD% %DEP_CLEAN% compile datanucleus:enhance package install

cd %BASEAPPDIR%\..\mwtportlets
call %MVN2CMD% %DEP_CLEAN% compile package install

cd %BASEAPPDIR%\..\econyx-core
call %MVN2CMD% %DEP_CLEAN% compile datanucleus:enhance package install

set SKIP_PAUSE=%SAVE_SKIP_PAUSE%

:NO_DEPENDENCIES


@echo on
%~d0

cd %BASEAPPDIR%
::call %MVN2CMD% install

cd %BASEAPPDIR%
call %MVN2CMD% %*

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause

:NO_PAUSE

:END
title Ready
cd %BASEAPPDIR%
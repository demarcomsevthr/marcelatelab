@ECHO OFF

echo BUILDING APP %APPNAME%...
echo.
echo.

title BUILDING APP %APPNAME% [%*]

call %~dp0\setenv.bat

SET BASEAPPDIR=%~dp0\..\..\%APPNAME%

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

set DEP_CLEAN=clean

if "%SKIP_DEPENDENCIES_CLEAN%"=="true" set DEP_CLEAN=

set SAVE_SKIP_PAUSE=%SKIP_PAUSE%
set SKIP_PAUSE=true

cd %BASEAPPDIR%\..\econyx-core
cmd /C bin\build-core.bat %DEP_CLEAN% compile datanucleus:enhance package install

cd %BASEAPPDIR%\..\econyx-core
cmd /C bin\install-ext-lib-core.bat

set SKIP_PAUSE=%SAVE_SKIP_PAUSE%

:NO_DEPENDENCIES

cd %BASEAPPDIR%\..\econyx-base-app
call mvn2 install

cd %BASEAPPDIR%
call mvn2 %*

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause

:NO_PAUSE

:END
title Ready
cd %BASEAPPDIR%
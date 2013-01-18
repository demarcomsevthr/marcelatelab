@ECHO OFF

echo BUILDING APP %APPNAME%...
echo.
echo.

title BUILDING APP %APPNAME% [%*]

call %~dp0\setenv.bat

SET BASEAPPDIR=%~dp0\..

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

cd %BASEAPPDIR%\..\..\gwtcommons
call mvn2 %DEP_CLEAN% compile datanucleus:enhance package install

:NO_DEPENDENCIES

if "%GWT_LOG_LEVEL%"=="" SET GWT_LOG_LEVEL=INFO

cd %BASEAPPDIR%
call %~dp0\mvn2 %* -Dgwt.logLevel=%GWT_LOG_LEVEL%

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause

:NO_PAUSE

:END
title Ready
cd %BASEAPPDIR%
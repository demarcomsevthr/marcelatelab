@ECHO OFF

echo BUILDING APP %APPNAME%...
echo.
echo.

title BUILDING APP %APPNAME% [%*]

%~d0

call %~dp0\setenv.bat

SET BASEAPPDIR=%~dp0\..

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

cd %BASEAPPDIR%\..\..\gwtcommons
call mvn2 %DEP_CLEAN% compile package install

:NO_DEPENDENCIES

if "%GWT_LOG_LEVEL%"=="" SET GWT_LOG_LEVEL=INFO

if "%SKIP_CLEAN_ASSETS%"=="true" GOTO NO_CLEAN_ASSETS

cd %BASEAPPDIR%\..\assets
rmdir /S /Q www
mkdir www

:NO_CLEAN_ASSETS

if "%SKIP_CLEAN_TARGET%"=="true" GOTO NO_CLEAN_TARGET

cd %BASEAPPDIR%\..
rmdir /S /Q target
mkdir target

:NO_CLEAN_TARGET

cd %BASEAPPDIR%
call %~dp0\mvn2 %* -Dgwt.logLevel=%GWT_LOG_LEVEL%

:COPY_DEPLOY

if "%DEPLOY_TARGET%"=="" GOTO NO_COPY_DEPLOY
set DEPLOY_SOURCE="%BASEAPPDIR%\..\assets\www"
del /Q /S "%DEPLOY_TARGET%\*" >NUL
rmdir /Q /S "%DEPLOY_TARGET%\main"
echo COPYING DEPLOY TO %DEPLOY_TARGET%
xcopy /E /Y %DEPLOY_SOURCE%\* "%DEPLOY_TARGET%\"
del /Q /S "%DEPLOY_TARGET%\cordova-*.js" >NUL

:NO_COPY_DEPLOY

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause

:NO_PAUSE

:END
title Ready
cd %BASEAPPDIR%
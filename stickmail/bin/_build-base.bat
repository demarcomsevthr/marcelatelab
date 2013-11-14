@ECHO OFF

echo BUILDING APP %APPNAME% %MODULE%
echo.
echo.

title BUILDING APP %APPNAME% %MODULE% [%*]

if "%MODULE%"=="" GOTO NO_MODULE_SET

call %~dp0\_setenv.bat

SET BASEAPPDIR=%~dp0..
SET MVN2CMD=%BASEAPPDIR%\bin\_mvn2.bat

if "%SKIP_DEPENDENCIES%"=="true" GOTO NO_DEPENDENCIES

set DEP_CLEAN=clean

if "%SKIP_DEPENDENCIES_CLEAN%"=="true" set DEP_CLEAN=

cd %BASEAPPDIR%\..\gwtcommons
call %MVN2CMD% %DEP_CLEAN% install

cd %BASEAPPDIR%\..\phgcommons
call %MVN2CMD% %DEP_CLEAN% install

cd %BASEAPPDIR%\adapter
call %MVN2CMD% %DEP_CLEAN% install

:NO_DEPENDENCIES

cd %BASEAPPDIR%\%MODULE%
call %MVN2CMD% %*

if "%SKIP_PAUSE%"=="true" GOTO NO_PAUSE
pause
GOTO END

:NO_MODULE_SET
echo NO MODULE SET
GOTO END

:NO_PAUSE
:END
title Ready
cd %BASEAPPDIR%

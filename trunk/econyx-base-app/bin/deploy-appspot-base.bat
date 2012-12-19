@ECHO OFF

set FAST_BUILD=false
::set FAST_BUILD=true

set APPNAME=%1
set MVN_GOALS=compile datanucleus:enhance
set SKIP_DEPENDENCIES=true

if "%FAST_BUILD%"=="true" GOTO FAST_BUILD
set SKIP_DEPENDENCIES=false
set MVN_GOALS=clean compile datanucleus:enhance gwt:compile

:FAST_BUILD

set MVN_GOALS=%MVN_GOALS% antrun:run
set MVN_GOALS=%MVN_GOALS% war:exploded
call %~dp0\build-base.bat %MVN_GOALS%


echo.
echo.
echo        ------------------------------------------------------------------------
echo         BEGINNING APPENGINE DEPLOY
echo        ------------------------------------------------------------------------
echo.

set SKIP_DEPENDENCIES=true
set MVN_GOALS= 
set MVN_GOALS=%MVN_GOALS% antrun:run
set MVN_GOALS=%MVN_GOALS% gae:update-indexes
set MVN_GOALS=%MVN_GOALS% gae:update
call %~dp0\build-base.bat %MVN_GOALS% -Dant.execution.task=after-build -Dgae.home=%GAE_HOME%

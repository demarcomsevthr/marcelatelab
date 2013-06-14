@echo off

set APPNAME=quilook

call %~dp0\setenv.bat

set MVN_GOALS=clean compile datanucleus:enhance gwt:compile antrun:run war:exploded
call %~dp0\build-base.bat %MVN_GOALS%
call %~dp0\build-endpoints.bat

echo.
echo.
echo        ------------------------------------------------------------------------
echo         BEGINNING APPENGINE DEPLOY
echo        ------------------------------------------------------------------------
echo.
echo.

set SKIP_DEPENDENCIES=true
set MVN_GOALS= 
set MVN_GOALS=%MVN_GOALS% antrun:run
set MVN_GOALS=%MVN_GOALS% gae:update-indexes
set MVN_GOALS=%MVN_GOALS% gae:update
call %~dp0\build-base.bat %MVN_GOALS% -Dant.execution.task=after-build -Dgae.home=%GAE_HOME%



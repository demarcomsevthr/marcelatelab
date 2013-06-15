@ECHO OFF

set APPNAME=%1

set SKIP_DEPENDENCIES=true

set MVN_GOALS=
set MVN_GOALS=%MVN_GOALS% clean
set MVN_GOALS=%MVN_GOALS% compile
set MVN_GOALS=%MVN_GOALS% datanucleus:enhance
::set MVN_GOALS=%MVN_GOALS% gwt:compile
set MVN_GOALS=%MVN_GOALS% antrun:run
set MVN_GOALS=%MVN_GOALS% war:exploded

call %~dp0\build-base.bat %MVN_GOALS%


@ECHO OFF

set APPNAME=%1

set SKIP_DEPENDENCIES=false
set SKIP_PAUSE=true
:: datanucleus:enhance gwt:compile
call %~dp0\build-base.bat compile antrun:run war:exploded

exit

set SKIP_DEPENDENCIES=true
set SKIP_PAUSE=false
call %~dp0\build-base.bat antrun:run -Dant.execution.task=after-build


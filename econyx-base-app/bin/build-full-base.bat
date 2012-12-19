@ECHO OFF

set APPNAME=%1

set SKIP_PAUSE=true
call %~dp0\build-base.bat clean compile datanucleus:enhance gwt:compile antrun:run war:exploded

set SKIP_DEPENDENCIES=true
set SKIP_PAUSE=false
call %~dp0\build-base.bat antrun:run -Dant.execution.task=after-build


@ECHO OFF

set APPNAME=%1

set SKIP_DEPENDENCIES=false
::set SKIP_PAUSE=true
:: datanucleus:enhance gwt:compile
call %~dp0\build-base.bat compile gwt:compile antrun:run war:exploded


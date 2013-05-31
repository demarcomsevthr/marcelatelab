@echo off

set APPNAME=quilook

set SKIP_DEPENDENCIES_CLEAN=true
call %~dp0\build-base.bat compile datanucleus:enhance gwt:compile antrun:run war:exploded

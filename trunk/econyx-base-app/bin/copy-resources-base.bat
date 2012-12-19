@echo off

set APPNAME=%1

set SKIP_DEPENDENCIES=true
set SKIP_PAUSE=true
call %~dp0\build-base.bat resources:resources war:exploded 


@ECHO OFF

set APPNAME=abaco-gwt
call %~dp0\build-base.bat compile gwt:compile war:exploded assembly:single

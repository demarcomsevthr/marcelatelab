@ECHO OFF

set APPNAME=gpg-irc-demo-gwt
call %~dp0\build-base.bat compile gwt:compile war:exploded assembly:single

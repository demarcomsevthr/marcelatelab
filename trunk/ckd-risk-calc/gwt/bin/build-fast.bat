@ECHO OFF
set APPNAME=ckd-risk-calc-gwt
call %~dp0\build-base.bat compile gwt:compile war:exploded assembly:single

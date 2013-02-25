@ECHO OFF
set APPNAME=ckd-risk-calc-gwt
set SKIP_DEPENDENCIES=true
set SKIP_PAUSE=true
call %~dp0\build-base.bat war:exploded

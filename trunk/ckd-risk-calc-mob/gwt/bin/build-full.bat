@ECHO OFF
set APPNAME=ckd-risk-calc-gwt
set GWT_LOG_LEVEL=TRACE
call %~dp0\build-base.bat clean compile gwt:compile war:exploded assembly:single

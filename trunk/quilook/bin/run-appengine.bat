@echo off

set APPNAME=quilook

set SKIP_DEPENDENCIES_CLEAN=true
set SKIP_PAUSE=true
call %~dp0\build-base.bat compile datanucleus:enhance antrun:run war:exploded

set JDWP_OPTS=-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n

@echo on
%JAVA_HOME%\bin\java -cp "%GAE_HOME%\lib\appengine-tools-api.jar" ^
    com.google.appengine.tools.KickStart ^
       --jvm_flag=-Xdebug ^
       --jvm_flag=%JDWP_OPTS% ^
       com.google.appengine.tools.development.DevAppServerMain ^
       %~dp0\..\..\%APPNAME%\target\%APPNAME%
       
@echo.
@echo.
@echo.
::pause


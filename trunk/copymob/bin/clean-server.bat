@ECHO OFF
set APPNAME=copymob

set MODULE=server
set SKIP_PAUSE=false
set SKIP_GWT_DEPENDENCIES=true
set USE_GAE_DEPENDENCIES=true
call %~dp0\_setenv2.bat

set goals=
set goals=%goals% clean
set goals=%goals% compile

call %~dp0\_build-base.bat %goals%


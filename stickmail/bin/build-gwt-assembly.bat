@ECHO OFF
set APPNAME=stickmail
set MODULE=gwt
::set SKIP_PAUSE=true
set SKIP_DEPENDENCIES=true
set goals=
set goals=%goals% clean assembly:assembly
call %~dp0\_build-base.bat -e %goals%

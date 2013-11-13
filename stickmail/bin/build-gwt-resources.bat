@ECHO OFF
set APPNAME=stickmail
set MODULE=gwt
set SKIP_PAUSE=false
set SKIP_DEPENDENCIES_CLEAN=true
set goals=
::set goals=%goals% -e
set goals=%goals% war:exploded
call %~dp0\_build-base.bat %goals%

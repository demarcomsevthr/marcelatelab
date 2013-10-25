@ECHO OFF
set APPNAME=gendtest-gwt
set SKIP_PAUSE=false
set goals=
set goals=%goals% clean
set goals=%goals% compile
set goals=%goals% gwt:compile
set goals=%goals% war:exploded
call %~dp0\_build-base.bat -e %goals%
@ECHO OFF
set APPNAME=protons

set MODULE=gwt
set SKIP_PAUSE=false
set SKIP_DEPENDENCIES=true
set goals=
::set goals=%goals% -e
set goals=%goals% war:exploded gwt:run-codeserver

call %~dp0\_build-base.bat %goals%

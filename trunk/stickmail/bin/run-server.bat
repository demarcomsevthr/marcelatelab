@ECHO OFF
set APPNAME=stickmail

set MODULE=gwt
set SKIP_PAUSE=true
set SKIP_DEPENDENCIES_CLEAN=true
set goals=
set goals=%goals% war:exploded assembly:assembly
call %~dp0\_build-base.bat %goals%

set MODULE=server
set SKIP_PAUSE=false
set SKIP_DEPENDENCIES=true
call %~dp0\_setenv2.bat

set goals=
set goals=%goals% clean
set goals=%goals% compile
set goals=%goals% appengine:endpoints_get_discovery_doc
set goals=%goals% war:exploded
set goals=%goals% appengine:devserver

call %~dp0\_build-base.bat %goals%


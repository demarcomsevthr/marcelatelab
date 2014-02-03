@ECHO OFF
set APPNAME=stickmail
set MODULE=server
set SKIP_PAUSE=false
set SKIP_GWT_DEPENDENCIES=true
call %~dp0\_setenv2.bat

set goals=
set goals=%goals% clean
set goals=%goals% compile
set goals=%goals% appengine:endpoints_get_discovery_doc
set goals=%goals% war:exploded
set goals=%goals% appengine:devserver

call %~dp0\_build-base.bat %goals%


@ECHO OFF
set APPNAME=gae-endp-test
set SKIP_PAUSE=false
set goals=clean compile
set goals=%goals% appengine:endpoints_get_discovery_doc
::set goals=%goals% appengine:endpoints_get_client_lib
set goals=%goals% war:exploded
call %~dp0\_build-base.bat %goals%
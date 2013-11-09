@ECHO OFF
set APPNAME=gae-endp-test
set SKIP_PAUSE=false
set goals=compile
set goals=%goals% appengine:endpoints_get_discovery_doc
set goals=%goals% war:exploded
set goals=%goals% appengine:devserver

call %~dp0\_build-base.bat %goals%


@ECHO OFF
set APPNAME=stickmail
set MODULE=server
set SKIP_PAUSE=false
SET M2_HOME=D:\OPT\apache-maven-3.1.1
set goals=
set goals=%goals% clean
set goals=%goals% compile
set goals=%goals% appengine:endpoints_get_discovery_doc
set goals=%goals% war:exploded
set goals=%goals% appengine:update
call %~dp0\_build-base.bat %goals%


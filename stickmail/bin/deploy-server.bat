@ECHO OFF
set APPNAME=stickmail

::GOTO NO_GTW_RPC_UPDATES

::SERVE PER COPIARE I POLICY FILE (.gwt.rpc) NEI PATH GIUSTI
set MODULE=gwt
set SKIP_PAUSE=false
set SKIP_DEPENDENCIES_CLEAN=true
set SKIP_GWT_DEPENDENCIES=true
set goals=
set goals=%goals% war:exploded
set goals=%goals% assembly:assembly
call %~dp0\_build-base.bat %goals%
echo.
echo.
echo.

:NO_GTW_RPC_UPDATES

set MODULE=server
set SKIP_PAUSE=false
set SKIP_GWT_DEPENDENCIES=true
set USE_GAE_DEPENDENCIES=true
call %~dp0\_setenv2.bat

set goals=
set goals=%goals% clean
set goals=%goals% compile
set goals=%goals% datanucleus:enhance
set goals=%goals% appengine:endpoints_get_discovery_doc
set goals=%goals% war:exploded
set goals=%goals% appengine:update

call %~dp0\_build-base.bat %goals%


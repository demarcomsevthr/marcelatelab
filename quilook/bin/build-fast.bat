@echo off

set APPNAME=quilook

set JAVA_HOME=D:\OPT\jdk1.7.0_21
set M2_HOME=D:\OPT\apache-maven-2.2.1

SET CURDRIVE=%~d0
SET CURPATH=%~dp0
SET MVNCMD=%M2_HOME%\bin\mvn.bat
SET BASEDIR=%CURPATH%..

CD %BASEDIR%

%CURPATH%\mvn2.bat clean package

GOTO EOF

set SKIP_DEPENDENCIES=true

set SKIP_DEPENDENCIES_CLEAN=true

set MVN_GOALS=
set MVN_GOALS=%MVN_GOALS% clean
set MVN_GOALS=%MVN_GOALS% compile
::set MVN_GOALS=%MVN_GOALS% datanucleus:enhance
::set MVN_GOALS=%MVN_GOALS% gwt:compile
::set MVN_GOALS=%MVN_GOALS% antrun:run
set MVN_GOALS=%MVN_GOALS% war:exploded

::set MVN_OPTS=-e -X
set MVN_OPTS=

call %~dp0\build-base.bat %MVN_GOALS% %MVN_OPTS%


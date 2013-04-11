@echo off

cd %~dp0\..

SET MVN2CMD=%~dp0\mvn2.bat

::SET gid=org.lobobrowser
::SET ver=0.98.4
::SET aid=cobra
::SET file=ext-lib/cobra/cobra-0.98.4.jar
::cmd /c %MVN2CMD% install:install-file -Dfile=%file% -DgroupId=%gid% -DartifactId=%aid% -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true
  
if "%SKIP_PAUSE%" == "true" goto END

echo.
echo.
pause

:END
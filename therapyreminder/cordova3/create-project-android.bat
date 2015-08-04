@ECHO OFF

echo.
echo CREATING PHONEGAP 3 PROJECT
echo.
echo ATTENZIONE!!!
echo.
echo OCCORRE INSERIRE NEL PATH DI SISTEMA LE SEGUENTI VARIABILI:
echo     ANT_HOME\bin
echo     JAVA_HOME\bin
echo     AND_SDK\platform-tools
echo     AND_SDK\tools
echo.
echo.

SET JAVA_HOME=P:\OPT\java\jdk1.7.0_67

echo PATH VARIABLE
echo %path%

SET BASEDIR=%~dp0

SET PROJNAME=template-project

SET PACKAGE=it.mate.therapyreminder

SET APPNAME=TherapyReminder

cd %BASEDIR%

echo.
echo.
echo removing project dir %projname%
RMDIR /S /Q %projname%

echo.
echo.
echo creating project %projname% %package% %appname%
pause
call cordova create %projname% %package% %appname%

cd %projname%

echo.
echo.
echo adding android platform
pause
call cordova platform add android

echo.
echo.
echo adding plugins
pause
call cordova plugin add cordova-plugin-device
call cordova plugin add cordova-plugin-inappbrowser
call cordova plugin add cordova-plugin-console
call cordova plugin add cordova-plugin-globalization

call cordova plugin add https://github.com/katzer/cordova-plugin-local-notifications.git

echo.
echo.
echo building android
pause
call cordova build android

echo.
echo.
echo FINISH
pause
cd %BASEDIR%

echo.
echo.
echo >>>>>>>>>>>>>  ATTENZIONE  <<<<<<<<<<<<<<
echo RICORDARSI DI RIPULIRE IL PATH DI SISTEMA AL TERMINE DELLA BUILD 
echo.
echo.
echo.

::EXIT

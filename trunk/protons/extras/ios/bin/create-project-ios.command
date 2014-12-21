
## NOTA BENE
##
##  VMX SETTINGS
##
##  RAM = 1,5 GB OR MORE
##

cd ~/Documents/phonegap-3

cordova create protons it.mate.protons Protons

cd protons

cordova platform add ios

cordova plugin add org.apache.cordova.device

cordova plugin add org.apache.cordova.console

cordova plugin add org.apache.cordova.inappbrowser

cordova plugin add org.apache.cordova.globalization

cordova plugin add org.apache.cordova.file

cordova plugin add org.apache.cordova.file-transfer

cordova build ios


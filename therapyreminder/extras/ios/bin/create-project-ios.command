
## NOTA BENE
##
##  VMX SETTINGS
##
##  RAM = 1,5 GB OR MORE
##

cd ~/Documents/phonegap-3

cordova create therapyreminder it.mate.therapyreminder TherapyReminder

cd therapyreminder

cordova platform add ios

cordova plugin add org.apache.cordova.device

cordova plugin add org.apache.cordova.console

cordova plugin add org.apache.cordova.inappbrowser

// NB: calendar da patchare (vedi phgcommons/extras/plugins):
cordova plugin add https://github.com/EddyVerbruggen/Calendar-PhoneGap-Plugin.git

cordova plugin add https://github.com/jcjee/email-composer.git

cordova plugin add org.apache.cordova.globalization

cordova plugin add https://github.com/katzer/cordova-plugin-local-notifications.git

cordova plugin add https://github.com/martinmose/cordova-keyboard.git

cordova build ios


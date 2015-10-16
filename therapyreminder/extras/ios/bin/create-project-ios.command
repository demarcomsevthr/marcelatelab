
## NOTA BENE
##
##  VMX SETTINGS
##
##  RAM = 1,5 GB OR MORE
##

cd ~/Documents/cordova5

cordova create therapyreminder it.mate.therapyreminder "Therapy Reminder"

cd therapyreminder

cordova platform add ios

cordova plugin add cordova-plugin-device

cordova plugin add cordova-plugin-inappbrowser

cordova plugin add cordova-plugin-console

cordova plugin add cordova-plugin-splashscreen

// NB: calendar da patchare (vedi phgcommons/extras/plugins):
cordova plugin add cordova-plugin-calendar

cordova plugin add https://github.com/jcjee/email-composer.git

cordova plugin add cordova-plugin-globalization

// [13/10/2015] Rimosso perche' va in errore nella build
// cordova plugin add https://github.com/katzer/cordova-plugin-local-notifications.git

cordova plugin add https://github.com/martinmose/cordova-keyboard.git

cordova build ios


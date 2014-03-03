
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

cordova build ios


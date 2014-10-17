
## NOTA BENE
##
##  VMX SETTINGS
##
##  RAM = 1,5 GB OR MORE
##

cd ~/Documents/phonegap-3

cordova create postscriptum it.mate.postscriptum PostScriptum

cd postscriptum

cordova platform add ios

cordova plugin add org.apache.cordova.device

cordova plugin add org.apache.cordova.console

cordova plugin add org.apache.cordova.inappbrowser

cordova plugin add org.apache.cordova.globalization

cordova plugin add org.apache.cordova.contacts

cordova build ios



######################################
#
# RICORDARSI DI AGGIORNARE:

>> Staging/config.xml:

      <preference name="BackupWebStorage" value="locale" />
      
      
      




	VMX SETTINGS
	
	RAM = 1,5 GB
	
  ________________________________________________________
  >
  >   UPGRADE CORDOVA 3.6.3 (a seguito di android security issue)
  >
  
  >> 04/08/2014 >> ANDROID SECURITY ISSUE ON CORDOVA LESS THAN 3.5.0 !!!
  >> see http://cordova.apache.org/announcements/2014/08/04/android-351.html
  
  >> OCCORRE AGGIORNARE CORDOVA:
  
  call npm update -g cordova
  
  >> POI RICREARE IL TEMPLATE PROJECT E FARE UPDATE/MERGE DEL PROJECT EFFETTIVO COME DI CONSUETO:
  
    > platforms/android/assets/www/plugins 																					(MERGE)
    > platforms/android/assets/www/cordova.js																				(UPDATE)
    > platforms/android/assets/www/cordova_plugins.js																(MERGE)
    > platforms/android/CordovaLib/ant-build/classes.jar --> libs/CordovaLib.jar		(UPDATE)
    > platforms/android/res/xml/config.xml 																					(MERGE)
    > platforms/android/res/values/strings.xml																			(MERGE)
    > platforms/android/src/it/... <discendente di CordovaActivity>									(MERGE)
    > platforms/android/src/org/... 																								(UPDATE)
    > platforms/android/AndroidManifest.xml 																				(MERGE)
    
    > platforms/android/res/... <ICONE MANCANTI>																		(ADD)
    
  >> IMPORTANTE: assets/www/plugins/org.apache.cordova.inappbrowser/www/inappbrowser.js		>>  CAMBIATO NOME (da maiuscolo a minuscolo)
  
  >> IMPORTANTE: UPGRADE A gwtphonegap-3.5.0.0								>> gwt/pom.xml
  
	
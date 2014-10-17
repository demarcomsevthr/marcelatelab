
	VMX SETTINGS
	
	RAM = 1,5 GB
	
  ______________________________________________________________________________________
  >
  >   UPGRADE CORDOVA 3.6.3 (ANDROID SECURITY ISSUE)
  >
  
  >> DAL 04/08/2014 SI RENDE NECESSARIO PASSARE A CDV 3.5.1+
                   (see http://cordova.apache.org/announcements/2014/08/04/android-351.html)
  
  >> BACKUP VMX [P:\VIRTUAL_MACHINES\OSX\Mac_OS_X_10.9 - CDV 3.3.0]
  
  >> AGGIORNAMENTO CORDOVA
  
     sudo npm update -g cordova
  
  >> UPDATE/MERGE TRA PROJECT RICREATO E BACKUP CDV 3.3.0
  
    > platforms/ios/www/plugins                                 (CHECK > NO UPDATES NEED)
    > platforms/ios/www/*.js                                    (COPY)
    > platforms/ios/www/*.html                                  (COPY)
    > platforms/ios/www/main                                    (COPY)
    > platforms/ios/PostScriptum/Resources/icons                (SUBSTITUTE)
    > platforms/ios/PostScriptum/Resources/splash               (SUBSTITUTE)

  ------------------------------------------------    

  >> IMPORTANTE: UPGRADE A gwtphonegap-3.5.0.0								>> gwt/pom.xml
  
  ------------------------------------------------    

  >> platforms/ios/PostScriptum/config.xml:
        <!-- modificato value da cloud a local 
             serve per evitare il warning 
             "Started backup to iCloud! Please be careful. 
             Your application might be rejected by Apple if you store too much data." 
             -->
        <preference name="BackupWebStorage" value="local" />

	
  ______________________________________________________________________________________

	MISSING TEST FLIGHT BETA ENTITLEMENT
	
	[17/10/2014]
	
	Ho dovuto ricreare il provisioning profile per App Store perchè su itunes connect dava il warning:
	
	"To use TestFlight Beta Testing, this build must contain the correct beta entitlement."
	
	see also http://stackoverflow.com/questions/25756669/app-does-not-contain-the-correct-beta-entitlement	
	
	
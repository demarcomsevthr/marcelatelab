
  ______________________________________________________________________________________

	VIRTUAL MACHINE SETTINGS
	
	RAM = 1,5 GB (almeno)
	
	darwin.iso --> contiene VMware Tools setup (attenzione utilizzare versione con dim. 9662kb)
	
  ______________________________________________________________________________________
  >
  >   [01/10/2014]
  >   UPGRADE CORDOVA 3.6.3 (A SEGUITO ANDROID SECURITY ISSUE)
  >
  
  >> AGGIORNAMENTO CORDOVA
  
     sudo npm update -g cordova
  
  >> UPDATE/MERGE TRA PROJECT RICREATO E BACKUP CDV 3.3.0
  
    > platforms/ios/www/plugins                                 (CHECK > NO UPDATES NEED)
    > platforms/ios/www/*.js                                    (COPY)
    > platforms/ios/www/*.html                                  (COPY)
    > platforms/ios/www/main                                    (COPY)
    > platforms/ios/<project>/Resources/icons                   (SUBSTITUTE)
    > platforms/ios/<project>/Resources/splash                  (SUBSTITUTE)

  
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
	
  ______________________________________________________________________________________

    XCODE 6 EXPORT

    [30/10/2014]
    
    XCODE 6 > SEMBRA CHE NON FUNZIONI BENE L'EXPORT DEL FILE IPA E LA SCELTA DEL PROVISIONING PROFILE
    (vedi http://stackoverflow.com/questions/25056144/xcode-6-how-to-pick-signing-certificate-provisioning-profile-for-ad-hoc-distri)
    
    WORKAROUND: utilizzare il comando xcodebuild
    
    (Es: /protoph/extras/ios/bin/protoph-xcodebuild.command)
    
    
	

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
	
	Ho dovuto ricreare il provisioning profile per App Store perch� su itunes connect dava il warning:
	
	"To use TestFlight Beta Testing, this build must contain the correct beta entitlement."
	
	see also http://stackoverflow.com/questions/25756669/app-does-not-contain-the-correct-beta-entitlement	
	
  ______________________________________________________________________________________

    XCODE 6 EXPORT

    [30/10/2014]
    
    XCODE 6 > SEMBRA CHE NON FUNZIONI BENE L'EXPORT DEL FILE IPA E LA SCELTA DEL PROVISIONING PROFILE
    (vedi http://stackoverflow.com/questions/25056144/xcode-6-how-to-pick-signing-certificate-provisioning-profile-for-ad-hoc-distri)
    
    WORKAROUND: utilizzare il comando xcodebuild
    
    (Es: /protoph/extras/ios/bin/protoph-xcodebuild.command)
    
	
  ______________________________________________________________________________________

    INSTALLAZIONE MULTIPLA XCODE 6 E XCODE 5
    
	> APPLE DOWNLOADS >> https://developer.apple.com/downloads/index.action
	
	> cercare xcode >> Xcode 5.1.1 (10/04/2014)

    >> http://iosdevelopertips.com/xcode/install-multiple-versions-of-xcode.html

	  1 - mkdir /Applications/Xcode5.1.1
	  
	  2 - OPEN DMG FILE
	
	  3 - USING FINDER DRAG/DROP THE Xcode.app ICON IN THE CREATED FOLDER
	      (Copying Xcode to Xcode5.1.1...)
	  
	  4 - LAUNCH Xcode.app

	>> SELEZIONARE LA VERSIONE DI XCODE DA UTILIZZARE
	
	  $sudo xcode-select --switch /Applications/Xcode5.1.1/Xcode.app
	  
	  $sudo xcode-select --switch /Applications/Xcode.app
	  
	
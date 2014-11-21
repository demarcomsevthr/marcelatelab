
  =================================================================
  >>                INSTALLAZIONE DI PHONEGAP 3                  <<
  =================================================================

  >> AGG. 24/01/2014

  ________________________________________________________
  > INSTALLAZIONE DI NODEJS SU WIN
  
    >> SEE http://docs.phonegap.com/en/3.3.0/guide_cli_index.md.html#The%20Command-Line%20Interface         

    -- download nodejs (es: P:\OPT\nodejs\node-v0.10.24-x64.msi)
    
    -- setup msi (va fatto per forza! aggiorna il registry e modifica il path di sistema!)
                 (es: P:\OPT\nodejs\node-v0.10.24-x64)
             
             
  ________________________________________________________
  > INSTALLAZIONE PHONEGAP / CORDOVA
  
    >> SEE http://docs.phonegap.com/en/3.3.0/guide_cli_index.md.html#The%20Command-Line%20Interface         

    > vedi install-phonegap-cordova.bat (lanciare da prompt)
    
    > CD %NODEJS_HOME%

    > npm install -g phonegap
     
    > npm install -g cordova
    



  ________________________________________________________
  > CREAZIONE ANDROID PHONEGAP PROJECT
  
    >> SEE http://docs.phonegap.com/en/3.3.0/guide_platforms_android_index.md.html#Android%20Platform%20Guide                                             
    
    > vedi create-project-android.bat (creazione project di riferimento, adding android platform, adding plugins, android build)
                                      (lanciare da prompt)
    
    >> IMPORTANTE: OCCORRE AGGIUNGERE NEL SYSTEM PATH (Sistema/Properties/Variabili di Ambiente) I SEGUENTI PATH FISSI:
    
      > ANT_HOME\bin
      > JAVA_HOME\bin
      > ANDROID_SDK\platform-tools
      > ANDROID_SDK\tools
      
      (vedi extra-system-path.txt)
      
      >> RICORDARSI DI TOGLIERLI DOPO AVER FATTO LA BUILD
      
    > DAL PROJECT CREATO COPIARE NEL PROJECT ANDROID ECLIPSE:
    
    > platforms/android/assets/www/plugins
    > platforms/android/assets/www/config.xml
    > platforms/android/assets/www/cordova_plugins.js
    > platforms/android/assets/www/cordova.js
    > platforms/android/CordovaLib/bin/CordovaLib.jar --> libs
    > platforms/android/res/xml/config.xml (copiare e controllare le cose che serve aggiungere, es:
                                            exit-on-suspend
                                            propri plugins, ...)
    > platforms/android/src/ ... la derivata di CordovaActivity.java (nel package specifico)
    > platforms/android/src/org/... (plugins cordova installati)
    > platforms/android/AndroidManifest.xml (copiare e controllare le cose che servono:
                                             minSdkVersion
                                             hardwareAccelerated = false 
                                             ...)
                                             
    
  ________________________________________________________
  > AGGIUNTA DI NUOVI CORDOVA PLUGINS
  
    >> SEE http://docs.phonegap.com/en/3.3.0/guide_cli_index.md.html#The%20Command-Line%20Interface (Add Plugin Features)

    > OCCORRE RIFARE LA BUILD DEL PROJECT (O RICREARLO EX NOVO)
      E AGGIORNARE LE RISORSE MODIFICATE:
      
    > platforms/android/assets/www/plugins
    > platforms/android/assets/www/config.xml
    > platforms/android/assets/www/cordova_plugins.js
    > platforms/android/assets/www/cordova.js
    > platforms/android/res/xml/config.xml (copiare e controllare le cose che serve aggiungere, es:
                                            exit-on-suspend
                                            propri plugins, ...)
    > platforms/android/src/org/... (plugins cordova installati)
  

    
  ________________________________________________________
  >
  >   UPGRADE CORDOVA 3.6.3 (android security issue)
  >
  
  >> 04/08/2014 >> ANDROID SECURITY ISSUE ON CORDOVA LESS THAN 3.5.0 !!!
  
  >> see http://cordova.apache.org/announcements/2014/08/04/android-351.html
  
  >> OCCORRE AGGIORNARE CORDOVA:
  
  call npm update -g cordova
  
  OPPURE:
  
  call npm list -g cordova
  
  call npm install -g cordova@3.6.3-0.2.13
  
  call npm install -g phonegap@3.6.0-0.21.19
  
  ---------------------------------------- 
  
  >> POI RICREARE IL TEMPLATE PROJECT E FARE UPDATE/MERGE DEL PROJECT EFFETTIVO COME DI CONSUETO:
  
    > platforms/android/assets/www/plugins                                              (MERGE)
    > platforms/android/assets/www/cordova.js                                           (UPDATE)
    > platforms/android/assets/www/cordova_plugins.js                                   (MERGE)
    > platforms/android/CordovaLib/ant-build/classes.jar --> libs/CordovaLib.jar        (UPDATE)
    > platforms/android/res/xml/config.xml                                              (MERGE)
    > platforms/android/res/values/strings.xml                                          (MERGE)
    > platforms/android/src/it/... <discendente di CordovaActivity>                     (MERGE)
    > platforms/android/src/org/...                                                     (UPDATE)
    > platforms/android/AndroidManifest.xml                                             (MERGE)
    
    > platforms/android/res/... <ICONE MANCANTI>                                        (ADD)
    
  >> IMPORTANTE: assets/www/plugins/org.apache.cordova.inappbrowser/www/inappbrowser.js		>>  CAMBIATO NOME (da maiuscolo a minuscolo)
  
  >> IMPORTANTE: UPGRADE A gwtphonegap-3.5.0.0								>> gwt/pom.xml
  

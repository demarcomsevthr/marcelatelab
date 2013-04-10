	
    ______________________________________________________________________________________

		>>> TIPS <<<
    ______________________________________________________________________________________


	UPGRADE VERSIONE APPENGINE
	

		ATTENZIONE: IL PROJECT MAVEN VIENE AGGIORNATO CON UN CERTO RITARDO 
		[net.kindleit.gae-runtime]
			
		1) Download appengine java sdk
			appengine-java-sdk-X.X.X
			download link: https://code.google.com/p/googleappengine/downloads/list?can=1&q=&colspec=Filename+Summary+Uploaded+ReleaseDate+Size+DownloadCount
			[nella pagina di download ufficiale c'è solo l'ultima versione]
			
		2) Oggetti da modificare:
			gwtcommons/pom.xml		>> gae.version e plugin maven-datanucleus-plugin* (controllare versioni dei jar dipendenti, SE CAMBIA)
			econyx-base-app/pom.xml	>> gae.version e plugin maven-datanucleus-plugin* (controllare versioni dei jar dipendenti, SE CAMBIA)
			econyx-base-app/setenv.bat >> set GAE_HOME=D:\OPT\gwt\appengine\appengine-java-sdk-X.X.X
		    * il plugin deve essere duplicato perchè l'enhancement va fatto sia su gwtcommons che su econyx

		3) Prima di provare sul nuovo appengine ricordarsi di fare una build full

		
		-------------------------------------------------
	
		4) [NON SERVE PIU'] Add Eclipse Appengine Sdk
			set as default >> questo aggiorna la library su del progetto econyx, però la inverte rispetto a maven (quindi bisogna ripristinare il corretto ordine delle librerie)
			NOTA BENE
			non dovrebbe più servire rimuovere datanucleus-appengine-x.x.x.jar dal appengine sdk
			NOTA 16/01/2013
			non serve più, perchè non uso più gae dal plugin di eclipse


	_____________________________________________________________________________________


	ORDINE DELLE LIBRARY DEL PROGETTO
	
		L'ordine delle library del progetto deve essere:
			JRE
			GWT
			App Engine SDK [quello di eclipse]
			Maven Dependencies



	_____________________________________________________________________________________

	
	CREZIONE DI UN NUOVO BRANCH
	
		Team > Branch/Tag
			Copy to URL > Select >
			  [selezionare una nuova dir sotto branches, es: https://marcelatetest.googlecode.com/svn/branches/econyx-0.5]
			  
		Team / Switch to another branch
		

	_____________________________________________________________________________________

		
	
	MERGE DI UN BRANCH
	
		- provato su eclipse @ home (versione?) il 19/11/2012
	
		- situazione iniziale: gwtcommons [branches/gwtcommons-beta] allineata al server
	
		> team > switch to another branch		// si sposta sul trunk
				>> selezionare trunk/gwtcommons
				>> OK
				>>> build workspace > IGNORARE GLI ERRORI
			
		> team > merge..										// effettua il merge e carica in wc la versione mergiata
				>> reintegrate a branch
				>> merge from: /branches/gwtcommons-beta
				>> accettare defaults [prompt|prompt|prompt]
				>> finish
			
				>>> merge results // fa vedere il dettaglio del merge
			
				>>> [OPZ] sui conflitti:
					- resolve the conflict by using the incoming version of file
				
				>>> file statistics [...]
			
				>>> OK
			
				>>>> build workspaces >>> spariscono gli errori !
			
		> team > COMMIT											// fa la commit della wc sul trunk
	
				>> committa la wc sul trunk
			
			
	
	


	_____________________________________________________________________________________



	CREZIONE DI UNA NUOVA VERSIONE GWTCOMMONS
	
	  gwtcommons/pom.xml	>> gae.version e plugin maven-gae-plugin e maven-datanucleus-plugin* (controllare versioni dei jar dipendenti)
	  econyx/pom.xml		>> gae.version e plugin maven-datanucleus-plugin* (controllare versioni dei jar dipendenti)
	

	_____________________________________________________________________________________

	
	CREZIONE DI UNA NUOVA VERSIONE ECONYX
	
		Oggetti da modificare:
		  econyx/pom.xml	>> version
		  bin/setenv.bat	>> PRJ_VERSION
		  output folder		>> es: econyx/target/econyx-0.4/WEB-INF/classes
	  


	_____________________________________________________________________________________


	CREZIONE DI UNA NUOVA VERSIONE DEPLOY
	
		Oggetti da modificare:
		  src/main/webapp/WEB-INF/appengine-web.xml
		
	


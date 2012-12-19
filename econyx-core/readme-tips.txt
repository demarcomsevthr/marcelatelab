	
    ______________________________________________________________________________________

		>>> TIPS <<<
    ______________________________________________________________________________________


	ORDINE DELLE LIBRARY DEL PROGETTO
	
		L'ordine delle library del progetto deve essere:
			JRE
			GWT
			App Engine SDK [quello di eclipse]
			Maven Dependencies


	
	CREZIONE DI UN NUOVO BRANCH
	
		Team > Branch/Tag
			Copy to URL > Select >
			  [selezionare una nuova dir sotto branches, es: https://marcelatetest.googlecode.com/svn/branches/econyx-0.5]
			  
		Team / Switch to another branch
		
		
	
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
			
			
	
	



	CREZIONE DI UNA NUOVA VERSIONE SRC GWTCOMMONS
	
	  gwtcommons/pom.xml	>> gae.version e plugin maven-gae-plugin e maven-datanucleus-plugin* (controllare versioni dei jar dipendenti)
	  econyx/pom.xml		>> gae.version e plugin maven-datanucleus-plugin* (controllare versioni dei jar dipendenti)
	
	
	CREZIONE DI UNA NUOVA VERSIONE SRC ECONYX
	
		Oggetti da modificare:
		  econyx/pom.xml	>> version
		  bin/setenv.bat	>> PRJ_VERSION
		  output folder		>> es: econyx/target/econyx-0.4/WEB-INF/classes
	  


	CREZIONE DI UNA NUOVA VERSIONE DEPLOY
	
		Oggetti da modificare:
		  src/main/webapp/WEB-INF/appengine-web.xml
		
	

	CAMBIO VERSIONE APPENGINE

		1) Download appengine java sdk>
			D:\OPT\Gwt\appengine\appengine-java-sdk-X.X.X
			
		2) Add Eclipse Appengine Sdk
			set as default >> questo aggiorna la library su del progetto econyx, però la inverte rispetto a maven (quindi bisogna ripristinare il corretto ordine delle librerie)
			
			NOTA BENE
			non dovrebbe più servire rimuovere datanucleus-appengine-x.x.x.jar dal appengine sdk

		3) Oggetti da modificare:
		  gwtcommons/pom.xml	>> gae.version e plugin maven-gae-plugin e maven-datanucleus-plugin* (controllare versioni dei jar dipendenti)
		  econyx/pom.xml		>> gae.version e plugin maven-datanucleus-plugin* (controllare versioni dei jar dipendenti)
		  
		  setenv.bat            >> set GAE_HOME=D:\OPT\gwt\appengine\appengine-java-sdk-X.X.X

		* il plugin deve essere duplicato perchè l'enhancement va fatto sia su gwtcommons che su econyx

		4) Prima di provare sul nuovo appengine ricordarsi di fare una build full
	

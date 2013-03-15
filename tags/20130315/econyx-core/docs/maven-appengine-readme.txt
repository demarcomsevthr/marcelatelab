
	_________________________________

	IMPORTANTE: COME UTILIZZARE INSIEME MAVEN E GPE (GOOGLE PLUGIN FOR ECLIPSE)
	
	>>> http://googlewebtoolkit.blogspot.com/2010/08/how-to-use-google-plugin-for-eclipse.html <<< 

		ESTRATTO
		
		5. Java Build Path / Order and Export
				move all Maven dependencies to the BOTTOM

				

	_________________________________

	COME UTILIZZARE INSIEME MAVEN E GPE APPENGINE SDK

	Datanucleus Enhancer genera un errore in GPE se trova datanucleus-appengine-X.X.X.jar duplicato 
	nel classpath del progetto
		>>> anche se il problema non è bloccante (se si compila sempre con maven) per risolverlo
			occorre duplicare la directory sdk dell'appengine (quella che GPE scarica in eclipse/plugin)
			e togliere il datanucleus-appengine.X.jar lì presente, e utilizzere questo sdk appengine
			al posto di quello scaricato di default in eclipse/plugin

	_________________________________

	FIXED DEPENDENCIES
	
	Se si vogliono utilizzare dei jar non presenti su maven occorre installarli come dipendenze maven locali.

	VEDI lib-ext E install-jars.bat
	
	gwt-portlets.jar
	
	xstream.jar (ver 1.4-SNAPSHOT inclusa in gwt-portlets 1.0 download, la 1.3.1 su maven non va bene)

		(nota: quest'ultima dipende da xpp3_min-1.1.4c.jar che si trova su maven)


	_________________________________

	LINKS

	ARTICOLO MAVEN + GPE (importante):
		http://googlewebtoolkit.blogspot.com/2010/08/how-to-use-google-plugin-for-eclipse.html

	GWT MAVEN PLUGIN (opzionale):
		http://mojo.codehaus.org/gwt-maven-plugin/

	MAVEN GAE PLUGIN (opzionale):
		http://www.kindleit.net/maven_gae_plugin/index.html


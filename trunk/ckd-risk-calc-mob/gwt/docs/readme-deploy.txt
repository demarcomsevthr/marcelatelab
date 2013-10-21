
	FAST EXAMPLE

	> Save for Enterprise or Ad-Hoc Deploy
	> Save As ckd201b6
	> Application url = 
		https://marcelatelab.googlecode.com/svn/trunk/ckd-risk-calc-mob/gwt/deploy/ckd201b6.ipa
	> Title = CKD 2.0.1.B6
	> Download url da mandare al tester:
		itms-services://?action=download-manifest&url=https://marcelatelab.googlecode.com/svn/trunk/ckd-risk-calc-mob/gwt/deploy/ckd201b6.plist


	__________________________________________________________________________________

	for complete readme see 
	IOS DEVELOPER WORKFLOW GUIDE 
	https://docs.google.com/document/d/1gNDyzJDvNbaP6Q5jcyXYjYAACr2iaAh0isxD8yTkc3I/edit#heading=h.779j63pllnzm
	
	XCODE BUILD AND ARCHIVE

	- CKDRisk proj e CordovaLib proj > Build Settings > Build Active Architecture Only = Yes
	- Scheme: IrcTestDemo
	- Destnation: iOS Device
	- Edit scheme
	- Archive
		- Build config = <release>
		- Archive name = IrcTestDemo
	- Product / Archive	


	DISTRIBUZIONE APP TEST (OTA DISTRIBUTION)

	- Xcode / Product / Archive
	- Organizer / Archives > selezionare il nuovo archive prodotto
		> Distribute
		> Save for Enterprise or Ad-Hoc Deploy
		> Code Sign Identity = Marcello De Marco (iOS Distribution)
		> Save As CKDRisk
		> in Documents
		> Save for Enterprise Distribution
		> Application url = http://marcelatelab.googlecode.com/files/ckdXX.ipa
		> Title = CKD 0.XX
		> Save
		> copy .ipa e .plist to shared
		> NOTA: il file plist è testuale ed editabile in qualsiasi editor
		> download .ipa e .plist su http://code.google.com/hosting/
		> copiare in safari sul device di test la stringa:
	
		itms-services://?action=download-manifest&url=http://marcelatelab.googlecode.com/files/ckdXX.plist
			
		e parte il download/setup
		
		


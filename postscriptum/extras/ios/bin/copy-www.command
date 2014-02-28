## ATTENZIONE
## non copiare come file (problemi di CR dos/unix)
## copiare tramite editor di testo
## poi dare i permessi di esecuzione (chmod +x)
## e controllare i path

#!/bin/bash
SOUR="/Volumes/VMware Shared Folders/shared/postscriptum/www"
DEST="/Users/marcello/Documents/phonegap-3/postscriptum/platforms/ios/www"
cd "$DEST"
rm -fr main
cd "$SOUR"
cp -R main "$DEST"/
#cp * "$DEST"/
exit 0
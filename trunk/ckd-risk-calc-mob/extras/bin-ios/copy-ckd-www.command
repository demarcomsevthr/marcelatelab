#!/bin/bash
SOUR="/Volumes/VMware Shared Folders/shared/ckd-www"
DEST="/Users/marcello/Documents/phonegap-2.4.0/ckd-risk/www"
cd "$DEST"
rm -fr main
cd "$SOUR"
cp -R main "$DEST"/
cp * "$DEST"/
exit 0

## 1 - CERCARE IL .xarchive PRODOTTO (Organizer > open in finder)
## 2 - SETTARLO IN PRJXARCH
## 3 - METTERE IL NOME DEL PROVISIONING PROFILE (E' IL VALORE DELLA CHIAVE "Name" CHE SI TROVA NEL .mobileprovisionig (download dal member center))


PRJXARCH="/Users/admin/Library/Developer/Xcode/Archives/2014-10-30/Protoph 30-10-14 22.47.xcarchive"

xcodebuild -exportArchive -archivePath "$PRJXARCH" -exportPath Protoph -exportFormat ipa -exportProvisioningProfile "Protoph Ad Hoc"


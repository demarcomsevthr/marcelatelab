
var gDbLanguageCookieValue, gDbCookieFound;

function setLocalLanguageCookie(value) {
  gDbLanguageCookieValue = value;
  var db = window.openDatabase("ckd-lang", "1.0", "Ckd Lang DB", 1000);
  db.transaction(doSetLocalCookie, dbError, dbSuccess);
}

function getLocalLanguageCookie() {
  gDbLanguageCookieValue = "";
  gDbCookieFound = "0";
  var db = window.openDatabase("ckd-lang", "1.0", "Ckd Lang DB", 1000);
  db.transaction(doGetLocalCookie, dbError, dbSuccess);
}

function doSetLocalCookie(tx) {
  tx.executeSql('DROP TABLE IF EXISTS DB_LANG_COOKIE');
  tx.executeSql('CREATE TABLE IF NOT EXISTS DB_LANG_COOKIE (data)');
  tx.executeSql('INSERT INTO DB_LANG_COOKIE (data) VALUES ("'+gDbLanguageCookieValue+'")');
}

function doGetLocalCookie(tx) {
  tx.executeSql('CREATE TABLE IF NOT EXISTS DB_LANG_COOKIE (data)');
  tx.executeSql('SELECT * FROM DB_LANG_COOKIE', [], dbQuerySuccess, dbError);
}

function dbQuerySuccess(tx, results) {
  if (results.rows.length > 0) {
    gDbLanguageCookieValue = results.rows.item(0).data;
    gDbCookieFound = "1";
    /*
    $('head').append('<meta name="gwt:property" content="locale='+gDbLanguageCookieValue+'">');
    */
    if (gDbLanguageCookieValue == 'it') {
      if (window.location.href.indexOf('index-it') == -1) {
        window.location.href.replace('index.html', 'index-it.html');
      }
    }
    if (gDbLanguageCookieValue == 'en') {
      if (window.location.href.indexOf('index.html') == -1) {
        window.location.href.replace('index-it.html', 'index.html');
      }
    }
  }
}

function dbError(tx, err) {
  alert("Error processing sql: " + err);
}

function dbSuccess() {
//alert("Db success");
}

getLocalLanguageCookie();


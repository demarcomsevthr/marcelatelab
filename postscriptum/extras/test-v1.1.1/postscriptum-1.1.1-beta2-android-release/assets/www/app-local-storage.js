
function setLocalStorageItem(name, value) {
  window.localStorage.setItem(name, value);
}

function getLocalStorageItem(name) {
  return window.localStorage.getItem(name);
}

function setLocalLanguageCookie(value) {
  window.localStorage.setItem("ckd-language", value);
}

function getLocalLanguageCookie() {
  return window.localStorage.getItem("ckd-language");
}

function switchLocalLanguage() {
  var lang = getLocalLanguageCookie();
  if (lang == 'it') {
    if (window.location.href.indexOf('index-it') == -1) {
      var href = window.location.href.replace('index.html', 'index-it.html');
      window.location.replace(href);
    }
  }
  if (lang == 'en') {
    if (window.location.href.indexOf('index.html') == -1) {
      var href = window.location.href.replace('index-it.html', 'index.html');
      window.location.replace(href);
    }
  }
  return lang;
}

var appLanguage = switchLocalLanguage();


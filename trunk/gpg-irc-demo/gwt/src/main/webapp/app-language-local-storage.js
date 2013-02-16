
function setLocalLanguageCookie(value) {
  window.localStorage.setItem("ckd-language", value);
}

function getLocalLanguageCookie() {
  var lang = window.localStorage.getItem("ckd-language");
  if (lang == 'it') {
    if (window.location.href.indexOf('index-it') == -1) {
      window.location.href.replace('index.html', 'index-it.html');
    }
  }
  if (lang == 'en') {
    if (window.location.href.indexOf('index.html') == -1) {
      window.location.href.replace('index-it.html', 'index.html');
    }
  }
}

getLocalLanguageCookie();



var it = it || {};

it.mate = it.mate || {};

it.mate.gendtest = it.mate.gendtest || {};

it.mate.gendtest.greetings = it.mate.gendtest.greetings || {};

it.mate.gendtest.greetings.CLIENT_ID = '929530856992.apps.googleusercontent.com';

it.mate.gendtest.greetings.SCOPES = 'https://www.googleapis.com/auth/userinfo.email';

it.mate.gendtest.greetings.signedIn = false;

it.mate.gendtest.greetings.init = function(apiRoot) {
  var apisToLoad;
  var callback = function() {
    if (--apisToLoad == 0) {
      it.mate.gendtest.greetings.enableButtons();
      it.mate.gendtest.greetings.signin(true, it.mate.gendtest.greetings.userAuthed);      
    }
  }
  apisToLoad = 2; // must match number of calls to gapi.client.load()
  gapi.client.load('greetings', 'v1', callback, apiRoot);
  gapi.client.load('oauth2', 'v2', callback);
};

it.mate.gendtest.greetings.signin = function(mode, callback) {
  gapi.auth.authorize({
	  client_id: it.mate.gendtest.greetings.CLIENT_ID,
      scope: it.mate.gendtest.greetings.SCOPES, 
      immediate: mode},
      callback);
};

it.mate.gendtest.greetings.auth = function() {
  if (!it.mate.gendtest.greetings.signedIn) {
    var logoutFrame = document.getElementById('logoutIFrame');
	if (logoutFrame != null) {
	  logoutFrame.parentElement.removeChild(logoutFrame);
	}
    it.mate.gendtest.greetings.signin(false, it.mate.gendtest.greetings.userAuthed);
  } else {
    it.mate.gendtest.greetings.logout();
    it.mate.gendtest.greetings.signedIn = false;
    document.getElementById('signinButton').innerHTML = 'Sign in';
    document.getElementById('addGreetingsLogged').disabled = true;
  }
};	

it.mate.gendtest.greetings.userAuthed = function() {
  var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
    if (!resp.code) {
   	  it.mate.gendtest.greetings.signedIn = true;
      document.getElementById('signinButton').innerHTML = 'Sign out';
      document.getElementById('addGreetingsLogged').disabled = false;
    }
  });
};
	
it.mate.gendtest.greetings.enableButtons = function() {
  document.getElementById('getGreeting').onclick = function() {
    it.mate.gendtest.greetings.getGreeting(
        document.getElementById('id').value
      );
  }
  document.getElementById('listGreetings').onclick = function() {
    it.mate.gendtest.greetings.listGreetings();
  }
  document.getElementById('addGreetings').onclick = function() {
	  it.mate.gendtest.greetings.addGreeting(
	      document.getElementById('greeting').value);
  }
  document.getElementById('addGreetingsLogged').onclick = function() {
	  it.mate.gendtest.greetings.addGreetingLogged(
	      document.getElementById('greeting').value);
  }
  document.getElementById('signinButton').onclick = function() {
	  it.mate.gendtest.greetings.auth();
  }  
};

it.mate.gendtest.greetings.print = function(resp) {
  var element = document.getElementById('outputLogDiv');
  if (element == null) {
    element = document.createElement('div');
    element.id = 'outputLogDiv';
    element.classList.add('row');
    document.getElementById('outputLog').appendChild(element);
  }
  element.innerHTML = '';
  if (typeof (resp.message) != 'undefined') {
    element.innerHTML = resp.message;
  } else if (typeof (resp.items) != 'undefined') {
	for (var it=0; it<resp.items.length; it++) {
		element.innerHTML += resp.items[it].message + "<br>";
	}
  } else {
    element.innerHTML = resp;
  }
};

it.mate.gendtest.greetings.getGreeting = function(id) {
  gapi.client.greetings.get({'id': id}).execute(
      function(resp) {
        if (!resp.code) {
          it.mate.gendtest.greetings.print(resp);
        }
      });
};

it.mate.gendtest.greetings.listGreetings = function() {
	  gapi.client.greetings.list().execute(
	      function(resp) {
	        if (!resp.code) {
	          it.mate.gendtest.greetings.print(resp);
	        }
	      });
	};

	
it.mate.gendtest.greetings.addGreetingLogged = function(message) {
  gapi.client.greetings.addLogged({
      'message': message
  }).execute(
      function(resp) {
          it.mate.gendtest.greetings.print(resp);
      });
};

it.mate.gendtest.greetings.addGreeting = function(message) {
  gapi.client.greetings.add({
      'message': message
  }).execute(function(resp) {
      if (!resp.code) {
        it.mate.gendtest.greetings.print("greeting added");
      }
    });
};

it.mate.gendtest.greetings.logout = function() {
  var element = document.createElement('iframe');
  element.id = 'logoutIFrame';
  element.src = 'https://accounts.google.com/logout';
  element.style.display = 'none';
  document.getElementById('outputLog').appendChild(element);
}


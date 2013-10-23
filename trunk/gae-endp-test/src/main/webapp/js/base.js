
var it = it || {};

it.mate = it.mate || {};

it.mate.gendtest = it.mate.gendtest || {};

it.mate.gendtest.greetings = it.mate.gendtest.greetings || {};

// REGISTERED APP = GEnd Test First Web Client //-> PROVATO OK [web origin = https://gendtest.appspot.com]
//it.mate.gendtest.greetings.CLIENT_ID = '929530856992.apps.googleusercontent.com';

// REGISTERED APP = GEnd Test Second Web Client //-> PROVATO KO [missing web origin]
//it.mate.gendtest.greetings.CLIENT_ID = '929530856992-bb0ht17h9o9vbvg43sv0pvu6gfpinhr8.apps.googleusercontent.com';

// REGISTERED APP = My Native Client //-> PROVATO KO [missing web origin]
//it.mate.gendtest.greetings.CLIENT_ID = '929530856992-tgsgml6l0au4b4q12r296o0bdk6o7e4f.apps.googleusercontent.com';

// REGISTERED APP = My Localhost Web Client //-> PROVATO OK [web origin = http://localhost]
it.mate.gendtest.greetings.CLIENT_ID = '929530856992-051orvog0ch2q68bmfggrksu7gp1ifap.apps.googleusercontent.com';


it.mate.gendtest.greetings.SCOPES = 'https://www.googleapis.com/auth/userinfo.email';


it.mate.gendtest.greetings.init = function(apiRoot) {
  var apisToLoad;
  var callback = function() {
    if (--apisToLoad == 0) {
      it.mate.gendtest.greetings.enableButtons();
      
      /* TODO: PROVARE A COMMENTARE */
      it.mate.gendtest.greetings.signin(true,
    		  it.mate.gendtest.greetings.userAuthed);      
      
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
	  
    it.mate.gendtest.greetings.signin(false,
	  it.mate.gendtest.greetings.userAuthed);
  } else {
    it.mate.gendtest.greetings.logout();
    it.mate.gendtest.greetings.signedIn = false;
    document.getElementById('signinButton').innerHTML = 'Sign in';
    document.getElementById('authedGreeting').disabled = true;
  }
};	

it.mate.gendtest.greetings.signedIn = false;

it.mate.gendtest.greetings.userAuthed = function() {
  var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
    if (!resp.code) {
   	  it.mate.gendtest.greetings.signedIn = true;
      document.getElementById('signinButton').innerHTML = 'Sign out';
      document.getElementById('authedGreeting').disabled = false;
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
  document.getElementById('authedGreeting').onclick = function() {
	  it.mate.gendtest.greetings.addUser();
  }
  document.getElementById('signinButton').onclick = function() {
	  it.mate.gendtest.greetings.auth();
  }  
};

it.mate.gendtest.greetings.addUser = function() {
  gapi.client.greetings.addUser().execute(
      function(resp) {
          it.mate.gendtest.greetings.print(resp);
      });
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

	
it.mate.gendtest.greetings.addGreeting = function(greeting) {
  gapi.client.greetings.add({
      'message': greeting
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


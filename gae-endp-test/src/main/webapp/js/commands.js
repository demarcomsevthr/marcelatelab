
var it = it || {};

it.mate = it.mate || {};

it.mate.gendtest = it.mate.gendtest || {};

it.mate.gendtest.commands = it.mate.gendtest.commands || {};

it.mate.gendtest.commands.init = function(apiRoot) {
  var apisToLoad;
  var callback = function() {
    if (--apisToLoad == 0) {
      it.mate.gendtest.commands.enableButtons();
    }
  }
  apisToLoad = 1; // must match number of calls to gapi.client.load()
  gapi.client.load('commands', 'v1', callback, apiRoot);
};

it.mate.gendtest.commands.enableButtons = function() {
  document.getElementById('sendEnableCommand').onclick = function() {
	  it.mate.gendtest.commands.sendEnableCommand();
  };
  document.getElementById('sendDisableCommand').onclick = function() {
	  it.mate.gendtest.commands.sendDisableCommand();
  };
};

it.mate.gendtest.commands.sendEnableCommand = function(message) {
  gapi.client.commands.addAction({
      'action': 1
  }).execute(function(resp) {
      if (!resp.code) {
        it.mate.gendtest.commands.print("enable command posted");
      }
    });
};

it.mate.gendtest.commands.sendDisableCommand = function(message) {
  gapi.client.commands.addAction({
	      'action': 2
  }).execute(function(resp) {
      if (!resp.code) {
        it.mate.gendtest.commands.print("disable command posted");
      }
    });
};

it.mate.gendtest.commands.print = function(resp) {
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


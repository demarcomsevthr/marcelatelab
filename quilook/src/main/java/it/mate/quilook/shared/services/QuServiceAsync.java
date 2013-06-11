package it.mate.quilook.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QuServiceAsync {

  void getQuMessage(AsyncCallback<String> callback);

}

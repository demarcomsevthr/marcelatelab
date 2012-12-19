package it.mate.portlets.shared.services;

import it.mate.portlets.shared.model.PageTemplate;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PortalServiceAsync {

  void getPage(String historyToken, AsyncCallback<PageTemplate> callback);

}

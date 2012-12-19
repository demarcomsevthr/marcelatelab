package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.ContoUtente;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomServiceAsync {

  void findContoUtenteByPortalUser(String portalUserId, AsyncCallback<ContoUtente> callback);

  void updateContoUtente(ContoUtente contoUtente, AsyncCallback<ContoUtente> callback);

}

package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.PortalUser;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PortalUserServiceAsync {

  void update(PortalUser entity, AsyncCallback<PortalUser> callback);

  void create(PortalUser entity, AsyncCallback<PortalUser> callback);

  void findById(String id, AsyncCallback<PortalUser> callback);

  void login(PortalUser portalUser, boolean keepConnection, AsyncCallback<PortalUser> callback);

  void findAll(AsyncCallback<List<PortalUser>> callback);

  void sendActivationMail(PortalUser user, AsyncCallback<Void> callback);

  void updateUserNotActive(PortalUser user, AsyncCallback<PortalUser> callback);

  void logout(PortalUser portalUser, AsyncCallback<Void> callback);

  void getGoogleLoginURL(String redirectURL, AsyncCallback<String> callback);

  void getGoogleLogoutURL(String redirectURL, AsyncCallback<String> callback);

  void updatePassword(PortalUser portalUser, String passwordAttuale, String nuovaPassword, String confermaPassword,
      AsyncCallback<PortalUser> callback);

  void activateUserById(String id, AsyncCallback<PortalUser> callback);

  void getSaldoByPortalUserId(String portalUserId, AsyncCallback<Double> callback);

}

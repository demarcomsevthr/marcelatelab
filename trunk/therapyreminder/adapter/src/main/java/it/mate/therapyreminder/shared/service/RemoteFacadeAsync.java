package it.mate.therapyreminder.shared.service;

import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RemoteFacadeAsync {

  void getServerTime(AsyncCallback<Date> callback);

  void sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion,
      AsyncCallback<String> callback);

  void createAccount(Account entity, AsyncCallback<Account> callback);

  void updateAccount(Account entity, AsyncCallback<Account> callback);

  void checkConnection(AsyncCallback<Boolean> callback);

  void saveSomministrazioni(List<Somministrazione> somministrazioni, Account account, String devInfoId, AsyncCallback<List<Somministrazione>> callback);

  void updateDatiContatto(Contatto tutor, Account account, AsyncCallback<Void> callback);

  void deleteSomministrazioni(List<Somministrazione> somministrazioni, AsyncCallback<Void> callback);


}

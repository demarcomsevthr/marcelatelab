package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;
import it.mate.gwtcommons.client.factories.AbstractCustomClientFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeneralServiceAsync {

  void loadFoldersData(AsyncCallback<Void> callback);

  void exportFoldersData(AsyncCallback<Void> callback);

  void loadPagesData(AsyncCallback<Void> callback);

  void exportPagesData(AsyncCallback<Void> callback);

  void initPagesData(AsyncCallback<Void> callback);

  void importPortalData(AsyncCallback<Void> callback);

  void exportPortalData(AsyncCallback<Void> callback);

  void getCustomClientFactory(AsyncCallback<AbstractCustomClientFactory> callback);

  void clearCache(AsyncCallback<Void> callback);

  void getPropertiesFromServer(AsyncCallback<Map<String, String>> callback);

  void storePortalSessionState(PortalSessionState state, AsyncCallback<Void> callback);

  void retrievePortalSessionState(int moduleType, AsyncCallback<PortalSessionState> callback);

  void deleteAll(AsyncCallback<Void> callback);

  void getServerContextUrl(AsyncCallback<String> callback);

  void reloadProperties(AsyncCallback<Void> callback);

  void generateRandomCustomers(int number, Date date, AsyncCallback<Void> callback);

  void generateRandomOrders(int number, Date date, AsyncCallback<Void> callback);

  void gdataSpreadsheetTest(AsyncCallback<String> callback);

  void testServiceException(AsyncCallback<Void> callback);

  void instanceCacheDump(AsyncCallback<List<CacheDumpEntry>> callback);

  void cobraTest(AsyncCallback<Void> callback);

  void createBlobstoreUploadUrl(String url, AsyncCallback<String> callback);

  void refreshUsersCache(AsyncCallback<Void> callback);

}

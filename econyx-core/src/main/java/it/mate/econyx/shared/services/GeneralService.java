package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;
import it.mate.gwtcommons.client.factories.AbstractCustomClientFactory;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".generalService")
public interface GeneralService extends RemoteService {
  
  public static final String PORTAL_USER_COOKIE = "ECONYX_PORTAL_USER"; 

  public AbstractCustomClientFactory getCustomClientFactory();
    
  public void loadFoldersData();

  public void exportFoldersData();
  
  public void initPagesData();

  public void loadPagesData();

  public void exportPagesData();

  public void importPortalData();

  public void exportPortalData();

  public void clearCache();
  
  public Map<String, String> getPropertiesFromServer();
  
  public void storePortalSessionState(PortalSessionState state);
  
  public PortalSessionState retrievePortalSessionState(int moduleType) throws ServiceException;
  
  public void deleteAll();
  
  public String getServerContextUrl();
  
  public void reloadProperties();
  
  public void generateRandomCustomers(int number, Date date);
  
  public void generateRandomOrders(int number, Date date);
  
  
  
  public String gdataSpreadsheetTest();
  
  public void testServiceException () throws ServiceException;
  

  public List<CacheDumpEntry> instanceCacheDump ();
  
  
  public void cobraTest();
  
  public String createBlobstoreUploadUrl(String url);
  
}

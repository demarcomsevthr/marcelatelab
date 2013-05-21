package it.mate.econyx.server.services;

import it.mate.econyx.server.model.impl.ExportJobDs;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface GeneralAdapter {

  public long findNextCounterValue();

  public void deleteCounter();

  public void clearCache();
  
  public void storePortalSessionState(HttpServletRequest request, PortalSessionState state);
  
  public PortalSessionState retrievePortalSessionState(HttpServletRequest request);
  
  public void deleteAllData ();
  
  public void generateRandomCustomers(int numero, Date date);
  
  public void generateRandomOrders(int number, Date date);
  
  public List<CacheDumpEntry> instanceCacheDump ();
  
  public void refreshUsersCache();
  
  public void deleteOrdersData ();
  
  public void cobraTest();
  
  public ExportJobDs createExportJob ();
  
  public ExportJobDs findExportJobById (String id);
  
  public ExportJobDs updateExportJob (ExportJobDs exportJob);
  
}

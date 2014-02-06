package it.mate.econyx.server.services;

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
  
  public void deleteAll ();
  
  public void generateRandomCustomers(int numero, Date date);
  
  public void generateRandomOrders(int number, Date date);
  
  public List<CacheDumpEntry> instanceCacheDump ();
  
  
  public void cobraTest();
  
}
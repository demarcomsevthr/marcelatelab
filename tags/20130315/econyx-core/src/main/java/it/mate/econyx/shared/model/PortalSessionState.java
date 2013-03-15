package it.mate.econyx.shared.model;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;

import java.io.Serializable;

import com.google.gwt.event.shared.EventBus;

@SuppressWarnings("serial")
public class PortalSessionState implements Serializable {
  
  private PortalUser loggedUser;
  
  private boolean googleAuthentication = false;
  
  private String pageId;
  private String pageCode;
  
  private transient Order openOrder;
  
  private transient boolean openOrderCheckDone = false;
  
  private String openOrderId;
  
  private String templateName;
  
  private Customer customer;
  
  public final static int MODULE_NOT_SET = 0;
  
  public final static int MODULE_SITE = 1;
  
  public final static int MODULE_ADMIN = 2;
  
  private Integer moduleType = MODULE_NOT_SET;
  
  
  private static transient Monitor monitor;
  

  public PortalSessionState() {
    
  }
  
  public PortalSessionState(Integer moduleType) {
    setModuleType(moduleType);
  }

  public PortalSessionState(Integer moduleType, PortalPage currentPage) {
    setModuleType(moduleType);
    setCurrentPage(currentPage);
  }

  public static void activateStateMonitor(EventBus eventBus) {
    monitor = new Monitor(eventBus);
  }
  

  public PortalUser getLoggedUser() {
    return loggedUser;
  }

  public void setLoggedUser(PortalUser loggedUser) {
    boolean valueChanged = isValueChanged(this.loggedUser, loggedUser);
    this.loggedUser = loggedUser;
    if (valueChanged)
      fireChangeEvent();
  }

  public String getCurrentPageId() {
    return pageId;
  }
  
  public String getCurrentPageCode() {
    return pageCode;
  }

  public void setCurrentPage(PortalPage currentPage) {
    boolean valueChanged = isValueChanged(this.pageId, currentPage != null ? currentPage.getId() : null);
    valueChanged = valueChanged || isValueChanged(this.pageCode, currentPage != null ? currentPage.getCode() : null);
    this.pageId = currentPage != null ? currentPage.getId() : null;
    this.pageCode = currentPage != null ? currentPage.getCode() : null;
    if (valueChanged)
      fireChangeEvent();
  }
  
  public String getOpenOrderId() {
    return openOrderId;
  }

  public void setOpenOrder(Order openOrder) {
    boolean valueChanged = openOrder == null || isValueChanged(this.openOrderId, openOrder.getId());
    this.openOrderId = openOrder != null ? openOrder.getId() : null;
    this.openOrder = openOrder;
    if (valueChanged)
      fireChangeEvent();
  }
  
  public Order getOpenOrder() {
    return openOrder;
  }
  
  public void setOpenOrderCheckDone(boolean openOrderCheckDone) {
    this.openOrderCheckDone = openOrderCheckDone;
  }
  
  public boolean isOpenOrderCheckDone() {
    return openOrderCheckDone;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    boolean valueChanged = isValueChanged(this.templateName, templateName);
    this.templateName = templateName;
    if (valueChanged)
      fireChangeEvent();
  }
  
  public Customer getCustomer() {
    return customer;
  }
  
  public void setCustomer(Customer customer) {
    boolean valueChanged = isValueChanged(this.customer, customer);
    this.customer = customer;
    if (valueChanged)
      fireChangeEvent();
  }
  
  public Integer getModuleType() {
    return moduleType;
  }

  public void setModuleType(Integer moduleType) {
    boolean valueChanged = isValueChanged(this.moduleType, moduleType);
    this.moduleType = moduleType;
    if (valueChanged)
      fireChangeEvent();
  }

  private boolean isValueChanged(Object oldValue, Object newValue) {
    if (oldValue == null && newValue == null)
      return false;
    if (oldValue == null && newValue != null)
      return true;
    if (oldValue != null && newValue == null)
      return true;
    return !oldValue.equals(newValue);
  }

  private static class Monitor {
    private EventBus eventBus;
    private Monitor(EventBus eventBus) {
      this.eventBus = eventBus;
    }
  }
  
  public static void forcePortalSessionChangeEvent() {
    PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
    if (portalSessionState != null)
      portalSessionState.fireChangeEvent();
  }
  
  public void setGoogleAuthentication(boolean googleAuthentication) {
    this.googleAuthentication = googleAuthentication;
  }
  
  public boolean isGoogleAuthentication() {
    return googleAuthentication;
  }
  
  private void fireChangeEvent() {
    if (monitor != null) {
      monitor.eventBus.fireEvent(new PortalSessionStateChangeEvent(this));
    }
  }

  @Override
  public String toString() {
    return "PortalSessionState [loggedUser=" + (loggedUser != null ? loggedUser.getScreenName() : "null") 
        + ", googleAuthentication=" + googleAuthentication + ", pageId=" + pageId
        + ", pageCode=" + pageCode + ", openOrderId=" + openOrderId 
        + ", templateName=" + templateName 
        + ", customer=" + (customer != null ? customer.getId() : "null")
        + ", moduleType=" + (moduleType == MODULE_SITE ? "site" : (moduleType == MODULE_ADMIN ? "admin" : "notSet" )) + "]";
  }

}

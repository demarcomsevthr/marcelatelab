package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface OrderState extends Serializable {

  public String getId();
  
  public void setId(String id);

  public String getCode();
  
  public void setCode(String code);
  
  public OrderStateConfig getConfig();

  public void setConfig(OrderStateConfig orderStateConfig);

  public Boolean getChecked();
  
  public void setChecked(Boolean checked);

  public PortalUser getPortalUser();

  public void setPortalUser(PortalUser portalUser);

  public Date getDate();
  
  public void setDate(Date date);
  
  public Order getOrder();
  
  public void setOrder(Order order);
  
  public Boolean getDisableEmailToCustomerSubmission();

  public void setDisableEmailToCustomerSubmission(Boolean disableEmailToCustomerSubmission);
  
}

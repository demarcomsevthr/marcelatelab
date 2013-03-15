package it.mate.econyx.shared.model;

import java.io.Serializable;


public interface OrderStateConfig extends Serializable {
  
  public String getId();
  
  public void setId(String id);
  
  public static final String OPENED = "OPE";
  
  public static final String INSERTED = "INS";
  
  public static final String CONFIRMED = "CON";
  
  public static final String PAID_WAIT = "PWA";
  
  public static final String PAID = "PAI";
  
  public static final String SHIPPING = "ISH";
  
  public static final String SHIPPED = "SHI";
  
  public static final String CANCELLED = "CAN";
  
  public Integer getOrderNm();

  public void setOrderNm(Integer nm);

  public String getCode();
  
  public void setCode(String code);

  public String getDescription();
  
  public void setDescription(String description);

  /**
   * comma separated list of codes
   */
  public String getRequiredOrderStateCodes();
  
  public void setRequiredOrderStateCodes(String orderStateCodes);

  public static final String YES = "Y";
  
  public static final String NO = "N";
  
  public static final String ONCONFIRM = "C";
  
  public String getEmailToCustomerSendType();
  
  public void setEmailToCustomerSendType(String type);
  
  public String getEmailToAdminsSendType();
  
  public void setEmailToAdminsSendType(String type);
  
  public String getEmailContent();
  
  public void setEmailContent(String content);
  
  public String getDisablingOrderStateCodes();
  
  public void setDisablingOrderStateCodes(String disablingOrderStateCodes);
  
  public Boolean getAttachOrderReport();

  public void setAttachOrderReport(Boolean attachOrderReport);

  
  
  // 16/01/2013
  
  public void setQuantityUpdatable(Boolean quantityUpdatable);

  public boolean isQuantityUpdatable();

  public Boolean getQuantityUpdatable();

  public void setAskDeliveryInformations(Boolean askDeliveryInformations);

  public boolean askDeliveryInformations();

  public Boolean getAskDeliveryInformations();

  public void setPrintButtonEnabled(Boolean printButtonEnabled);

  public boolean isPrintButtonEnabled();

  public Boolean getPrintButtonEnabled();

  public void setNextStateDescription(String nextStateDescription);

  public String getNextStateDescription();

  public void setNextStateCode(String nextStateCode);

  public String getNextStateCode();

}

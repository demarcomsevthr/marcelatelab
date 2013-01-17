package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.OrderStateConfig;

@SuppressWarnings("serial")
public class OrderStateConfigTx implements OrderStateConfig {

  String id;
  
  String code;
  
  Integer orderNm;
  
  String description;
  
  String requiredOrderStateCodes;
  
  String emailToCustomerSendType;
  
  String emailToAdminsSendType;
  
  String emailContent;
  
  Boolean attachOrderReport;

  String disablingOrderStateCodes;
  
  
  
  // 16/01/2013
  
  private String nextStateCode;
  private String nextStateDescription;
  private Boolean printButtonEnabled;
  private Boolean askDeliveryInformations;
  private Boolean quantityUpdatable;
  
  
  @Override
  public String toString() {
    return "OrderStateConfigTx [code=" + code + ", description=" + description + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRequiredOrderStateCodes() {
    return requiredOrderStateCodes;
  }

  public void setRequiredOrderStateCodes(String requiredOrderStateCodes) {
    this.requiredOrderStateCodes = requiredOrderStateCodes;
  }
  
  public String getEmailToCustomerSendType() {
    return emailToCustomerSendType;
  }

  public void setEmailToCustomerSendType(String type) {
    this.emailToCustomerSendType = type;
  }

  public String getEmailToAdminsSendType() {
    return emailToAdminsSendType;
  }

  public void setEmailToAdminsSendType(String type) {
    this.emailToAdminsSendType = type;
  }

  public String getEmailContent() {
    return emailContent;
  }

  public void setEmailContent(String emailContent) {
    this.emailContent = emailContent;
  }

  public String getDisablingOrderStateCodes() {
    return disablingOrderStateCodes;
  }

  public void setDisablingOrderStateCodes(String disablingOrderStateCodes) {
    this.disablingOrderStateCodes = disablingOrderStateCodes;
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public Boolean getAttachOrderReport() {
    return attachOrderReport;
  }

  public void setAttachOrderReport(Boolean attachOrderReport) {
    this.attachOrderReport = attachOrderReport;
  }

  
  // 16/01/2013
  
  public String getNextStateCode() {
    return nextStateCode;
  }

  public void setNextStateCode(String nextStateCode) {
    this.nextStateCode = nextStateCode;
  }

  public String getNextStateDescription() {
    return nextStateDescription;
  }

  public void setNextStateDescription(String nextStateDescription) {
    this.nextStateDescription = nextStateDescription;
  }

  public Boolean getPrintButtonEnabled() {
    return printButtonEnabled;
  }
  
  public boolean isPrintButtonEnabled() {
    return printButtonEnabled != null ? printButtonEnabled : false;
  }
  
  public void setPrintButtonEnabled(Boolean printButtonEnabled) {
    this.printButtonEnabled = printButtonEnabled;
  }

  public Boolean getAskDeliveryInformations() {
    return askDeliveryInformations;
  }

  public boolean askDeliveryInformations() {
    return askDeliveryInformations != null ? askDeliveryInformations : false;
  }

  public void setAskDeliveryInformations(Boolean askDeliveryInformations) {
    this.askDeliveryInformations = askDeliveryInformations;
  }

  public Boolean getQuantityUpdatable() {
    return quantityUpdatable;
  }

  public boolean isQuantityUpdatable() {
    return quantityUpdatable != null ? quantityUpdatable : false;
  }

  public void setQuantityUpdatable(Boolean quantityUpdatable) {
    this.quantityUpdatable = quantityUpdatable;
  }
  
}

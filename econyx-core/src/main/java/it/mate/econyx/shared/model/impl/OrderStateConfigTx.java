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
  
}

package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.impl.OrderStateConfigTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=OrderStateConfigTx.class)
public class OrderStateConfigDs implements OrderStateConfig, HasKey {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String code;
  
  @Persistent
  Integer orderNm;
  
  @Persistent
  String description;
  
  @Persistent
  String requiredOrderStateCodes;
  
  @Persistent
  String emailToCustomerSendType;
  
  @Persistent
  String emailToAdminsSendType;
  
  @Persistent
  String emailContent;
  
  @Persistent
  Boolean attachOrderReport;
  
  @Persistent
  String disablingOrderStateCodes;
  


  public Key getKey() {
    return id;
  }
  
  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
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

  public void setEmailToCustomerSendType(String emailToCustomerSendType) {
    this.emailToCustomerSendType = emailToCustomerSendType;
  }

  public String getEmailToAdminsSendType() {
    return emailToAdminsSendType;
  }

  public void setEmailToAdminsSendType(String emailToAdminsSendType) {
    this.emailToAdminsSendType = emailToAdminsSendType;
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

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
@CacheableEntity (txClass=OrderStateConfigTx.class, instanceCache=true)
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
  

  // 16/01/2013
  
  @Persistent
  private String nextStateCode;
  
  @Persistent
  private String nextStateDescription;
  
  @Persistent
  private Boolean printButtonEnabled;
  
  @Persistent
  private Boolean askDeliveryInformations;
  
  @Persistent
  private Boolean quantityUpdatable;
  

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

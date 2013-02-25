package it.mate.ckd.client.i18n;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedMessages extends Messages {

  LocalizedMessages IMPL = GWT.create(LocalizedMessages.class);
  
  @DefaultMessage("Insert parameters")
  String HomeView_paramBtn_text();
  
  @DefaultMessage("Parameters")
  String CKDOutputView_headerBackButton_text();
  
  @DefaultMessage("VERY LOW")
  String CKDOutputView_veryLowRisk_text();
  
  @DefaultMessage("LOW")
  String CKDOutputView_lowRisk_text();
  
  @DefaultMessage("MIDDLE")
  String CKDOutputView_middleRisk_text();
  
  @DefaultMessage("HIGH")
  String CKDOutputView_highRisk_text();
  
  @DefaultMessage("VERY HIGH")
  String CKDOutputView_veryHighRisk_text();
  
}

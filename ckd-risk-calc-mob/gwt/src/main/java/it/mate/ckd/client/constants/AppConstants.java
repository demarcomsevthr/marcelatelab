package it.mate.ckd.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;

public interface AppConstants extends Messages {

  AppConstants IMPL = GWT.create(AppConstants.class);
  
  @DefaultMessage("")
  String tabletAppName();
  
  @DefaultMessage("")
  String phoneAppName();
  
  @DefaultMessage("Version 0.0 - Powered by Medup Dev.")
  String versionCredits();
  
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
  
  @DefaultMessage("") String ProtocolStep_1_QuestionText();
  @DefaultMessage("") String ProtocolStep_1_Answer1Text();
  @DefaultMessage("-1") String ProtocolStep_1_Answer1Step();
  @DefaultMessage("") String ProtocolStep_1_Answer1EndText();
  @DefaultMessage("") String ProtocolStep_1_Answer2Text();
  @DefaultMessage("-1") String ProtocolStep_1_Answer2Step();
  @DefaultMessage("") String ProtocolStep_1_Answer2EndText();
  
  @DefaultMessage("") String ProtocolStep_2_QuestionText();
  @DefaultMessage("") String ProtocolStep_2_Answer1Text();
  @DefaultMessage("-1") String ProtocolStep_2_Answer1Step();
  @DefaultMessage("") String ProtocolStep_2_Answer1EndText();
  @DefaultMessage("") String ProtocolStep_2_Answer2Text();
  @DefaultMessage("-1") String ProtocolStep_2_Answer2Step();
  @DefaultMessage("") String ProtocolStep_2_Answer2EndText();
  
  @DefaultMessage("") String ProtocolStep_3_QuestionText();
  @DefaultMessage("") String ProtocolStep_3_Answer1Text();
  @DefaultMessage("-1") String ProtocolStep_3_Answer1Step();
  @DefaultMessage("") String ProtocolStep_3_Answer1EndText();
  @DefaultMessage("") String ProtocolStep_3_Answer2Text();
  @DefaultMessage("-1") String ProtocolStep_3_Answer2Step();
  @DefaultMessage("") String ProtocolStep_3_Answer2EndText();
  
  @DefaultMessage("") String ProtocolStep_4_QuestionText();
  @DefaultMessage("") String ProtocolStep_4_Answer1Text();
  @DefaultMessage("-1") String ProtocolStep_4_Answer1Step();
  @DefaultMessage("") String ProtocolStep_4_Answer1EndText();
  @DefaultMessage("") String ProtocolStep_4_Answer2Text();
  @DefaultMessage("-1") String ProtocolStep_4_Answer2Step();
  @DefaultMessage("") String ProtocolStep_4_Answer2EndText();
  
  @DefaultMessage("") String ProtocolStep_5_QuestionText();
  @DefaultMessage("") String ProtocolStep_5_Answer1Text();
  @DefaultMessage("-1") String ProtocolStep_5_Answer1Step();
  @DefaultMessage("") String ProtocolStep_5_Answer1EndText();
  @DefaultMessage("") String ProtocolStep_5_Answer2Text();
  @DefaultMessage("-1") String ProtocolStep_5_Answer2Step();
  @DefaultMessage("") String ProtocolStep_5_Answer2EndText();
  
  @DefaultMessage("") String ProtocolStep_6_QuestionText();
  @DefaultMessage("") String ProtocolStep_6_Answer1Text();
  @DefaultMessage("-1") String ProtocolStep_6_Answer1Step();
  @DefaultMessage("") String ProtocolStep_6_Answer1EndText();
  @DefaultMessage("") String ProtocolStep_6_Answer2Text();
  @DefaultMessage("-1") String ProtocolStep_6_Answer2Step();
  @DefaultMessage("") String ProtocolStep_6_Answer2EndText();
  
}

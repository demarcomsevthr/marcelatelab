package it.mate.ckd.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;

public interface AppConstants extends Messages {

  AppConstants IMPL = GWT.create(AppConstants.class);
  
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
  
  @DefaultMessage("false")
  String HomeView_devInfo_visible();
  
  @DefaultMessage("false")
  String SpinnerDoubleBox_enSeparator_fix_enabled();
  
  public class Cast {
    
    public static boolean SpinnerDoubleBox_enSeparator_fix_enabled() {
      return Boolean.parseBoolean(AppConstants.IMPL.SpinnerDoubleBox_enSeparator_fix_enabled());
    }
    
  }
  
}

package it.mate.ckd.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;


public interface AppProperties extends Constants {
  
  AppProperties IMPL = GWT.create(AppProperties.class);
  
  @DefaultBooleanValue(false)
  boolean SpinnerIntegerBox_disableSpinButtons();
  
  @DefaultBooleanValue(false)
  boolean SpinnerDoubleBox_disableSpinButtons();
  
  @DefaultBooleanValue(false)
  boolean extendedVersion();
  
  @DefaultBooleanValue(false)
  boolean useDebugCKD();
  
  @DefaultBooleanValue(false)
  boolean HomeView_devInfo_visible();
  
  @DefaultBooleanValue(false)
  boolean HomeView_langInfo_visible();
  
  @DefaultBooleanValue(false)
  boolean CKDOutputView_adjust_ckdHelpBtn_position();
  
  @DefaultBooleanValue(false)
  boolean SpinnerDoubleBox_enSeparator_fix_enabled();
  
  @DefaultBooleanValue(false)
  boolean SpinnerIntegerBox_keyPress_fix_enabled();
  
  @DefaultBooleanValue(false)
  boolean SpinnerDoubleBox_keyPress_fix_enabled();
  
  public class Global {
    
    public static boolean isExtendedVersion() {
      return AppProperties.IMPL.extendedVersion();
    }
    
  }
  
  
}

package it.mate.ckd.client.config;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;


public interface ClientProperties extends Constants {
  
  ClientProperties IMPL = GWT.create(ClientProperties.class);
  
  @DefaultBooleanValue(false)
  boolean SpinnerIntegerBox_disableSpinButtons();
  
  @DefaultBooleanValue(false)
  boolean SpinnerDoubleBox_disableSpinButtons();
  
}

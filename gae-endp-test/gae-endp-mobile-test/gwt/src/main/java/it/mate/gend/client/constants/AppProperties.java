package it.mate.gend.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants.DefaultBooleanValue;
import com.google.gwt.i18n.client.Messages;

public interface AppProperties extends Messages {

  AppProperties IMPL = GWT.create(AppProperties.class);
  
  @DefaultMessage("")
  String tabletAppName();
  
  @DefaultMessage("")
  String phoneAppName();
  
  @DefaultMessage("Version 0.0 - Powered by Medup Dev.")
  String versionCredits();
  
  @DefaultMessage("Insert parameters")
  String HomeView_paramBtn_text();
  
  @DefaultBooleanValue(false)
  boolean extendedVersion();
  
}

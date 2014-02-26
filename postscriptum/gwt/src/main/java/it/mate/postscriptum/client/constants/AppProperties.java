package it.mate.postscriptum.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface AppProperties extends Constants {

  AppProperties IMPL = GWT.create(AppProperties.class);
  
  @DefaultStringValue("")
  String tabletAppName();
  
  @DefaultStringValue("")
  String phoneAppName();
  
  @DefaultStringValue("Version 0.1 - Powered by MDM")
  String versionCredits();
  
  @DefaultBooleanValue(false)
  boolean extendedVersion();
  
}

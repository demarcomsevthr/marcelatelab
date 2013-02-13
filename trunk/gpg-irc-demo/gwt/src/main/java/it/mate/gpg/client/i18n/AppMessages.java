package it.mate.gpg.client.i18n;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;

public interface AppMessages extends Messages {

  AppMessages IMPL = GWT.create(AppMessages.class);
  
  @DefaultMessage("Insert parameters")
  String insertParameters();
  
}

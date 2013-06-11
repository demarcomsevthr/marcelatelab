package it.mate.quilook.client.factories;

import it.mate.econyx.client.factories.AppGinjector;

import com.google.gwt.core.client.GWT;

public class AdminClientFactoryImpl extends it.mate.econyx.client.factories.AdminClientFactoryImpl {

  @Override
  protected AppGinjector createGinjector() {
    return GWT.create(AdminGinjector.class);
  }
  
}

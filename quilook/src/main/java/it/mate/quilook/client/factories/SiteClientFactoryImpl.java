package it.mate.quilook.client.factories;

import it.mate.econyx.client.factories.AppGinjector;

import com.google.gwt.core.client.GWT;

public class SiteClientFactoryImpl extends it.mate.econyx.client.factories.SiteClientFactoryImpl {
  
  @Override
  protected AppGinjector createGinjector() {
    return GWT.create(SiteGinjector.class);
  }
  
  
}

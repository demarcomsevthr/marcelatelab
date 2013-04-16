package it.mate.econyx.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface AppClientBundle extends ClientBundle {

  public static final AppClientBundle INSTANCE = GWT.create(AppClientBundle.class);
  
  @Source ({"app-bundle.css", "app-bundle-custom.css"})
  public CssResource css();

  
}

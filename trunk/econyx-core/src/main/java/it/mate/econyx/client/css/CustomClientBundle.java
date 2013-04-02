package it.mate.econyx.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CustomClientBundle extends ClientBundle {

  public static final CustomClientBundle INSTANCE = GWT.create(CustomClientBundle.class);
  
  @Source ("bundle-custom.css")
  public CssResource css();

  
}

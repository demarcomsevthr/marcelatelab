package it.mate.econyx.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CoreClientBundle extends ClientBundle {
  
  public static final CoreClientBundle INSTANCE = GWT.create(CoreClientBundle.class);
  
  @Source ("it/mate/econyx/client/css/core.css")
  public CssResource core();

}

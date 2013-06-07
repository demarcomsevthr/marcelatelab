package it.mate.portlets.client.ui.gwtp;

import it.mate.portlets.client.ui.Portlet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseGwtpPortlet extends Portlet {

  public interface ViewUiBinder extends UiBinder<Widget, BaseGwtpPortlet> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField SimplePanel gwtpPanel;

  protected abstract void initGwtp(SimplePanel gwtpPanel);
  
  public BaseGwtpPortlet() {
    super();
    initUI();
  }
  
  protected void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    initGwtp(gwtpPanel);
  }
  
}

package it.mate.portlets.client.ui.mvp;

import it.mate.portlets.client.ui.Portlet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseMvpPortlet extends Portlet {

  public interface ViewUiBinder extends UiBinder<Widget, BaseMvpPortlet> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField SimplePanel mvpView;

  protected abstract void initMvp(SimplePanel mvpPanel);
  
  public BaseMvpPortlet() {
    super();
    initUI();
  }
  
  protected void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    initMvp(mvpView);
  }
  
}

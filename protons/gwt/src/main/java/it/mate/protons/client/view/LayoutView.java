package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.ui.OnsLayoutView;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsSlidingMenu;
import it.mate.protons.client.view.LayoutView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class LayoutView extends AbstractBaseView<Presenter> implements OnsLayoutView {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, LayoutView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  
  @UiField OnsNavigator navigator;
  
  
  public LayoutView() {
    initUI();
  }

  private void initProvidedElements() {

  }
  
  @Override
  public OnsNavigator getOnsNavigator() {
    return navigator;
  }

  public OnsSlidingMenu getOnsSlidingMenu() {
    return null;
  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }
  
}

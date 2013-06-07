package it.mate.quilook.client.view;

import it.mate.quilook.client.presenter.SecondPagePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class SecondPageView extends ViewImpl implements SecondPagePresenter.MyView {
  
  public interface ViewUiBinder extends UiBinder<Widget, SecondPageView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public SecondPageView() {

  }

  @Override
  public Widget asWidget() {
    return uiBinder.createAndBindUi(this);
  }

}

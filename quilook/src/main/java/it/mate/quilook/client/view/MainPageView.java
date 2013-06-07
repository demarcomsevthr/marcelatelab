package it.mate.quilook.client.view;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.quilook.client.factories.AppClientFactory;
import it.mate.quilook.client.presenter.MainPagePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {
  
  public interface ViewUiBinder extends UiBinder<Widget, MainPageView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  MainPagePresenter presenter;
  
  public MainPageView() {
    AppClientFactory.IMPL.getGwtpGinjector().getMainPagePresenter().get(new AsyncCallback<MainPagePresenter>() {
      public void onSuccess(MainPagePresenter result) {
        MainPageView.this.presenter = result;
      }
      public void onFailure(Throwable caught) {
        GwtUtils.log("error");
      }
    });
  }

  @Override
  public Widget asWidget() {
    return uiBinder.createAndBindUi(this);
  }
  
  @UiHandler ("goToBtn")
  public void onGotoSencondBtn (ClickEvent event) {
    presenter.goToSecondPlace();
  }

}

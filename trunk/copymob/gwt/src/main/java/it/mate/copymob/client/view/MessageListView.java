package it.mate.copymob.client.view;

import it.mate.copymob.client.view.MessageListView.Presenter;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsToolbarButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class MessageListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void showMenu();
  }

  public interface ViewUiBinder extends UiBinder<Widget, MessageListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsToolbarButton btnMenu;
  
  public MessageListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    btnMenu.setVisible(OnsenUi.isSlidingMenuLayoutPattern());
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }

  @UiHandler("btnMenu")
  public void onBtnMenu(TapEvent event) {
    getPresenter().showMenu();
  }
  
}

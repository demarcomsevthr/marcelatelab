package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.utils.OnsUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;
import it.mate.protons.client.view.SettingsView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class SettingsView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, SettingsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsButton btnPop;
  @UiField Label counterLbl;
  
  public SettingsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    btnPop.onClickTest(new JSOCallback() {
      public void handle(JavaScriptObject jso) {
        OnsUtils.log("btnPop touched");
//      getPresenter().goToHomeView();
        getPresenter().goToPrevious();
      }
    });
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
    if (model instanceof String) {
      counterLbl.setText((String)model);
    }
    
  }

}

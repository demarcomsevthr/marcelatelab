package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.view.TherapyListView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class TherapyListView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToTherapyEditView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, TherapyListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  public TherapyListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler ("newBtn")
  public void onNewBtn (TouchEndEvent event) {
    getPresenter().goToTherapyEditView();
  }
  
}

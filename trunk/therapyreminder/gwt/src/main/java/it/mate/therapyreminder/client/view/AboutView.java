package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.AboutView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class AboutView extends BaseMgwtView <Presenter> {
  
  public interface Presenter extends BasePresenter, SignPanel.Presenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, AboutView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label header1Label;
  @UiField Label header2Label;
  
  public AboutView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    wrapperPanel.getElement().getStyle().clearHeight();
    header1Label.setText("Therapy Reminder " + AppProperties.IMPL.versionNumber());
    header2Label.setText("By " + AppProperties.IMPL.devName() + " @2014");
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
}

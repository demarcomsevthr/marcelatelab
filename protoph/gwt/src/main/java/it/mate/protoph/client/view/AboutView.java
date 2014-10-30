package it.mate.protoph.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protoph.client.constants.AppProperties;
import it.mate.protoph.client.ui.SignPanel;
import it.mate.protoph.client.view.AboutView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public class AboutView extends BaseMgwtView <Presenter> {
  
  public interface Presenter extends BasePresenter, SignPanel.Presenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, AboutView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label header1Label;
  @UiField Label header2Label;
  
  @UiField ScrollPanel innerScrollPanel;
  
  public AboutView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    disableMainScrolling();
    header1Label.setText("Therapy Reminder " + AppProperties.IMPL.versionNumber());
    header2Label.setText("By " + AppProperties.IMPL.devName() + " @2014");
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    adjustInnerScrollPanelHeight(innerScrollPanel);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
}

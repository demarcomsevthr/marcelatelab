package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CalEventEditViewImpl extends AbstractBaseView<CalEventEditView.Presenter> implements CalEventEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, CalEventEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<CalEventEditView.Presenter> adminTab;
  
  List<AdminTabPanel.Section<Presenter>> sections;
  
  public CalEventEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) { 
        getPresenter().update((CalEvent)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  private void initSections(CalEvent event) {
    if (sections == null) {
      sections = new ArrayList<AdminTabPanel.Section<CalEventEditView.Presenter>>();
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Generale")
          .setView(new CalEventEditGeneralView()));
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Testo")
          .setView(new CalEventEditHtmlView()));
      adminTab.setSections(sections);
    }
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof CalEvent) {
      CalEvent event = (CalEvent)model;
      nameLabel.setText("Editing event: " + (event != null ? event.getName() : "new event"));
      initSections(event);
      adminTab.setPresenter(getPresenter());
      adminTab.setModel(model, null);
    }
  }
  
}

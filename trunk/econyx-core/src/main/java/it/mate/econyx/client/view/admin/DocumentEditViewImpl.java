package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.DocumentEditView;
import it.mate.econyx.shared.model.Document;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DocumentEditViewImpl extends AbstractBaseView<DocumentEditView.Presenter> implements DocumentEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<DocumentEditView.Presenter> adminTab;
  
  List<AdminTabPanel.Section<Presenter>> sections;
  
  public DocumentEditViewImpl() {
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
        getPresenter().update((Document)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  private void initSections(Document folder) {
    if (sections == null) {
      sections = new ArrayList<AdminTabPanel.Section<DocumentEditView.Presenter>>();
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Generale")
          .setView(new DocumentEditGeneralView()));
      adminTab.setSections(sections);
    }
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Document) {
      Document document = (Document)model;
      nameLabel.setText("Editing document: " + (document != null ? document.getName() : "new document"));
      initSections(document);
      adminTab.setPresenter(getPresenter());
      adminTab.setModel(model, null);
    }
  }
  
}

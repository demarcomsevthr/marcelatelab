package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.DocumentFolderEditView;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DocumentFolderEditViewImpl extends AbstractBaseView<DocumentFolderEditView.Presenter> implements DocumentFolderEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentFolderEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<DocumentFolderEditView.Presenter> adminTab;
  
  List<AdminTabPanel.Section<Presenter>> sections;
  
  public DocumentFolderEditViewImpl() {
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
        getPresenter().update((DocumentFolder)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  private void initSections(DocumentFolder folder) {
    if (sections == null) {
      sections = new ArrayList<AdminTabPanel.Section<DocumentFolderEditView.Presenter>>();
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Generale")
          .setView(new DocumentFolderEditGeneralView()));
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Documenti")
          .setView(new DocumentFolderEditChildreenListView()));
      adminTab.setSections(sections);
    }
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof DocumentFolder) {
      DocumentFolder folder = (DocumentFolder)model;
      nameLabel.setText("Editing folder: " + (folder != null ? folder.getName() : "new folder"));
      initSections(folder);
      adminTab.setPresenter(getPresenter());
      adminTab.setModel(model, null);
    }
  }
  
}

package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderEditViewImpl extends AbstractBaseView<ArticleFolderEditView.Presenter> implements ArticleFolderEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<ArticleFolderEditView.Presenter> adminTab;
  
  List<AdminTabPanel.Section<Presenter>> sections;
  
  public ArticleFolderEditViewImpl() {
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
        getPresenter().update((ArticleFolder)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  private void initSections(ArticleFolder folder) {
    if (sections == null) {
      sections = new ArrayList<AdminTabPanel.Section<ArticleFolderEditView.Presenter>>();
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Generale")
          .setView(new ArticleFolderEditGeneralView()));
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Articoli")
          .setView(new ArticleFolderEditItemListView()));
      adminTab.setSections(sections);
    }
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof ArticleFolder) {
      ArticleFolder folder = (ArticleFolder)model;
      nameLabel.setText("Editing folder: " + (folder != null ? folder.getName() : "new folder"));
      initSections(folder);
      adminTab.setPresenter(getPresenter());
      adminTab.setModel(model, null);
    }
  }
  
}

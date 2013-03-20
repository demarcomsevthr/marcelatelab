package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.shared.model.Article;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ArticleEditViewImpl extends AbstractBaseView<ArticleEditView.Presenter> implements ArticleEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<ArticleEditView.Presenter> adminTab;
  
  List<AdminTabPanel.Section<Presenter>> sections;
  
  public ArticleEditViewImpl() {
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
        getPresenter().update((Article)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  private void initSections(Article folder) {
    if (sections == null) {
      sections = new ArrayList<AdminTabPanel.Section<ArticleEditView.Presenter>>();
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Generale")
          .setView(new ArticleEditGeneralView()));
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Testo")
          .setView(new ArticleEditHtmlView()));
      adminTab.setSections(sections);
    }
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Article) {
      Article folder = (Article)model;
      nameLabel.setText("Editing article: " + (folder != null ? folder.getName() : "new article"));
      initSections(folder);
      adminTab.setPresenter(getPresenter());
      adminTab.setModel(model, null);
    }
  }
  
}

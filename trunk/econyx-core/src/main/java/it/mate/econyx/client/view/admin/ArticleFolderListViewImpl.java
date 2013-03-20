package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ArticleFolderListView;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderListViewImpl extends AbstractBaseView<ArticleFolderListView.Presenter> implements ArticleFolderListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<ArticleFolderListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public ArticleFolderListViewImpl() {
    this(null, null);
  }
  
  public ArticleFolderListViewImpl(String width, String height) {
    super();
    this.width = width;
    this.height = height;
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Raccolte")
        .setView(new ArticleFolderListGeneralView()));
    adminTab.setSections(sections);
  }
  
  public void setHeight(String height) {
    this.height = height;
    adminTab.setTabHeight(height);
  }
  
  public void setWidth(String width) {
    this.width = width;
    adminTab.setTabWidth(width);
  }
  
  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height).setEditButtonEnabled(true)
        .setDeleteButtonEnabled(true)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
        /** TO DO 
        new ArticleFolderOptionsDialog(new Delegate<ArticleFolderOptionsDialog.Options>() {
          public void execute(final ArticleFolderOptionsDialog.Options options) {
            GwtUtils.log(getClass(), "onNewModelRequested", options.getArticleFolderType());
            getPresenter().newInstance(options.getArticleFolderType(), new Delegate<ArticleFolder>() {
              public void execute(ArticleFolder page) {
                page.setName(options.getArticleFolderName());
                getPresenter().edit(page);
              }
            });
          }
        });
        **/
      }
      public void onEdit(Object model) {
        if (model instanceof ArticleFolder) {
          getPresenter().edit((ArticleFolder)model);
        }
      }
      public void onDelete(Object model) {
        ArticleFolder ArticleFolder = (ArticleFolder)model;
        getPresenter().delete(ArticleFolder);
      }
    };
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    adminTab.setModel(model, null);
  }
  
}

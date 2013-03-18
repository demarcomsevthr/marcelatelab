package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ArticleListView;
import it.mate.econyx.shared.model.Article;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ArticleListViewImpl extends AbstractBaseView<ArticleListView.Presenter> implements ArticleListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<ArticleListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public ArticleListViewImpl() {
    this(null, null);
  }
  
  public ArticleListViewImpl(String width, String height) {
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
        .setText("Generale")
        .setView(new ArticleListGeneralView()));
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
        new ArticleOptionsDialog(new Delegate<ArticleOptionsDialog.Options>() {
          public void execute(final ArticleOptionsDialog.Options options) {
            GwtUtils.log(getClass(), "onNewModelRequested", options.getArticleType());
            getPresenter().newInstance(options.getArticleType(), new Delegate<Article>() {
              public void execute(Article page) {
                page.setName(options.getArticleName());
                getPresenter().edit(page);
              }
            });
          }
        });
        **/
      }
      public void onEdit(Object model) {
        if (model instanceof Article) {
          getPresenter().edit((Article)model);
        }
      }
      public void onDelete(Object model) {
        Article Article = (Article)model;
        getPresenter().delete(Article);
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

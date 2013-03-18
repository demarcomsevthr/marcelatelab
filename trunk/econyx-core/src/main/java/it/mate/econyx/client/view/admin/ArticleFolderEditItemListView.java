package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.activities.ArticleActivity;
import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.client.view.ArticleListView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

public class ArticleFolderEditItemListView extends AbstractAdminTabPage<ArticleFolderEditView.Presenter> implements ArticleFolderEditView, IsAdminTabPage<ArticleFolderEditView.Presenter> {
  
  private ArticleListView itemListView;
  
  private ArticleFolder articleFolder;
  
  public ArticleFolderEditItemListView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(itemListView.asWidget());
  }
  
  protected void initProvided() {
    
    itemListView = new ArticleListViewImpl("640px", "480px");
    
    itemListView.setPresenter(new ArticleListView.Presenter() {
      public BaseView getView() {
        return null;
      }
      public void goToPrevious() {  }
      public void edit(Article article) {
        GwtUtils.log(getClass(), "edit", "selected article " + article);
//      ArticleFolderEditItemListView.this.getPresenter().edit(article);
      }
      /*
      public void newInstance(String classname, final Delegate<Article> delegate) {  
        if (ArticleFolderEditItemListView.this.getPresenter() instanceof ArticleActivity && articleFolder != null) {
          ArticleActivity articleActivity = (ArticleActivity)ArticleFolderEditItemListView.this.getPresenter();
          articleActivity.newInstance(classname, new Delegate<Article>() {
            public void execute(Article newPage) {
              newPage.setParentId(articleFolder.getId());
              delegate.execute(newPage);
            }
          });
        }
      }
      */
      public void delete(Article article) {
        if (ArticleFolderEditItemListView.this.getPresenter() instanceof ArticleActivity) {
          ArticleActivity articleActivity = (ArticleActivity)ArticleFolderEditItemListView.this.getPresenter();
//        articleActivity.delete(article);
        }
      }
    });
    
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof ArticleFolder) {
      this.articleFolder = (ArticleFolder)model;
      if (articleFolder.getArticles() != null) {
        itemListView.setModel(articleFolder.getArticles(), null);
      }
    }
  }
  
  @Override
  protected void onAttach() {
    super.onAttach();
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int w = ArticleFolderEditItemListView.this.getParent().getOffsetWidth() - 60;
        int h = ArticleFolderEditItemListView.this.getParent().getOffsetHeight() - 60;
        if (w > 0 && h > 0) {
          itemListView.setWidth(w+"px");
          itemListView.setHeight(h+"px");
        }
      }
    });
  }

  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    Article article = (Article)model;
    delegate.execute(article);
  }
  
}

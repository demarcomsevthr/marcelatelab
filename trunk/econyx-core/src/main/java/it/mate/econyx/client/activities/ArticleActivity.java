package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ArticlePlace;
import it.mate.econyx.client.util.PagesUtils;
import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.client.view.ArticleFolderListView;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.client.view.ArticleView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.impl.ArticleFolderTx;
import it.mate.econyx.shared.model.impl.ArticleTx;
import it.mate.econyx.shared.services.ArticleServiceAsync;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ArticleActivity extends BaseActivity implements 
    ArticleFolderView.Presenter,
    ArticleFolderListView.Presenter,
    ArticleFolderEditView.Presenter,
    ArticleView.Presenter,
    ArticleEditView.Presenter {

  private ArticlePlace place;
  
  private ArticleServiceAsync articleService = AppClientFactory.IMPL.getGinjector().getArticleService();
  
  private PortalPageServiceAsync pageService = AppClientFactory.IMPL.getGinjector().getPortalPageService();
  
  private static final String CURRENT_ARTICLE_FOLDER_PAGE_ID = "articleActivity.currentArticleFolderPageId";
  
  public ArticleActivity(ArticlePlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(ArticlePlace.FOLDER_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getArticleFolderView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ArticlePlace.FOLDER_LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getArticleFolderListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ArticlePlace.FOLDER_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getArticleFolderEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ArticlePlace.ARTICLE_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getArticleView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ArticlePlace.ARTICLE_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getArticleEditView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    if (place.getToken().equals(ArticlePlace.FOLDER_VIEW)) {
      if (place.getModel() instanceof ArticleFolderTx) {
        ArticleFolderTx articleFolder = (ArticleFolderTx)place.getModel();
        GwtUtils.setClientAttribute(CURRENT_ARTICLE_FOLDER_PAGE_ID, articleFolder.getContainerPageId());
      }
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(ArticlePlace.FOLDER_LIST)) {
      articleService.findAll(new AsyncCallback<List<ArticleFolder>>() {
        public void onSuccess(List<ArticleFolder> results) {
          getView().setModel(results);
        }
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
      });
    }
    if (place.getToken().equals(ArticlePlace.FOLDER_EDIT)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(ArticlePlace.ARTICLE_VIEW)) {
      boolean rendered = false; 
      if (place.getModel() instanceof Article) {
        Article article = (Article)place.getModel();
        if (article.getComments() == null || article.getComments().size() == 0) {
          rendered = true;
          articleService.findArticleById(article.getId(), true, new AsyncCallback<Article>() {
            public void onFailure(Throwable caught) {
              Window.alert(caught.getMessage());
            }
            public void onSuccess(Article article) {
              getView().setModel(article);
            }
          });
        }
      }
      if (!rendered) {
        getView().setModel(place.getModel());
      }
    }
    if (place.getToken().equals(ArticlePlace.ARTICLE_EDIT)) {
      getView().setModel(place.getModel());
    }
  }
  
  private void registerHandlers(EventBus eventBus) {

  }

  @Override
  public void onDispose() {
    GwtUtils.log("disposing " + this);
    super.onDispose();
  }
  
  @Override
  public void edit(ArticleFolder folder) {
    goTo(new ArticlePlace(ArticlePlace.FOLDER_EDIT, folder));
  }

  @Override
  public void update(ArticleFolder folder) {
    if (folder.getId() == null) {
      articleService.create(folder, new AsyncCallback<ArticleFolder>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(ArticleFolder result) {
          edit(result);
        }
      });
    } else {
      articleService.update(folder, new AsyncCallback<ArticleFolder>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(ArticleFolder result) {
          edit(result);
        }
      });
    }
  }

  @Override
  public void delete(ArticleFolder folder) {
    articleService.delete(folder, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        goTo(new ArticlePlace(ArticlePlace.FOLDER_LIST));
      }
    });
  }

  @Override
  public void addCommentToArticle(String id, ArticleComment comment) {
    articleService.addCommentToArticle(id, comment, new AsyncCallback<Article>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Article article) {
        String articleFolderPageId = (String)GwtUtils.getClientAttribute(CURRENT_ARTICLE_FOLDER_PAGE_ID);
        if (articleFolderPageId != null) {
          PagesUtils.removePageFromCache(articleFolderPageId);
          pageService.removePageFromCache(articleFolderPageId, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) { }
            public void onSuccess(Void result) { }
          });
        }
        getView().setModel(article);
      }
    });
  }
  
  @Override
  public void edit(Article article) {
    goTo(new ArticlePlace(ArticlePlace.ARTICLE_EDIT, article));
  }

  @Override
  public void update(final Article article) {
    if (article.getId() == null) {
      ArticleFolder articleFolder = ((ArticleTx)article).getArticleFolder();
      articleFolder.getArticles().add(article);
      articleService.update(articleFolder, new AsyncCallback<ArticleFolder>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(ArticleFolder articleFolder) {
          edit(article);
        }
      });
    } else {
      articleService.updateArticle(article, new AsyncCallback<Article>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Article article) {
          goTo(new ArticlePlace(ArticlePlace.ARTICLE_EDIT, article));
        }
      });
    }
  }

}

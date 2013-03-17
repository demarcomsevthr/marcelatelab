package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ArticlePlace;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.shared.services.ArticleServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ArticleActivity extends BaseActivity implements 
    ArticleFolderView.Presenter {

  private ArticlePlace place;
  
  private ArticleServiceAsync articleService = AppClientFactory.IMPL.getGinjector().getArticleService();
  
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
    if (place.getToken().equals(ArticlePlace.ARTICLE_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getArticleView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    if (place.getToken().equals(ArticlePlace.FOLDER_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(ArticlePlace.ARTICLE_VIEW)) {
      getView().setModel(place.getModel());
    }
  }
  
  @Override
  public void onDispose() {
    GwtUtils.log("disposing " + this);
    super.onDispose();
  }
  
  private void registerHandlers(EventBus eventBus) {

  }

}

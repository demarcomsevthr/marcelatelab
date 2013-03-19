package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.ArticleComment;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface ArticleView extends BaseView<ArticleView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    public void addCommentToArticle(String id, ArticleComment comment);
    
  }
  
  public class NotImpl extends UnimplementedView<ArticleView.Presenter> implements ArticleView {

  }
  
}

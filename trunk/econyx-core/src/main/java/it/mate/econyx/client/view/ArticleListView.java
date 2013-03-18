package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Article;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ArticleListView extends BaseView<ArticleListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void edit (Article article);
    
    void delete (Article article);
    
  }
  
  public void setHeight(String height);
  
  public void setWidth(String width);

  public class NotImpl extends UnimplementedView<ArticleListView.Presenter> implements ArticleListView {

  }
  
}

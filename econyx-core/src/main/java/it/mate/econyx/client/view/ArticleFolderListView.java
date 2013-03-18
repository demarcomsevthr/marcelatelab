package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ArticleFolderListView extends BaseView<ArticleFolderListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void edit (ArticleFolder page);
    
    void delete (ArticleFolder page);
    
  }
  
  public void setHeight(String height);
  
  public void setWidth(String width);

  public class NotImpl extends UnimplementedView<ArticleFolderListView.Presenter> implements ArticleFolderListView {

  }
  
}

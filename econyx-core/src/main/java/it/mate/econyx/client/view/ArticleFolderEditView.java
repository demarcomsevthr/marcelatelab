package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ArticleFolderEditView extends BaseView<ArticleFolderEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (ArticleFolder folder);
    
    public void edit(ArticleFolder folder);
    
  }
  
  public class NotImpl extends UnimplementedView<ArticleFolderEditView.Presenter> implements ArticleFolderEditView {

  }
  
}

package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Article;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ArticleEditView extends BaseView<ArticleEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (Article folder);
    
    public void edit(Article folder);
    
  }
  
  public class NotImpl extends UnimplementedView<ArticleEditView.Presenter> implements ArticleEditView {

  }
  
}

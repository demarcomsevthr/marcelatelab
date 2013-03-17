package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface ArticleView extends BaseView<ArticleView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<ArticleView.Presenter> implements ArticleView {

  }
  
}

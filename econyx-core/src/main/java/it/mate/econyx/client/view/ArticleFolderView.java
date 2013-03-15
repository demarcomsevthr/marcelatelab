package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface ArticleFolderView extends BaseView<ArticleFolderView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<ArticleFolderView.Presenter> implements ArticleFolderView {

  }
  
}

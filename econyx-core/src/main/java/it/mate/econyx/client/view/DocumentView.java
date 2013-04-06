package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface DocumentView extends BaseView<DocumentView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<DocumentView.Presenter> implements DocumentView {

  }
  
}

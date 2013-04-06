package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface DocumentFolderView extends BaseView<DocumentFolderView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<DocumentFolderView.Presenter> implements DocumentFolderView {

  }
  
}

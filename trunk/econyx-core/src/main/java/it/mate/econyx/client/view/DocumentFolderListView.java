package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface DocumentFolderListView extends BaseView<DocumentFolderListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void edit (DocumentFolder folder);
    
    void delete (DocumentFolder folder);
    
  }
  
  public void setHeight(String height);
  
  public void setWidth(String width);

  public class NotImpl extends UnimplementedView<DocumentFolderListView.Presenter> implements DocumentFolderListView {

  }
  
}

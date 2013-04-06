package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Document;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface DocumentListView extends BaseView<DocumentListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void edit (Document document);
    
    void delete (Document document);
    
  }
  
  public void setHeight(String height);
  
  public void setWidth(String width);

  public class NotImpl extends UnimplementedView<DocumentListView.Presenter> implements DocumentListView {

  }
  
}

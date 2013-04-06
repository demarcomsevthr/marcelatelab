package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Document;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface DocumentEditView extends BaseView<DocumentEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (Document document);
    
    public void edit(Document document);
    
  }
  
  public class NotImpl extends UnimplementedView<DocumentEditView.Presenter> implements DocumentEditView {

  }
  
}

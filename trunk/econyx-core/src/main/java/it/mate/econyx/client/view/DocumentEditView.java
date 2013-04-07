package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Document;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface DocumentEditView extends BaseView<DocumentEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (Document document);
    
    public void edit(Document document);
    
    public void createBlobstoreUploadUrl (String url, Delegate<String> delegate);
    
  }
  
  public class NotImpl extends UnimplementedView<DocumentEditView.Presenter> implements DocumentEditView {

  }
  
}

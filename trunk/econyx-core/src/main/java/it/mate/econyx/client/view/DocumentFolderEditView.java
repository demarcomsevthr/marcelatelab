package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface DocumentFolderEditView extends BaseView<DocumentFolderEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (DocumentFolder folder);
    
    public void edit(DocumentFolder folder);
    
    public void edit(Document document);
    
  }
  
  public class NotImpl extends UnimplementedView<DocumentFolderEditView.Presenter> implements DocumentFolderEditView {

  }
  
}

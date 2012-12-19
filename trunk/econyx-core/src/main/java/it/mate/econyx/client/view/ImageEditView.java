package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Image;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ImageEditView extends BaseView<ImageEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (Image image);
    
    public void refresh (Image image);
    
  }
  
  public class NotImpl extends UnimplementedView<ImageEditView.Presenter> implements ImageEditView {

  }
  
}

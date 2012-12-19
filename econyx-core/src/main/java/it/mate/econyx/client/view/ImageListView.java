package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Image;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ImageListView extends BaseView<ImageListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void edit (Image image);
    
    public void editNew (Image image);
    
    public void delete (Image image);
    
  }
  
  public class NotImpl extends UnimplementedView<ImageListView.Presenter> implements ImageListView {

  }
  
}

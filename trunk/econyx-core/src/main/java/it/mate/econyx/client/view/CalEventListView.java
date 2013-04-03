package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface CalEventListView extends BaseView<CalEventListView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    void edit (CalEvent page);
    
    void delete (CalEvent page);
    
  }
  
  public void setHeight(String height);
  
  public void setWidth(String width);

  public class NotImpl extends UnimplementedView<CalEventListView.Presenter> implements CalEventListView {

  }
  
}

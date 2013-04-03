package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface CalEventEditView extends BaseView<CalEventEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
    public void update (CalEvent event);
    
    public void edit(CalEvent event);
    
  }
  
  public class NotImpl extends UnimplementedView<CalEventEditView.Presenter> implements CalEventEditView {

  }
  
}

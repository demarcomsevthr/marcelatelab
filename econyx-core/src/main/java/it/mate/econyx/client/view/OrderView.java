package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface OrderView extends BaseView<OrderView.Presenter> {

  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<OrderView.Presenter> implements OrderView {

  }
  
  
}

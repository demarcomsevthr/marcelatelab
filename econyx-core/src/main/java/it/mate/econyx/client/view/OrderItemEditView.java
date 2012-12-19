package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface OrderItemEditView extends BaseView<OrderItemEditView.Presenter> {

  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<OrderItemEditView.Presenter> implements OrderItemEditView {

  }
  
}

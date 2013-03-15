package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ProducerListView extends BaseView<ProducerListView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    void edit (Produttore page);
    
    void delete (Produttore page);
    
  }
  
  public class NotImpl extends UnimplementedView<ProducerListView.Presenter> implements ProducerListView {

  }
  

}

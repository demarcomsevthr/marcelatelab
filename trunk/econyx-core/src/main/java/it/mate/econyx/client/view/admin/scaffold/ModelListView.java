package it.mate.econyx.client.view.admin.scaffold;

import it.mate.econyx.client.view.UnimplementedView;
import it.mate.econyx.client.view.admin.scaffold.ModelListView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface ModelListView <M extends HasId> extends BaseView<Presenter<M>> {

  public interface Presenter<M extends HasId> extends BasePresenter {
    
    void edit (M page);
    
    void delete (M page);
    
  }
  
  public class NotImpl <M extends HasId> extends UnimplementedView<ModelListView.Presenter<M>> implements ModelListView<M> {

  }
  
}

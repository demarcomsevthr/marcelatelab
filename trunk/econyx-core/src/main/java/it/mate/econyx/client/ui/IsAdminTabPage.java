package it.mate.econyx.client.ui;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;

public interface IsAdminTabPage <A extends BasePresenter> extends BaseView<A> {
  
  public void updateModel(Object model, Delegate<Object> delegate);
  
  public Object getSelectedObject();
  
}

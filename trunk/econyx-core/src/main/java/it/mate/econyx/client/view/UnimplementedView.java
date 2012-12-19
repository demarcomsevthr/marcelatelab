package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.NotImplementedViewException;

public abstract class UnimplementedView <P extends BasePresenter> extends AbstractBaseView<P> {

  public void setModel(Object model, String tag) {
    throw new NotImplementedViewException();
  }
  
}

package it.mate.econyx.client.ui;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;

public abstract class AbstractAdminTabPage <P extends BasePresenter> extends AbstractBaseView<P> implements IsAdminTabPage<P> {
  
  public Object getSelectedObject() {
    return null;
  }

}

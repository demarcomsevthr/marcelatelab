package it.mate.ckd.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.user.client.ui.Composite;

public abstract class DetailView <P extends BasePresenter> extends Composite {

  private P presenter;
  
  protected P getPresenter() {
    return presenter;
  }

  public void setPresenter(P presenter) {
    this.presenter = presenter;
  }

  public abstract void setModel(Object model, String tag);  
  
}

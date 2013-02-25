package it.mate.ckd.client.factories;

import it.mate.ckd.client.activities.mapper.MainActivityMapper;
import it.mate.ckd.client.view.CKDInputView;
import it.mate.ckd.client.view.CKDOutputView;
import it.mate.ckd.client.view.HomeView;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public HomeView getHomeView();
  
  public CKDInputView getCKDInputView();
  
  public CKDOutputView getCKDOutputView();
  
}

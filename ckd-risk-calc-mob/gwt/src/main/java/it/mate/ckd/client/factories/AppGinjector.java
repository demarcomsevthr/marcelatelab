package it.mate.ckd.client.factories;

import it.mate.ckd.client.activities.mapper.MainActivityMapper;
import it.mate.ckd.client.view.CKDInputView;
import it.mate.ckd.client.view.CKDInputViewWrapper;
import it.mate.ckd.client.view.CKDOutputViewWrapper;
import it.mate.ckd.client.view.ExtendedVerView;
import it.mate.ckd.client.view.HomeView;
import it.mate.ckd.client.view.ProtocolStepView;
import it.mate.ckd.client.view.ReferralDecisionView;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public HomeView getHomeView();
  
  public CKDInputView getCKDInputView();
  public CKDInputViewWrapper getCKDInputViewWrapper();
  
//public CKDOutputView getCKDOutputView();
  public CKDOutputViewWrapper getCKDOutputView();
  
  public ExtendedVerView getExtendedVerView();
  
  public ReferralDecisionView getReferralDecisionView();
  
  public ProtocolStepView getProtocolStepView();
  
}

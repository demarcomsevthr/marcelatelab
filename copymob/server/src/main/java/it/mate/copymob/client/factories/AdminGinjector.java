package it.mate.copymob.client.factories;

import it.mate.copymob.client.activities.AdminActivity;
import it.mate.copymob.client.activities.AdminActivityMapper;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.OrdiniView;
import it.mate.copymob.shared.service.RemoteFacadeAsync;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AdminGinModule.class})
public interface AdminGinjector extends CommonGinjector{

  public RemoteFacadeAsync getRemoteFacade();
  
  public AdminActivityMapper getAdminActivityMapper();
  
  public AdminActivity getMainActivity();
  
  public HomeView getHomeView();
  
  public OrdiniView getOrdiniView();
  
}

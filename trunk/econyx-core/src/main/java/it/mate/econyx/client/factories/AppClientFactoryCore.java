package it.mate.econyx.client.factories;

import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;

public interface AppClientFactoryCore <G extends CoreGinjector> extends BaseClientFactory<G> {

  public static AppClientFactoryCore IMPL = Initializer.create();

  // 11/06/2013
  public static boolean isAdminModule = (IMPL instanceof AdminClientFactory);
  public static boolean isSiteModule = (IMPL instanceof SiteClientFactory);

  /*
  public static boolean isAdminModule = (IMPL instanceof AdminClientFactoryImpl);
  public static boolean isSiteModule = (IMPL instanceof SiteClientFactoryImpl);
  */
  
  class Initializer {
    private static AppClientFactoryCore create() {
      String startupModule = GwtUtils.getJSVar("startupModule", null);
      AppClientFactoryCore clientFactory = null;
      if ("admin".equals(startupModule)) {
        // 11/06/2013
        clientFactory = GWT.create(AdminClientFactory.class);
//      clientFactory = new AdminClientFactoryImpl();
      } else if ("site".equals(startupModule)) {
        // 11/06/2013
        clientFactory = GWT.create(SiteClientFactory.class);
//      clientFactory = new SiteClientFactoryImpl();
      } else {
        Window.alert("Internal error: startupModule property not set!");
      }
      return clientFactory;
    }
  }
  
  public class Customizer {
    
    public static DefaultCustomClientFactory cast() {
      return (DefaultCustomClientFactory)IMPL.internalGetCustomClientFactory();
    }
    
    public static <C extends DefaultCustomClientFactory>  C cast(Class<C> customClientFactoryClass) {
      return (C)IMPL.internalGetCustomClientFactory();
    }
    
  }
  
  // 11/06/2013
  public <G extends CoreGinjector> G castGinjector(Class<G> ginClass);
  /*
  public <G extends AppGinjector> G getConcreteGinjector(Class<G> ginClass);
  */
  
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper);
  
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper, Place defaultPlace);
  
  public PortalSessionState getPortalSessionState();

  public void setPortalSessionState(PortalSessionState portalSessionState);
  
}

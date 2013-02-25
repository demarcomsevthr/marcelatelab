package it.mate.ckd.client.factories;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.user.client.ui.SimplePanel;

public interface AppClientFactory extends BaseClientFactory<AppGinjector> {
  
  public static final AppClientFactory IMPL = Initializer.create();

  class Initializer {
    private static AppClientFactory create() {
      AppClientFactory clientFactory = null;
      /*
      String startupModule = GwtUtils.getJSVar("startupModule", null);
      if ("admin".equals(startupModule)) {
        clientFactory = new AdminClientFactoryImpl();
      } else if ("site".equals(startupModule)) {
        clientFactory = new SiteClientFactoryImpl();
      } else {
        Window.alert("Internal error: startupModule property not set!");
      }
      */
      clientFactory = new AppClientFactoryImpl();
      return clientFactory;
    }
  }
  
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper);
  
  public com.google.web.bindery.event.shared.EventBus getBinderyEventBus();
  
}

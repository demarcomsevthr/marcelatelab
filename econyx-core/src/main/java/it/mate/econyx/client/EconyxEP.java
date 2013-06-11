package it.mate.econyx.client;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.admin.AdminLayoutView;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.econyx.shared.services.PropertiesConstants;
import it.mate.gwtcommons.client.factories.AbstractCustomClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class EconyxEP implements EntryPoint {

  public void onModuleLoad() {
    final GeneralServiceAsync generalService = AppClientFactory.IMPL.getGinjector().getGeneralService();
    generalService.initPortalSession(new AsyncCallback<Map<String,String>>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Map<String, String> properties) {
        AppClientFactory.IMPL.setServerProperties(properties);
        generalService.getCustomClientFactory(new AsyncCallback<AbstractCustomClientFactory>() {
          public void onSuccess(AbstractCustomClientFactory customClientFactory) {
            
            customClientFactory.initEventBus(AppClientFactory.IMPL.getEventBus());
            
            CssResource customCss = customClientFactory.getCustomCss();
            if (customCss != null) {
              customCss.ensureInjected();
            }
            
            GwtUtils.logEnvironment(getClass(), "onModuleLoad");
            GwtUtils.log(getClass(), "onModuleLoad", "developmentMode = " + PropertiesHolder.getBoolean(PropertiesConstants.SYSTEM_ENVIRONMENT_DEVELOPMENT_MODE));
            AppClientFactory.IMPL.setCustomClientFactory(customClientFactory);
            GwtUtils.log(getClass(), "getCustomClientFactory", "customClientFactory = " + AppClientFactory.Customizer.cast() + " - " + AppClientFactory.Customizer.cast().getCustomName());
            if (AppClientFactory.isAdminModule) {
              startupModuleAdmin();
            } else if (AppClientFactory.isSiteModule) { 
              startupModuleSite();
            } else {
              Window.alert("ClientFactory not set (maybe startupModule property is undefined)");
            }
          }
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
        });
      }
    });
  }
  
  private void startupModuleAdmin() {
    AdminLayoutView mainView = new AdminLayoutView();
    RootPanel.get().add(mainView);
    AppClientFactory.IMPL.initModule(mainView.getMvpPanel());
  }
  
  private void startupModuleSite() {
    Panel portalPanel = RootPanel.get("portalPanel");
    if (portalPanel == null) {
      portalPanel = RootPanel.get();
    }
    AppClientFactory.IMPL.initModule(portalPanel);
  }
  
}

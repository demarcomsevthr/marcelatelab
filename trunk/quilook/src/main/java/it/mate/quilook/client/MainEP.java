package it.mate.quilook.client;

import it.mate.econyx.client.view.admin.AdminLayoutView;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.econyx.shared.services.PropertiesConstants;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.quilook.client.factories.AppClientFactory;

import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class MainEP implements EntryPoint {

  public void onModuleLoad() {
    
    GeneralServiceAsync generalService = AppClientFactory.IMPL.getGinjector().getGeneralService();
    generalService.initPortalSession(new AsyncCallback<Map<String,String>>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Map<String, String> properties) {
        AppClientFactory.IMPL.setServerProperties(properties);
        GwtUtils.logEnvironment(getClass(), "onModuleLoad");
        GwtUtils.log(getClass(), "onModuleLoad", "developmentMode = " + PropertiesHolder.getBoolean(PropertiesConstants.SYSTEM_ENVIRONMENT_DEVELOPMENT_MODE));
        if (AppClientFactory.isAdminModule) {
          startupModuleAdmin();
        } else if (AppClientFactory.isSiteModule) { 
          startupModuleSite();
        } else {
          Window.alert("ClientFactory not set (maybe startupModule property is undefined)");
        }
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
    GwtUtils.log("here");
    AppClientFactory.IMPL.initModule(portalPanel);
  }
  
}

package it.mate.quilook.client.factories;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.places.AppPlaceHistoryMapper;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.util.PropertyConstants;
import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import javax.annotation.PreDestroy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class AdminClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> implements 
        AppClientFactory, PortalSessionStateChangeEvent.Handler {

  private static PlaceHistoryMapper placeHistoryMapper;
  
  private PortalSessionState portalSessionState = new PortalSessionState(PortalSessionState.MODULE_NOT_SET);
  
  private HandlerRegistration portalSessionStateChangeRegistration;
  
  @Override
  public SiteGinjector getGwtpGinjector() {
    return null;
  }

  @Override
  protected AppGinjector createGinjector() {
    return GWT.create(AdminGinjector.class);
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public <G extends AppGinjector> G getConcreteGinjector(Class<G> ginClass) {
    return (G)AppClientFactory.IMPL.getGinjector();
  }
  
  @Override
  public void initModule(Panel portalPanel) {
    PortalSessionState.activateStateMonitor(getEventBus());
    portalSessionStateChangeRegistration = getEventBus().addHandler(PortalSessionStateChangeEvent.TYPE, this);
    getPortalSessionState().setModuleType(PortalSessionState.MODULE_ADMIN);
  }
  
  @Override
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper) {
    super.initMvp(panel, getPlaceHistoryMapper(), activityMapper);
  }

  @Override
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper, Place defaultPlace) {
    super.initMvp(panel, getPlaceHistoryMapper(), activityMapper, defaultPlace);
  }

  @Override
  public void onPortalSessionStateChange(PortalSessionStateChangeEvent event) {
    
    GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "############################");
    GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "changed portal session state");
    GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "event = " + event);

    if (PropertiesHolder.getBoolean(PropertyConstants.STORE_PORTAL_STATE_IN_HTTP_SESSION)) {
      getGinjector().getGeneralService().storePortalSessionState(event.getState(), new AsyncCallback<Void>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Void result) {
          
          if (getPortalSessionState().getLoggedUser() == null) {
            getGinjector().getGeneralService().retrievePortalSessionState(PortalSessionState.MODULE_ADMIN, new AsyncCallback<PortalSessionState>() {
              public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
              }
              public void onSuccess(PortalSessionState portalSessionState) {
                // 28/11/2012
                if (portalSessionState.getLoggedUser() != null) {
                  setPortalSessionState(portalSessionState);
                  getEventBus().fireEvent(new PortalSessionStateChangeEvent(portalSessionState));
                }
              }
            });
          }
          
        }
      });
    }
    
  }

  @Override
  public PlaceHistoryMapper getPlaceHistoryMapper() {
    if (placeHistoryMapper == null)
      placeHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);
    return placeHistoryMapper;
  }
  
  @Override
  public PortalSessionState getPortalSessionState() {
    return portalSessionState;
  }

  @Override
  public void setPortalSessionState(PortalSessionState portalSessionState) {
    this.portalSessionState = portalSessionState;
  }
  
  @PreDestroy
  public void onDestroy() {
    portalSessionStateChangeRegistration.removeHandler();
  }
  
}

package it.mate.quilook.client.factories;

import it.mate.econyx.client.places.AppPlaceHistoryMapper;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class SiteClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> 
    implements AppClientFactory {
  
  private static PlaceHistoryMapper placeHistoryMapper;
  
  private final SiteGinjector siteGinjector = GWT.create(SiteGinjector.class);
  
  public SiteGinjector getGwtpGinjector() {
    return siteGinjector;
  }
  
  @Override
  protected AppGinjector createGinjector() {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <G extends AppGinjector> G getConcreteGinjector(Class<G> ginClass) {
    return ((G)AppClientFactory.IMPL.getGinjector());
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
  public PlaceHistoryMapper getPlaceHistoryMapper() {
    if (placeHistoryMapper == null)
      placeHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);
    return placeHistoryMapper;
  }
  
  @Override
  public void initModule(Panel portalPanel) {
    
    GwtUtils.log("here");
    
    DelayedBindRegistry.bind(siteGinjector);
    
    siteGinjector.getPlaceManager().revealCurrentPlace();
    
  }

  @Override
  public PortalSessionState getPortalSessionState() {
    return null;
  }

  @Override
  public void setPortalSessionState(PortalSessionState portalSessionState) {
    
  }
  
}

package it.mate.postscriptum.client.factories;

import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.service.StickFacade2Async;

import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;


public class AppClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> implements AppClientFactory {

  @Override
  public PlaceHistoryMapper getPlaceHistoryMapper() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public EventBus getBinderyEventBus() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PhoneGap getPhoneGap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getWrapperPct() {
    // TODO Auto-generated method stub
    return -1;
  }

  @Override
  public void adaptWrapperPanel(Panel wrapperPanel, String id, boolean adaptVerMargin, int headerPanelHeight, Delegate<Element> delegate) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getTabletWrapperHeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getTabletWrapperWidth() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getNativeProperty(String name, String defValue) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean getNativeProperty(String name, boolean defValue) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public StickFacade2Async getStickFacade2() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setRemoteUserDelegate(Delegate<RemoteUser> remoteUserDelegate) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void authenticate() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected AppGinjector createGinjector() {
    // TODO Auto-generated method stub
    return null;
  }

  
}

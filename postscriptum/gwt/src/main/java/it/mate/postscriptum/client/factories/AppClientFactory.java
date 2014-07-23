package it.mate.postscriptum.client.factories;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.service.StickFacade2Async;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwtphonegap.client.PhoneGap;

@SuppressWarnings("rawtypes")
public interface AppClientFactory extends BaseClientFactory<AppGinjector> {
  
  public static final AppClientFactory IMPL = Initializer.create();

  class Initializer {
    private static AppClientFactory create() {
      AppClientFactory clientFactory = new AppClientFactoryImpl();
      return clientFactory;
    }
  }
  
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper);
  
  public com.google.web.bindery.event.shared.EventBus getBinderyEventBus();
  
  public PhoneGap getPhoneGap();
  
  public int getWrapperPct();
  
  public void adaptWrapperPanel(Panel wrapperPanel, String id, boolean adaptVerMargin, int headerPanelHeight, Delegate<Element> delegate);

  public int getTabletWrapperHeight();
  
  public int getTabletWrapperWidth();
  
  public void initTitle(BaseMgwtView view);
  
  public String getNativeProperty(String name, String defValue);
  
  public boolean getNativeProperty(String name, boolean defValue);
  
  public StickFacade2Async getStickFacade2();

  /*
  public void initEndpointProxy(Delegate<StickMailEPProxy> delegate, Delegate<Boolean> authDelegate);
  
  public StickMailEPProxy getStickMailEPProxy();
  
  public void getRemoteUser(final Delegate<RemoteUser> delegate);
  */
  
  public void setRemoteUserDelegate(Delegate<RemoteUser> remoteUserDelegate);
  
  public void authenticate();
  
}

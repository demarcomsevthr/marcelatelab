package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.mapper.PortalPageMenuMapper;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.PortletFactory;
import it.mate.portlets.client.ui.mvp.BaseMvpPortlet;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageMenuPortlet extends BaseMvpPortlet {
  
  private static Widget headerMenuView = null;

  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }

  @Override
  protected void initMvp(SimplePanel headerMenuPanel) {
    if (PortalPageMenuPortlet.headerMenuView != null) {
      GwtUtils.log(getClass(), "initMvp", "headerMenuPanel.view = " + PortalPageMenuPortlet.headerMenuView);
      headerMenuPanel.setWidget(PortalPageMenuPortlet.headerMenuView);
    } else {
      GwtUtils.log(getClass(), "initMvp", "headerMenuPanel.widget = " + headerMenuPanel.getWidget());
      AppClientFactory.IMPL.initMvp(headerMenuPanel, new PortalPageMenuMapper(AppClientFactory.IMPL));
      GwtUtils.log(getClass(), "initMvp", "headerMenuPanel.widget = " + headerMenuPanel.getWidget());
      PortalPageMenuPortlet.headerMenuView = headerMenuPanel.getWidget();
    }
  }

  public static class Factory extends PortletFactory<PortalPageMenuPortlet> {

    @Override
    public PortalPageMenuPortlet createWidget() {
      return new PortalPageMenuPortlet();
    }
    
  }
  
}

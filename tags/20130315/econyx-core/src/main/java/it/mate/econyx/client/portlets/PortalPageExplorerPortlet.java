package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.mapper.PortalPageExplorerMapper;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.PortletFactory;
import it.mate.portlets.client.ui.mvp.BaseMvpPortlet;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageExplorerPortlet extends BaseMvpPortlet {

  private static Widget explorerView = null;
  
  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }

  @Override
  protected void initMvp(SimplePanel explorerPanel) {
    if (PortalPageExplorerPortlet.explorerView != null) {
      GwtUtils.log(getClass(), "initMvp", "explorer.view = " + PortalPageExplorerPortlet.explorerView);
      explorerPanel.setWidget(PortalPageExplorerPortlet.explorerView);
    } else {
      GwtUtils.log(getClass(), "initMvp", "explorerPanel.widget = " + explorerPanel.getWidget());
      AppClientFactory.IMPL.initMvp(explorerPanel, new PortalPageExplorerMapper(AppClientFactory.IMPL));
      GwtUtils.log(getClass(), "initMvp", "explorerPanel.widget = " + explorerPanel.getWidget());
      PortalPageExplorerPortlet.explorerView = explorerPanel.getWidget();
    }
  }

  public static class Factory extends PortletFactory<PortalPageExplorerPortlet> {

    @Override
    public PortalPageExplorerPortlet createWidget() {
      return new PortalPageExplorerPortlet();
    }
    
  }
  
}

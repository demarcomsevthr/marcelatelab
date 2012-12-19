package it.mate.econyx.client.portlets;

import it.mate.econyx.client.activities.mapper.TestActivityMapper;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.PortletFactory;
import it.mate.portlets.client.ui.mvp.BaseMvpPortlet;

import com.google.gwt.user.client.ui.SimplePanel;

public class TestPortlet extends BaseMvpPortlet {

  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }

  @Override
  protected void initMvp(SimplePanel mvpPanel) {
    AppClientFactory.IMPL.initMvp(mvpPanel, new TestActivityMapper(AppClientFactory.IMPL));
  }

  public static class Factory extends PortletFactory<TestPortlet> {

    @Override
    public TestPortlet createWidget() {
      return new TestPortlet();
    }
    
  }
  
}

package it.mate.quilook.client.portlets;

import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.Portlet;
import it.mate.portlets.client.ui.PortletFactory;
import it.mate.quilook.client.view.site.QuView;

@SuppressWarnings("rawtypes")
public class QuPortlet extends Portlet {

  @Override
  public WidgetFactory createWidgetFactory() {
    return null;
  }
  
  private QuPortlet() {
    initui();
  }

  private void initui() {
    initWidget(new QuView());
  }

  @SuppressWarnings("serial")
  public static class Factory extends PortletFactory<QuPortlet> {
    public QuPortlet createWidget() {
      return new QuPortlet();
    }
  }
  
}

package it.mate.econyx.client.factories;

import it.mate.econyx.client.view.site.PortalPageMenuViewCustomer;

@SuppressWarnings("serial")
public class CustomClientFactory extends DefaultCustomClientFactory {

  @Override
  public String getCustomName() {
    return "nxpearl";
  }

  @Override
  public PortalPageMenuViewCustomer getPortalPageMenuViewCustomizer() {
    return new PortalPageMenuViewCustomer();
  }

}

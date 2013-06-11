package it.mate.quilook.client.factories;

import it.mate.econyx.client.factories.DefaultCustomClientFactory;

@SuppressWarnings("serial")
public class AppCustomClientFactory extends DefaultCustomClientFactory {

  public String getCustomName() {
    return "quilook";
  }
  
}

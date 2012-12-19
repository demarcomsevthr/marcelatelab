package it.mate.portlets.shared.model;

import it.mate.portlets.client.WidgetFactory;

import java.io.Serializable;

@SuppressWarnings({"serial", "rawtypes"})
public class PageTemplate implements Serializable {

  private String name;
  
  private WidgetFactory widgetFactory;
  
  @Override
  public String toString() {
    return "PageTemplate [name=" + name + ", widgetFactory=" + widgetFactory + "]";
  }

  public WidgetFactory getWidgetFactory() {
    return widgetFactory;
  }

  public void setWidgetFactory(WidgetFactory widgetFactory) {
    this.widgetFactory = widgetFactory;
  }

  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
}

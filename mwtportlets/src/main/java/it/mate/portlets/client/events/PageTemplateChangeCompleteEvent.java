package it.mate.portlets.client.events;

import it.mate.portlets.client.WidgetFactory;

@SuppressWarnings("rawtypes")
public class PageTemplateChangeCompleteEvent implements PageTemplateEvent {

  private String name;
  
  private WidgetFactory widgetFactory;
  
  public PageTemplateChangeCompleteEvent(String name, WidgetFactory widgetFactory) {
    super();
    this.name = name;
    this.widgetFactory = widgetFactory;
  }

  public String getName() {
    return name;
  }

  public WidgetFactory getWidgetFactory() {
    return widgetFactory;
  }
  
}

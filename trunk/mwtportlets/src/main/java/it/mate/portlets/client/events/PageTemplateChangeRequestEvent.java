package it.mate.portlets.client.events;


@SuppressWarnings("rawtypes")
public class PageTemplateChangeRequestEvent implements PageTemplateEvent {

  private String name;
  
  public PageTemplateChangeRequestEvent(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

}

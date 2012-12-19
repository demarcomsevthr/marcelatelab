package it.mate.portlets.client.ui;

import it.mate.portlets.client.AbstractWidgetFactory;

import com.google.gwt.user.client.ui.HTML;

public class HtmlPortlet extends HTML {

  public static class Factory extends AbstractWidgetFactory<HtmlPortlet> {
    
    private String html;
    
    public void setHtml(String html) {
      this.html = html.replace("$$", "<br>");
    }

    @Override
    public HtmlPortlet createWidget() {
      return new HtmlPortlet();
    }
    
    @Override
    public void refresh(HtmlPortlet widget) {
      super.refresh(widget);
      widget.setHTML(html);
    }
    
  }
  
}

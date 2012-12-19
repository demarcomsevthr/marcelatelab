package it.mate.portlets.client.ui;

import it.mate.portlets.client.AbstractWidgetFactory;

import com.google.gwt.user.client.ui.Label;

public class LabelPortlet extends Label {
  
  public LabelPortlet(String text) {
    super(text);
  }

  public static class Factory extends AbstractWidgetFactory<LabelPortlet> {
    
    private String text;
    
    public void setText(String text) {
      this.text = text;
    }
    
    @Override
    public LabelPortlet createWidget() {
      return new LabelPortlet(text);
    }
    
  }
  
}

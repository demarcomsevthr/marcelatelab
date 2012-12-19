package it.mate.portlets.client;

import it.mate.portlets.shared.util.StringUtils;

import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("serial")
public abstract class AbstractWidgetFactory<W extends Widget> implements WidgetFactory<W> {

  protected String stylename;
  
  public void setStylename(String styleName) {
    this.stylename = styleName;
  }

  public AbstractWidgetFactory() {
    super();
  }

  public AbstractWidgetFactory(Widget w) {
    this.stylename = w.getStyleName();
  }

  public void refresh(W widget) {
    if (StringUtils.isSet(stylename)) {
//    widget.setStyleName(stylename);
      widget.addStyleName(stylename);
    }
  }
  
}

package it.mate.portlets.client.ui;

import it.mate.gwtcommons.client.ui.ContainerPanel;
import it.mate.portlets.client.layout.Container;
import it.mate.portlets.client.layout.ContainerFactory;
import it.mate.portlets.client.layout.HasLayout;
import it.mate.portlets.client.layout.Layout;
import it.mate.portlets.client.layout.LayoutConstraint;
import it.mate.portlets.shared.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;

public class BoxContainerPortlet extends ContainerPanel implements Container, HasLayout {

  private List<Widget> widgets = new ArrayList<Widget>();
  
  private Layout layout;
  
  private String title;

  public BoxContainerPortlet() {
    setStyleName("mwt-BoxContainer");
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  @Override
  public void add(Widget widget, LayoutConstraint constraint) {
    this.widgets.add(widget);
  }
  
  @Override
  public void setLayout(Layout layout) {
    this.layout = layout;
  }

  @Override
  public void doLayout() {
    
    Anchor title = new Anchor(SafeHtmlUtils.fromTrustedString("<span>"+this.title+"</span>"));
    add(title, "mwt-BoxContainerTitle");
    if (layout != null) {
      if (StringUtils.isSet(layout.getWidth())) {
        this.setWidth(layout.getWidth());
        title.setWidth(layout.getWidth());
      }
    }
    for (Widget widget : widgets) {
      add(widget, "mwt-BoxContainerBody");
    }
  }

  @SuppressWarnings("serial")
  public static class Factory extends ContainerFactory<BoxContainerPortlet> {

    private String title = "Titolo";
    
    @Override
    public BoxContainerPortlet createWidget() {
      BoxContainerPortlet result = new BoxContainerPortlet();
      result.setTitle(title);
      return result;
    }
    
    public void setTitle(String title) {
      this.title = title;
    }
    
  }
  
}

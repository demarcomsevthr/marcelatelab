package it.mate.portlets.client.layout;

import it.mate.portlets.client.AbstractWidgetFactory;
import it.mate.portlets.client.WidgetFactory;

import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings({"serial", "rawtypes"})
public abstract class ContainerFactory<W extends Widget> extends AbstractWidgetFactory<W> {

  public WidgetFactory[] widgets;
  public LayoutConstraint[] constraints;
  public Layout layout;
  
  public void setWidgets(WidgetFactory[] widgets) {
    this.widgets = widgets;
  }
  public void setConstraints(LayoutConstraint[] constraints) {
    this.constraints = constraints;
  }
  public void setLayout(Layout layout) {
    this.layout = layout;
  }

  public void refresh(W widget) {
    super.refresh(widget);
    Container container = (Container) widget;
    container.clear();
    if (widgets != null) {
      for (int it = 0; it < widgets.length; it++) {
        WidgetFactory wf = widgets[it];
        if (wf != null) {
          container.add(LayoutUtil.createWidget(wf), constraints == null ? null : constraints[it]);
        }
      }
    }
    if (widget instanceof HasLayout) {
      HasLayout hasLayout = (HasLayout)widget;
      if (layout != null) {
        hasLayout.setLayout(layout);
      }
      hasLayout.doLayout();
    }
  };
  
}

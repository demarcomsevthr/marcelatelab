package it.mate.portlets.client.ui;

import it.mate.gwtcommons.client.ui.DivGridPanel;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.layout.ContainerFactory;
import it.mate.portlets.client.layout.HasLayout;
import it.mate.portlets.client.layout.Layout;
import it.mate.portlets.client.layout.LayoutConstraint;
import it.mate.portlets.client.util.StyleUtil;
import it.mate.portlets.shared.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BoxLayoutPanel extends SimplePanel implements HasLayout {

  private DivGridPanel layoutPanel;
  
  private List<ConstraintWidget> cws = new ArrayList<BoxLayoutPanel.ConstraintWidget>();
  
  private Layout layout;
  
  @Override
  public void add(Widget widget, LayoutConstraint constraint) {
    cws.add(new ConstraintWidget(widget, constraint));
  }

  @Override
  public void setLayout(Layout layout) {
    this.layout = layout;
  }
  
  @Override
  public void doLayout() {
    layoutPanel = new DivGridPanel();

    if (StringUtils.isSet(layout.getWidth())) {
      setWidth(layout.getWidth());
    } else {
      setWidth("100%");
    }
    
    if (StringUtils.isSet(layout.getHeight()))
      setHeight(layout.getHeight());

    if (StringUtils.isSet(layout.getStylename())) {
      setStyleName(layout.getStylename());
    } else {
      setStyleName("mwt-BoxLayoutPanel");
    }
    
    if (StringUtils.isSet(layout.getStyle())) {
      StyleUtil.applyStyle(this, layout.getStyle());
    }

    if (layout.isVertical()) {
      layoutPanel.setStyleName("mwt-BoxLayoutPanelVertical");
    } else {
      layoutPanel.setStyleName("mwt-BoxLayoutPanelHorizontal");
    }
    layoutPanel.setWidth("100%");
    layoutPanel.setHeight("100%");
    
    
    for (ConstraintWidget cw : cws) {

      /*
      Widget portletWidget = new SimplePanel(cw.widget);
      portletWidget.setWidth("100%");
      portletWidget.setStyleName("mwt-portletBoundary");
      */
      
      Widget constrainedWidget = cw.widget;
      
//    layoutPanel.add(cw.widget);
      layoutPanel.add(constrainedWidget);
      
      Element cellElem = constrainedWidget.getElement().getParentElement().cast();
      cellElem.addClassName("mwt-BoxLayoutPanelCell");
      if (layout.isVertical()) {
        cellElem.addClassName("mwt-BoxLayoutPanelVerticalCell");
      } else {
        cellElem.addClassName("mwt-BoxLayoutPanelHorizontalCell");
      }
      
    }
    for (int it = 0; it < cws.size(); it++) {
      ConstraintWidget cw = cws.get(it);
      Widget constrainedWidget = cw.widget;
      if (cw.constraint != null && cw.constraint.getSize() != null) {
        if (layout.isVertical()) {
//        Element childElem = cw.widget.getElement().getFirstChildElement().cast();
//        GwtUtils.setStyleAttribute(childElem, "height", cw.constraint.getSize());
//        cw.widget.setHeight(cw.constraint.getSize());
//        cw.widget.getParent().setHeight(cw.constraint.getSize());
          Element cellElem = constrainedWidget.getElement().getParentElement().cast();
          GwtUtils.setStyleAttribute(cellElem, "height", cw.constraint.getSize());
        } else {
//        cw.widget.setWidth(cw.constraint.getSize());
//        cw.widget.getParent().setWidth(cw.constraint.getSize());
          Element cellElem = constrainedWidget.getElement().getParentElement().cast();
          GwtUtils.setStyleAttribute(cellElem, "width", cw.constraint.getSize());
        }
        GwtUtils.setStyleAttribute(constrainedWidget, "overflow", cw.constraint.getOverflow());
      }
    }
    super.clear();
    super.setWidget(layoutPanel);
  }
  
  private class ConstraintWidget {
    Widget widget;
    LayoutConstraint constraint;
    private ConstraintWidget(Widget widget, LayoutConstraint constraint) {
      super();
      Widget portletBoundaryWidget = new SimplePanel(widget);
//    portletBoundaryWidget.setWidth("100%");
      portletBoundaryWidget.setStyleName("mwt-portletBoundary");
//    this.widget = widget;
      this.widget = portletBoundaryWidget;
      this.constraint = constraint;
    }
  }


  @SuppressWarnings("serial")
  public static class Factory extends ContainerFactory<BoxLayoutPanel> {

    @Override
    public BoxLayoutPanel createWidget() {
      return new BoxLayoutPanel();
    }
    
  }
  
}

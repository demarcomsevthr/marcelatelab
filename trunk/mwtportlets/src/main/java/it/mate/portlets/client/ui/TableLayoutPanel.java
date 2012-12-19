package it.mate.portlets.client.ui;

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
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TableLayoutPanel extends SimplePanel implements HasLayout {

  private CellPanel layoutPanel;
  
  private List<ConstraintWidget> cws = new ArrayList<TableLayoutPanel.ConstraintWidget>();
  
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
    if (layout.isVertical()) {
      layoutPanel = new VerticalPanel();
    } else {
      layoutPanel = new HorizontalPanel();
    }

    if (StringUtils.isSet(layout.getWidth())) {
      layoutPanel.setWidth(layout.getWidth());
    } else {
      layoutPanel.setWidth("100%");
    }
    
    if (StringUtils.isSet(layout.getHeight()))
      layoutPanel.setHeight(layout.getHeight());

    layoutPanel.setStyleName("mwt-TableLayoutPanel");
    
    if (StringUtils.isSet(layout.getStylename())) {
      layoutPanel.addStyleName(layout.getStylename());
    }
    
    if (StringUtils.isSet(layout.getStyle())) {
      StyleUtil.applyStyle(layoutPanel, layout.getStyle());
    }
    
    for (ConstraintWidget cw : cws) {
      
      SimplePanel divPanel = new SimplePanel(cw.widget);
      divPanel.setWidth("100%");
      
      layoutPanel.add(divPanel);
      
      Element cellElem = divPanel.getElement();
      cellElem.setClassName("mwt-TableLayoutPanelCell");
      
    }
    for (int it = 0; it < cws.size(); it++) {
      ConstraintWidget cw = cws.get(it);
      if (cw.constraint != null && cw.constraint.getSize() != null) {
        if (layout.isVertical()) {
          layoutPanel.setCellHeight(layoutPanel.getWidget(it), cw.constraint.getSize());
        } else {
          layoutPanel.setCellWidth(layoutPanel.getWidget(it), cw.constraint.getSize());
        }
        GwtUtils.setStyleAttribute(cw.widget, "overflow", cw.constraint.getOverflow());
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
      this.widget = widget;
      this.constraint = constraint;
    }
  }


  @SuppressWarnings("serial")
  public static class Factory extends ContainerFactory<TableLayoutPanel> {

    @Override
    public TableLayoutPanel createWidget() {
      return new TableLayoutPanel();
    }
    
  }
  
}

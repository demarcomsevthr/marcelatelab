package it.mate.econyx.client.ui;

import it.mate.gwtcommons.client.utils.ResizeUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import com.google.gwt.user.client.ui.Widget;

public class AdminClientUtils {
  
  public static String defaultWidth() {
    return PropertiesHolder.getString("client.AdminLayoutView.adminTab.defaultWidth", "980px");
  }
  
  public static String defaultHeight() {
    return PropertiesHolder.getString("client.AdminLayoutView.adminTab.defaultHeight", "720px");
  }

  public static Integer adminTabPanelRelativeHeight() {
    return PropertiesHolder.getInt("client.AdminLayoutView.adminTab.relativeHeight", 73);
  }

  public static Integer adminTabPanelBottomHeight() {
    return PropertiesHolder.getInt("client.AdminLayoutView.adminTab.bottomHeight", 150);
  }
  
  public static void applyDefaultResizePolicy(Widget widget) {
//  ResizeUtils.setHeightRelativeToScreen(widget, AdminClientUtils.adminTabPanelRelativeHeight());
    ResizeUtils.setHeightWithFixedBottom(widget, AdminClientUtils.adminTabPanelBottomHeight());
  }

}

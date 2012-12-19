package it.mate.portlets.client.layout;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.WidgetFactory;
import it.mate.portlets.client.ui.BadWidgetPlaceholder;

import com.google.gwt.user.client.ui.Widget;

public class LayoutUtil {

  public static Widget createWidget(WidgetFactory wf) {
    try {
      /*
      WidgetRefreshHook rh = WidgetRefreshHook.App.get();
      if (rh != null && rh instanceof HasWidgetFactoryEnabled) {
        String s = ((HasWidgetFactoryEnabled) rh).getWidgetFactoryDisabledMessage(wf);
        if (s != null) {
          return new BadWidgetPlaceholder(wf, s);
        }
      }
      */
      Widget w = wf.createWidget();
      wf.refresh(w);
      return w;
    } catch (Exception ex) {
      GwtUtils.log(LayoutUtil.class, "createWidget", "error", ex);
      return new BadWidgetPlaceholder(wf, ex);
    }
  }

}

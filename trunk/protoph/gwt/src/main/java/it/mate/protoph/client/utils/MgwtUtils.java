package it.mate.protoph.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class MgwtUtils {

  
  public static void prepareInnerScrollPanel(final ScrollPanel scrollPanel, final Delegate<Boolean> scrollDelegate) {
    scrollPanel.setScrollingEnabledY(true);
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        int resultsHeight = Window.getClientHeight() - scrollPanel.getAbsoluteTop();
        scrollPanel.getElement().getStyle().setHeight(resultsHeight, Unit.PX);
      }
    });
    if (scrollDelegate != null) {
      scrollPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {
        public void onScrollMove(ScrollMoveEvent event) {
          scrollDelegate.execute(true);
        }
      });
      scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {
        public void onScrollEnd(ScrollEndEvent event) {
          scrollDelegate.execute(false);
        }
      });
    }
  }
  
}

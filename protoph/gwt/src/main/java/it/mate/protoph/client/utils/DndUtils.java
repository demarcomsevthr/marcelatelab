package it.mate.protoph.client.utils;

import com.google.gwt.dom.client.Element;

public class DndUtils {
  
  public static void doTest(Element dropable, Element dragable) {
    jsMakeDropableImpl(dropable);
    jsMakeDraggableImpl(dragable);
    jsTouchPunchImpl(dropable);
    jsTouchPunchImpl(dragable);
  }
  
  /*
   * SEE http://www.w3schools.com/html/html5_draganddrop.asp
   */
  
  private static native void jsMakeDraggableImpl(Element element) /*-{
    var dragHandler = $entry(function(ev) {
      ev.dataTransfer.setData("text", ev.target.id);
    });
    element.setAttribute('draggable', 'true');
    element.addEventListener('dragstart', dragHandler, false);
  }-*/;

  private static native void jsMakeDropableImpl(Element element) /*-{
    var allowDropHandler = $entry(function(ev) {
      ev.preventDefault();
    });
    var dropHandler = $entry(function(ev) {
      ev.preventDefault();
      var data = ev.dataTransfer.getData("text");
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("data = " + data);
      ev.target.appendChild($doc.getElementById(data));
    });
    element.addEventListener('drop', dropHandler, false);
    element.addEventListener('dragover', allowDropHandler, false);
  }-*/;
  

  /**
   * SEE http://stackoverflow.com/questions/7531412/drag-and-drop-in-phonegap-android
   */
  private static native void jsTouchPunchImpl(Element element) /*-{
    
    var touchHandler = $entry(function(event) {
      var touches = event.changedTouches;
      var first = touches[0];
      var type = "";
      switch(event.type) {
        case "touchstart": type = "mousedown"; break;
        case "touchmove":  type = "mousemove"; break;        
        case "touchend":   type = "mouseup"; break;
        default: return;
      }
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("punch.1");
      var simulatedEvent = $doc.createEvent("MouseEvent");
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("punch.2");
      simulatedEvent.initMouseEvent(type, true, true, $wnd, 1,
                          first.screenX, first.screenY,
                          first.clientX, first.clientY, false,
                          false, false, false, 0, null);
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("punch.3");
      first.target.dispatchEvent(simulatedEvent); 
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)("punch.4");
      event.preventDefault();
    });
    
    element.addEventListener("touchstart", touchHandler, true);
    element.addEventListener("touchmove", touchHandler, true);
    element.addEventListener("touchend", touchHandler, true);
    element.addEventListener("touchcancel", touchHandler, true);   

  }-*/;
  

}

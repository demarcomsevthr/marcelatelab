package it.mate.protons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class DndUtils {
  
  public static void doTest1(Element dropable, Element dragable) {
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
  
  
  public static void doTest2(Element dragable) {
    JavaScriptObject offset = getTouchOffsetImpl(dragable.getParentElement(), 0);
    jsMakeDraggable2Impl(dragable, offset);
  }
  
  /*
   * SEE http://mobiforge.com/design-development/touch-friendly-drag-and-drop
   */
  
  private static native void jsMakeDraggable2Impl(Element draggable, JavaScriptObject offset) /*-{
    
    var touchMoveHandler = $entry(function(event) {
      var touch = event.targetTouches[0];
      draggable.style.left = touch.pageX - offset.left + 'px';
      draggable.style.top = touch.pageY - offset.top + 'px';
      event.preventDefault();
    });
    
    draggable.addEventListener('touchmove', touchMoveHandler, false);
    
    var mouseMoveHandler = $entry(function(event) {
      draggable.style.left = event.pageX - offset.left + 'px';
      draggable.style.top = event.pageY - offset.top + 'px';
    });
    
    var mouseDownHandler = $entry(function(event) {
      draggable.addEventListener("mousemove", mouseMoveHandler, false);
    });
    
    draggable.addEventListener('mousedown', mouseDownHandler, false);
    
    var mouseUpHandler = $entry(function(event) {
      event.preventDefault();
      draggable.removeEventListener("mousemove", mouseMoveHandler, false); 
    });
    
    draggable.addEventListener('mouseup', mouseUpHandler, false);
    
  }-*/;

  private static native JavaScriptObject getTouchOffsetImpl(Element obj, double factor) /*-{
    var offsetLeft = 0;
    var offsetTop = 0;
    var offsetWidth = obj.offsetWidth;
    var offsetHeight = obj.offsetHeight;
    do {
      if (!isNaN(obj.offsetLeft)) {
          offsetLeft += obj.offsetLeft;
      }
      if (!isNaN(obj.offsetTop)) {
          offsetTop += obj.offsetTop;
      }   
    } while(obj = obj.offsetParent );
    return {left: (offsetLeft + offsetWidth * factor), top: (offsetTop + offsetHeight * factor)};
  }-*/;

  
  public static void doTest3(Element dragable, Element dropable) {
    jsMakeDraggableAndDropable3Impl(dragable, dropable);
  }
  
  private static native void jsMakeDraggableAndDropable3Impl(Element dragable, Element dropable) /*-{
    
    var dragableOffset = @it.mate.protons.client.utils.DndUtils::getTouchOffsetImpl(Lcom/google/gwt/dom/client/Element;D)(dragable, 0.5);
    @it.mate.protons.client.utils.DndUtils::jsLog(Lcom/google/gwt/dom/client/Element;)(dragableOffset);
    
    var touchMoveHandler = $entry(function(event) {
      var touch = event.targetTouches[0];
      dragable.style.left = touch.pageX - dragableOffset.left + 'px';
      dragable.style.top = touch.pageY - dragableOffset.top + 'px';
      event.preventDefault();
    });
    
    dragable.addEventListener('touchmove', touchMoveHandler, false);
    
    var mouseMoveHandler = $entry(function(event) {
      dragable.style.left = event.pageX - dragableOffset.left + 'px';
      dragable.style.top = event.pageY - dragableOffset.top + 'px';
    });
    
    var mouseDownHandler = $entry(function(event) {
      dragable.addEventListener("mousemove", mouseMoveHandler, false);
    });
    
    dragable.addEventListener('mousedown', mouseDownHandler, false);
    
    var detectHit = function (xt,yt,x1,y1,w1,h1) {
      if(xt-x1>w1) return false;
      if(yt-y1>h1) return false;
      return true;
    }
  
    var dropableOffset = @it.mate.protons.client.utils.DndUtils::getTouchOffsetImpl(Lcom/google/gwt/dom/client/Element;D)(dropable, 0);
    @it.mate.protons.client.utils.DndUtils::jsLog(Lcom/google/gwt/dom/client/Element;)(dropableOffset);
    
    var touchEndHandler = $entry(function(event) {
      var touch = event.targetTouches[0];
      if (detectHit(touch.pageX, touch.pageY, dropableOffset.left, dropableOffset.top, dropable.offsetWidth, dropable.offsetHeight)) {
        @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('detected hit on dropable element');
      }
    });
    
    dragable.addEventListener('touchend', touchEndHandler, false);
    
    var mouseUpHandler = $entry(function(event) {
      event.preventDefault();
      dragable.removeEventListener("mousemove", mouseMoveHandler, false); 
      if (detectHit(event.pageX, event.pageY, dropableOffset.left, dropableOffset.top, dropable.offsetWidth, dropable.offsetHeight)) {
        @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('detected hit on dropable element');
      }
    });
    
    dragable.addEventListener('mouseup', mouseUpHandler, false);
    
  }-*/;

  private static native void jsLog(Element element) /*-{
    var msg = @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(element);
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('element = ' + msg);
  }-*/;
  
  private static native void jsCloneElementOnTouch(Element element, ElementCallback callback) /*-{
    
    var touchHandler = $entry(function(event) {
      var clonedElem = element.cloneNode(true);
      element.parentElement.appendChild(clonedElem);
      callback.@it.mate.protons.client.utils.DndUtils.ElementCallback::handle(Lcom/google/gwt/dom/client/Element;)(clonedElem);
    });
    
    element.addEventListener('mousedown', touchHandler, false);
    element.addEventListener('touchstart', touchHandler, false);
    
  }-*/;
  
  public interface ElementCallback {
    public void handle(Element element);
  }
  
  public static void doTest4(Element dragable, final Element dropable) {
    jsCloneElementOnTouch(dragable, new ElementCallback() {
      public void handle(final Element dragable) {
        GwtUtils.deferredExecution(new Delegate<Void>() {
          public void execute(Void element) {
            
            jsTriggerTouchEventImpl(dragable);
            
            GwtUtils.deferredExecution(new Delegate<Void>() {
              public void execute(Void element) {
                jsMakeDraggableAndDropable4Impl(dragable, dropable);
              }
            });
            
          }
        });
      }
    });
  }
  
  private static native void jsMakeDraggableAndDropable4Impl(Element dragable, Element dropable) /*-{

    dragable.style.zIndex = "99999";
  
    var lastTouch;
    
    var mobileDetected = (typeof window.orientation !== 'undefined');
  
    var dragableOffset = @it.mate.protons.client.utils.DndUtils::getTouchOffsetImpl(Lcom/google/gwt/dom/client/Element;D)(dragable, 0.5);
    @it.mate.protons.client.utils.DndUtils::jsLog(Lcom/google/gwt/dom/client/Element;)(dragableOffset);
    
    var touchMoveHandler = $entry(function(event) {
      var touch = event.targetTouches[0];
      if (touch == null || typeof touch == 'undefined') {
        return;
      }
      lastTouch = touch;
      dragable.style.left = touch.pageX - dragableOffset.left + 'px';
      dragable.style.top = touch.pageY - dragableOffset.top + 'px';
      event.preventDefault();
    });
    
    dragable.addEventListener('touchmove', touchMoveHandler, false);
    
    var mouseMoveHandler = $entry(function(event) {
      dragable.style.left = event.pageX - dragableOffset.left + 'px';
      dragable.style.top = event.pageY - dragableOffset.top + 'px';
    });
    
    dragable.addEventListener("mousemove", mouseMoveHandler, false);
    
    var detectHit = function (xt,yt,x1,y1,w1,h1) {
      if(xt-x1>w1) return false;
      if(yt-y1>h1) return false;
      return true;
    }
  
    var dropableOffset = @it.mate.protons.client.utils.DndUtils::getTouchOffsetImpl(Lcom/google/gwt/dom/client/Element;D)(dropable, 0);
    @it.mate.protons.client.utils.DndUtils::jsLog(Lcom/google/gwt/dom/client/Element;)(dropableOffset);
    
    var touchEndHandler = $entry(function(event) {
      dragable.removeEventListener("touchmove", touchMoveHandler, false); 
//    var touch = event.targetTouches[0];
//    if (touch == null || typeof touch == 'undefined') {
//      return;
//    }
      if (lastTouch == null || typeof lastTouch == 'undefined') {
        return;
      }
      if (detectHit(lastTouch.pageX, lastTouch.pageY, dropableOffset.left, dropableOffset.top, dropable.offsetWidth, dropable.offsetHeight)) {
        @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('detected hit on dropable element (touch)');
      }
    });
    
    dragable.addEventListener('touchend', touchEndHandler, false);
    
    var mouseUpHandler = $entry(function(event) {
      event.preventDefault();
      dragable.removeEventListener("mousemove", mouseMoveHandler, false); 
      if (mobileDetected) {
        return;
      }
      if (detectHit(event.pageX, event.pageY, dropableOffset.left, dropableOffset.top, dropable.offsetWidth, dropable.offsetHeight)) {
        @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('detected hit on dropable element (mouse)');
      }
    });
    
    dragable.addEventListener('mouseup', mouseUpHandler, false);
    
  }-*/;

  /*
   * SEE https://developer.apple.com/library/iad/documentation/UserExperience/Reference/TouchEventClassReference/TouchEvent/TouchEvent.html#//apple_ref/javascript/instm/TouchEvent/initTouchEvent
   */
  private static native void jsTriggerTouchEventImpl(Element target) /*-{
    var mobileDetected = (typeof window.orientation !== 'undefined');
    if (!mobileDetected) {
      return;
    }
    var event = $doc.createEvent('TouchEvent');
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)(
      'jsTriggerTouchEventImpl.1 - '+
      @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(event)
    );
    var offset = @it.mate.protons.client.utils.DndUtils::getTouchOffsetImpl(Lcom/google/gwt/dom/client/Element;D)(target, 0);
    @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)(
      'jsTriggerTouchEventImpl.2 - '+
      @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(offset)
    );
    var touch = $doc.createTouch($wnd, target, 0, (offset.left+1), (offset.top+1), (offset.left+1), (offset.top+1));
    var touchlist = $doc.createTouchList(touch);
    event.initTouchEvent ('touchstart', true, true, $wnd, 1, (offset.left+1), (offset.top+1), (offset.left+1), (offset.top+1), 
      false, false, false, false, touchlist, touchlist, touchlist, 1.0, 0.0);
    target.dispatchEvent(event);  
  }-*/;

}

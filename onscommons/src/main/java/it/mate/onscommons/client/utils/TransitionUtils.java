package it.mate.onscommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.StringUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class TransitionUtils {
  
  public static final int FADE_IN = 1;
  
  public static class Options {
    int type;
    int deferring = 250;
    int delay = 1;
    int duration = 2000;
    protected Options() { }
    @Override
    public String toString() {
      return "Options [type=" + type + ", deferring=" + deferring + ", delay=" + delay + ", duration=" + duration + "]";
    }
    public Options setType(int type) {
      this.type = type;
      return this;
    }
    public Options setDeferring(int deferring) {
      this.deferring = deferring;
      return this;
    }
    public Options setDelay(int delay) {
      if (this.delay == 1)  {
        this.delay = delay;
      }
      return this;
    }
    public Options setDuration(int duration) {
      this.duration = duration;
      return this;
    }
  }
  
  public static Options parseAttributeValue(String value) {
    Options options = new Options();
    boolean durSet=false, delSet=false;
    String[] tokens = value.split(",");
    for (String token : tokens) {
      if (token.equalsIgnoreCase("fadeIn")) {
        options.type = FADE_IN;
      }
      if (StringUtils.isNumber(token) && !durSet) {
        options.duration = Integer.parseInt(token);
        durSet = true;
      } else if (StringUtils.isNumber(token) && !delSet) {
        options.delay = Integer.parseInt(token);
        delSet = true;
      }
    }
    return options;
  }
  
  public static void fadeIn(Element element, final int duration) {
    OnsenUi.onAvailableElement(element, new Delegate<Element>() {
      public void execute(final Element availableElement) {
        GwtUtils.setJsPropertyString(availableElement.getStyle(), "transition", "opacity " + duration + "ms");
        GwtUtils.deferredExecution(new Delegate<Void>() {
          public void execute(Void element) {
            availableElement.getStyle().setOpacity(1.0);
          }
        });
      }
    });
  }

  public static void fadeIn(final Element element, Options options) {
    PhgUtils.log("SETTING TRANSITION WITH OPTIONS " + options);
    element.getStyle().setOpacity(0);
    JavaScriptObject style = element.getStyle();
    if (options.delay > 0) {
      PhgUtils.log("SETTING TRANSITION DELAY = " + options.delay);
      GwtUtils.setJsPropertyString(style, "transition-delay", options.delay+"s");
    }
    GwtUtils.setJsPropertyString(style, "transition", "opacity " + options.duration + "ms");
    //GwtUtils.setJsPropertyString(style, "-webkit-transition", "opacity 2s");
    
    GwtUtils.deferredExecution(options.deferring, new Delegate<Void>() {
      public void execute(Void dummy) {
        OnsenUi.onAvailableElement(element, new Delegate<Element>() {
          public void execute(Element availableElement) {
            availableElement.getStyle().setOpacity(1.0);
          }
        });
        
      }
    });
  }

}

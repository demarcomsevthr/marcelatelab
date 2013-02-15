package it.mate.gpg.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

/*
 * 
 *
public class LocaleImage extends TouchWidget {
 */

public class LocaleImage extends TouchWidget {
  
  private String locale;
  
  private Image image;
  
  private Element elem;
  

  public LocaleImage() {
    
    elem = DOM.createDiv();
    setElement(elem);
    
    addTouchStartHandler(new TouchStartHandler() {
      public void onTouchStart(TouchStartEvent event) {
        
        /*
        MetaElement meta = (MetaElement)Document.get().getElementById("localeSetting");
        meta.setContent("locale=" + LocaleImage.this.locale);
        GwtUtils.log("tapped " + LocaleImage.this.locale);
        */
        
        /*
        UrlBuilder builder = Window.Location.createUrlBuilder();
        builder.setParameter("locale", LocaleImage.this.locale);
        Window.Location.assign(builder.buildString());
        */
        
        Document.get().getElementById("debugDiv").setInnerHTML("setting locale " + LocaleImage.this.locale);
        Cookies.setCookie("mgwtLanguage", LocaleImage.this.locale);
        Window.Location.reload();
        
      }
    });
    
    image = new Image();
    
  }
  
  public LocaleImage(String locale) {
    this();
    this.locale = locale;
  }
  
  public void setLocale(String locale) {
    this.locale = locale;
  }
  
  public void setResource(ImageResource resource) {
    image.setResource(resource);
    elem.appendChild(image.getElement());
  }
  
}

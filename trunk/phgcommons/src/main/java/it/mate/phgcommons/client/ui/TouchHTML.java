package it.mate.phgcommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class TouchHTML extends TouchWidget {

  public TouchHTML() {
    this("");
  }
  
  public TouchHTML(String html) {
    this(SafeHtmlUtils.fromTrustedString(html));
  }
  
  public TouchHTML(SafeHtml html) {
    this(new HTML(html));
  }
  
  protected TouchHTML(HTML html) {
    setElement(html.getElement());
    addStyleName("phg-TouchHTML");
  }
  
  public void setHtml(SafeHtml html) {
    Element elem = getElement();
    elem.setInnerSafeHtml(html);
  }
  
  public void setText(String text) {
    setHtml(SafeHtmlUtils.fromTrustedString(text));
  }
  
}

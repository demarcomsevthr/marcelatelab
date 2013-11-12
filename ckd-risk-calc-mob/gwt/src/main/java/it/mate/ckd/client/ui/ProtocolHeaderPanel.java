package it.mate.ckd.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class ProtocolHeaderPanel extends SimpleContainer {
  
  private int backBtnWidth = 40;
  
  private int backBtnHeight = 50;
  
  private HTMLPanel headerButtonPanel;
  
  private BackButton backBtn;
  
  private HTML headerHtml;

  private DivElement btnContainer;
  private SpanElement spanImage1;
  private SpanElement spanImage2;
  private SpanElement spanImage3;
  
  private String classNamePrefix = "ckd-Protocol";

  public ProtocolHeaderPanel() {
    super();
    initUI();
  }
  
  private void initUI() {
    headerButtonPanel = new HTMLPanel("");
    btnContainer = Document.get().createDivElement();
    spanImage1 = Document.get().createSpanElement();
    spanImage2 = Document.get().createSpanElement();
    spanImage3 = Document.get().createSpanElement();
    btnContainer.appendChild(spanImage1);
    btnContainer.appendChild(spanImage2);
    btnContainer.appendChild(spanImage3);
    applyBackBtnDimensions();
    headerButtonPanel.getElement().appendChild(btnContainer);
    backBtn = new BackButton();
    setBackBtnText("Prev Step");
    headerButtonPanel.add(backBtn);
    headerHtml = new HTML();
    add(headerButtonPanel);
    add(headerHtml);
    applyStyleNames();
  }
  
  public void setBackBtnText(String text) {
    backBtn.setText(text);
  }
  
  public void setHeaderHtml(String html) {
    headerHtml.setHTML(html);
  }
  
  public HTMLPanel getHeaderButton() {
    return headerButtonPanel;
  }
  
  public HTML getHeaderHTML() {
    return headerHtml;
  }
  
  public void addBackBtnTouchStartHandler(TouchStartHandler handler) {
    backBtn.addTouchStartHandler(handler);
  }
  
  public void setClassNamePrefix(String classNamePrefix) {
    this.classNamePrefix = classNamePrefix;
    applyStyleNames();
  }
  
  public void setBackBtnHeight(int backBtnHeight) {
    this.backBtnHeight = backBtnHeight;
    applyBackBtnDimensions();
  }
  
  public void setBackBtnWidth(int backBtnWidth) {
    this.backBtnWidth = backBtnWidth;
    applyBackBtnDimensions();
  }

  private void applyBackBtnDimensions() {
    int marginLeft = 5;
    spanImage1.getStyle().setWidth(14, Unit.PX);
    spanImage1.getStyle().setHeight(backBtnHeight, Unit.PX);
    spanImage1.getStyle().setProperty("backgroundSize", "14px " + backBtnHeight + "px");
    spanImage2.getStyle().setWidth(backBtnWidth, Unit.PX);
    spanImage2.getStyle().setHeight(backBtnHeight, Unit.PX);
    spanImage2.getStyle().setProperty("backgroundSize", backBtnWidth + "px " + backBtnHeight + "px");
    spanImage2.getStyle().setLeft(marginLeft + 14, Unit.PX);
    spanImage3.getStyle().setWidth(8, Unit.PX);
    spanImage3.getStyle().setHeight(backBtnHeight, Unit.PX);
    spanImage3.getStyle().setProperty("backgroundSize", "8px " + backBtnHeight + "px");
    spanImage3.getStyle().setLeft(marginLeft + 14 + backBtnWidth - 2, Unit.PX);
  }
  
  private void applyStyleNames() {
    addStyleName(classNamePrefix+"HeaderPanel");
    addStyleName(classNamePrefix+"HeaderPanel2");
    btnContainer.addClassName(classNamePrefix+"HeaderButton2-button-container");
    spanImage1.addClassName(classNamePrefix+"HeaderButton2-image-1");
    spanImage2.addClassName(classNamePrefix+"HeaderButton2-image-2");
    spanImage3.addClassName(classNamePrefix+"HeaderButton2-image-3");
    backBtn.addStyleName(classNamePrefix+"HeaderButton2-text");
    headerHtml.addStyleName(classNamePrefix+"HeaderPanel2-center");
  }
  
  public static class BackButton extends TouchWidget {
    Element p;
    public BackButton() {
      p = Document.get().createPElement();
      setElement(p);
    }
    public void setText(String text) {
      p.setInnerText(text);
    }
  }
  
  public static String getBackStepImage1() {
    return "url('" + GWT.getModuleBaseURL() + "images/back-step-1.png" + "')";
  }
  
  public static String getBackStepImage2() {
    return "url('" + GWT.getModuleBaseURL() + "images/back-step-2.png" + "')";
  }
  
  public static String getBackStepImage3() {
    return "url('" + GWT.getModuleBaseURL() + "images/back-step-3.png" + "')";
  }
  
}

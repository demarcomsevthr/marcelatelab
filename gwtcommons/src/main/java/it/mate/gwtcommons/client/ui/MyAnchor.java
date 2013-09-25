package it.mate.gwtcommons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;

public class MyAnchor extends Anchor {
  
  public MyAnchor(String text, ClickHandler handler) {
    super(text);
    addClickHandler(handler);
  }

  public MyAnchor() {
    super();
  }

  public MyAnchor(boolean useDefaultHref) {
    super(useDefaultHref);
  }

  public MyAnchor(Element element) {
    super(element);
  }

  public MyAnchor(SafeHtml html, Direction dir, String href) {
    super(html, dir, href);
  }

  public MyAnchor(SafeHtml html, Direction dir) {
    super(html, dir);
  }

  public MyAnchor(SafeHtml html, DirectionEstimator directionEstimator, String href) {
    super(html, directionEstimator, href);
  }

  public MyAnchor(SafeHtml html, DirectionEstimator directionEstimator) {
    super(html, directionEstimator);
  }

  public MyAnchor(SafeHtml html, String href, String target) {
    super(html, href, target);
  }

  public MyAnchor(SafeHtml html, String href) {
    super(html, href);
  }

  public MyAnchor(SafeHtml html) {
    super(html);
  }

  public MyAnchor(String text, boolean asHtml, String href, String target) {
    super(text, asHtml, href, target);
  }

  public MyAnchor(String text, boolean asHTML, String href) {
    super(text, asHTML, href);
  }

  public MyAnchor(String text, boolean asHtml) {
    super(text, asHtml);
  }

  public MyAnchor(String text, Direction dir, String href) {
    super(text, dir, href);
  }

  public MyAnchor(String text, Direction dir) {
    super(text, dir);
  }

  public MyAnchor(String text, DirectionEstimator directionEstimator, String href) {
    super(text, directionEstimator, href);
  }

  public MyAnchor(String text, DirectionEstimator directionEstimator) {
    super(text, directionEstimator);
  }

  public MyAnchor(String text, String href, String target) {
    super(text, href, target);
  }

  public MyAnchor(String text, String href) {
    super(text, href);
  }

  public MyAnchor(String text) {
    super(text);
  }

}

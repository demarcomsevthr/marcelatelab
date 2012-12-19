package it.mate.econyx.client.text;

import com.google.gwt.dom.client.Document;
import com.google.gwt.text.client.DoubleRenderer;
import com.google.gwt.user.client.ui.ValueBox;

public class DecimalBox extends ValueBox<Double> {

  public DecimalBox() {
    super(Document.get().createTextInputElement(), DoubleRenderer.instance(),
        DecimalParser.instance());
  }
  
  
}

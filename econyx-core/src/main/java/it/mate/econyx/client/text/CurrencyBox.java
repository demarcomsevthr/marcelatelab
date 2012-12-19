package it.mate.econyx.client.text;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ValueBox;

public class CurrencyBox extends ValueBox<Double> {

  public CurrencyBox() {
    // 21/11/2012
    super(Document.get().createTextInputElement(), CurrencyRenderer.instance(), CurrencyParser.instance());
//  super(Document.get().createTextInputElement(), CurrencyRenderer.instance(), DoubleParser.instance());
  }
  
}

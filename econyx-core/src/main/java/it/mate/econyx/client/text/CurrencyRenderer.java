package it.mate.econyx.client.text;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;

public class CurrencyRenderer extends AbstractRenderer<Double> {

  private static CurrencyRenderer INSTANCE;
  
  private NumberFormat formatter = CurrencyFormat.instance();
  
  public static Renderer<Double> instance() {
    if (INSTANCE == null) {
      INSTANCE = new CurrencyRenderer();
    }
    return INSTANCE;
  }
  
  protected CurrencyRenderer () {
  }

  public String render(Double object) {
    if (object == null) {
      return "";
    }
    return formatter.format(object);
  }
  
}

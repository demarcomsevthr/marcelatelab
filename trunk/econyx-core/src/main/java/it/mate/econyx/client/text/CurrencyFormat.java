package it.mate.econyx.client.text;

import com.google.gwt.i18n.client.NumberFormat;

public class CurrencyFormat extends NumberFormat {

  private static NumberFormat INSTANCE;
  
  public static NumberFormat instance() {
    if (INSTANCE == null) {
      INSTANCE = NumberFormat.getCurrencyFormat("EUR");
    }
    return INSTANCE;
  }

  protected CurrencyFormat() {
    super("", null, true);
  }
  
  /**
   * @param currencyCode
   *    see com.google.gwt.i18n.client.constants.CurrencyCodeMapConstants.properties
   */
  public static void setDefaultFormat (String currencyCode) {
    INSTANCE = NumberFormat.getCurrencyFormat(currencyCode);
  }
  
}

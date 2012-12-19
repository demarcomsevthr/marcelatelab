package it.mate.econyx.client.text;

import java.text.ParseException;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Parser;

public class DecimalParser implements Parser<Double> {

  private static DecimalParser INSTANCE;

  private NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();
  
  public static Parser<Double> instance() {
    if (INSTANCE == null) {
      INSTANCE = new DecimalParser();
    }
    return INSTANCE;
  }
  
  protected DecimalParser () {
  }

  public Double parse(CharSequence object) throws ParseException {
    if ("".equals(object.toString())) {
      return 0d;
    }
    try {
      String text = object.toString();
      if (text.contains(",")) {
        text = text.replace(",", ".");
      }
      return decimalFormatter.parse(text);
    } catch (NumberFormatException e) {
      throw new ParseException(e.getMessage(), 0);
    }
  }
  
}

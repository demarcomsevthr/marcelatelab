package it.mate.ckd.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.constants.NumberConstantsImpl;

public class FixedNumberConstantsImpl implements NumberConstantsImpl {

  private static FixedNumberConstants inst = GWT.create(FixedNumberConstants.class);
  
  @Override
  public String notANumber() {
    return inst.notANumber();
  }

  @Override
  public String currencyPattern() {
    return inst.currencyPattern();
  }

  @Override
  public String decimalPattern() {
    return inst.decimalPattern();
  }

  @Override
  public String decimalSeparator() {
    return inst.decimalSeparator();
  }

  @Override
  public String defCurrencyCode() {
    return inst.defCurrencyCode();
  }

  @Override
  public String exponentialSymbol() {
    return inst.exponentialSymbol();
  }

  @Override
  public String globalCurrencyPattern() {
    return inst.globalCurrencyPattern();
  }

  @Override
  public String groupingSeparator() {
    return inst.groupingSeparator();
  }

  @Override
  public String infinity() {
    return inst.infinity();
  }

  @Override
  public String minusSign() {
    return inst.minusSign();
  }

  @Override
  public String monetaryGroupingSeparator() {
    return inst.monetaryGroupingSeparator();
  }

  @Override
  public String monetarySeparator() {
    return inst.monetarySeparator();
  }

  @Override
  public String percent() {
    return inst.percent();
  }

  @Override
  public String percentPattern() {
    return inst.percentPattern();
  }

  @Override
  public String perMill() {
    return inst.perMill();
  }

  @Override
  public String plusSign() {
    return inst.plusSign();
  }

  @Override
  public String scientificPattern() {
    return inst.scientificPattern();
  }

  @Override
  public String simpleCurrencyPattern() {
    return inst.simpleCurrencyPattern();
  }

  @Override
  public String zeroDigit() {
    return inst.zeroDigit();
  }
  
  
  
}

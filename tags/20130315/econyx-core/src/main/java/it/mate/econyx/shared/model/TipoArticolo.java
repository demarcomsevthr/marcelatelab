package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.List;

public interface TipoArticolo extends Serializable {
  
  public final static String Generic = "GEN";

  public final static String Stamp = "STA";

  /*
  public enum Hardcodes {
    Generic ("GEN"),
    Stamp ("STA");
    private String code;
    private Hardcodes(String code) {
      this.code = code;
    }
    public String code() {
      return code;
    }
    public static Hardcodes valueOfCode(String code) {
      for (Hardcodes item : Hardcodes.values()) {
        if (item.code().equals(code)) {
          return item;
        }
      }
      throw new IllegalArgumentException(code);
    }
    public static ProductType typeFromCode (List<? extends ProductType> types, Hardcodes code) {
      for (ProductType type : types) {
        if (type.getHardcode() == code) {
          return type;
        }
      }
      throw new IllegalArgumentException(code.code());
    }
  }
  */
  
  public static class Decoder {
    public static TipoArticolo typeFromCode (List<? extends TipoArticolo> types, String internalCode) {
      for (TipoArticolo type : types) {
        if (type.getCodiceInterno().equals(internalCode)) {
          return type;
        }
      }
      throw new IllegalArgumentException("internalCode = " + internalCode);
    }
    public static String valueOfCode(String code) {
      if (Generic.equals(code)) {
        return Generic;
      } else if (Stamp.equals(code)) {
        return Stamp;
      } else {
        throw new IllegalArgumentException("internalCode = " + code);
      }
    }
    public static boolean equals (TipoArticolo tip1, String internalCode) {
      return tip1.getCodiceInterno().equals(internalCode);
    }
  }
  

  public String getId();

  public void setId(String id);

  public String getCodice();

  public void setCodice(String code);

  public String getCodiceInterno();

  public void setCodiceInterno(String internalCode);
  
}

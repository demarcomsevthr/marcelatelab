package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.TipoArticolo;

@SuppressWarnings("serial")
public class TipoArticoloTx implements TipoArticolo {

  String id;
  
  String codice;
  
  String codiceInterno;
  
  /*
  public static ProductTypeTx clone (ProductType source) {
    if (source != null) {
      if (source instanceof ProductTypeTx) {
        return (ProductTypeTx)source;
      } else {
        return BeanUtils.copyProperties(source, new ProductTypeTx());
      }
    }
    return null;
  }

  public static List<? extends ProductType> clone (List<? extends ProductType> sources) {
    List<ProductTypeTx> results = new ArrayList<ProductTypeTx>();
    for (ProductType source : sources) {
      results.add(clone(source));
    }
    return results;
  }
  */
  
  @Override
  public String toString() {
    return "ProductTypeTx [code=" + codice + ", internalCode=" + codiceInterno + ", id=" + id + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodice() {
    return codice;
  }

  public void setCodice(String code) {
    this.codice = code;
  }

  public String getCodiceInterno() {
    return codiceInterno;
  }

  public void setCodiceInterno(String internalCode) {
    this.codiceInterno = internalCode;
  }
  
}

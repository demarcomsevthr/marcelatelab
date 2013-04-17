package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.List;

@SuppressWarnings("serial")
public class ProduttoreTx implements Produttore {

  String id;
  
  String codice;
  
  String nome;
  
  List<ArticoloTx> products;

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

  public String getNome() {
    return nome;
  }

  public void setNome(String name) {
    this.nome = name;
  }

  public List<Articolo> getProducts() {
    return CollectionPropertyClientUtil.cast(products, ArticoloTx.class);
  }

  @CloneableProperty (targetClass=ArticoloTx.class)
  public void setProducts(List<Articolo> products) {
    this.products = CollectionPropertyClientUtil.clone(products, ArticoloTx.class);
  }
  
}

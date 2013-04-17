package it.mate.econyx.shared.model;

import it.mate.econyx.client.view.admin.scaffold.HasId;

import java.io.Serializable;
import java.util.List;

public interface Produttore extends Serializable, HasId, PortalEntity {
  
  public String getId();

  public void setId(String id);

  public String getCodice();

  public void setCodice(String code);

  public String getNome();

  public void setNome(String name);
  
  public List<Articolo> getProducts();
  
  public void setProducts(List<Articolo> products);

}

package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface ContoUtenteMovimento extends Serializable {
  
  public static final String POSITIVO = "P";
  
  public static final String NEGATIVO = "N";
  
  public String getId();

  public void setId(String id);

  public Double getImporto();
  
  public void setImporto(Double importo);
  
  public String getSegno();
  
  public void setSegno(String segno);
  
  public String getCausale();
  
  public void setCausale(String causale);
  
  public Date getData();

  public void setData(Date data);
  
  public Order getOrder();

  public void setOrder(Order order);
  
  public PortalUser getRegisteringPortalUser();
  
  public void setRegisteringPortalUser(PortalUser portalUser);

}

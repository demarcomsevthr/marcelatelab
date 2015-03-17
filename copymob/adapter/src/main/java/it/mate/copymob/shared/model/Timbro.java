package it.mate.copymob.shared.model;

import java.io.Serializable;

public interface Timbro extends Serializable {

  public void setImage(String image);

  public String getImage();
  
  public String getImageData();

  public void setCodice(String codice);

  public String getCodice();

  public void setNome(String nome);

  public String getNome();

  public void setId(Integer id);

  public Integer getId();
  
  /*
  public void setOval(boolean oval);
  public boolean isOval();
  public Integer getOvalInt();
  public void setOvalInt(Integer oval);
  */

  public void setHeight(Double height);

  public Double getHeight();

  public void setWidth(Double width);

  public Double getWidth();

  public void setRemoteId(String remoteId);

  public String getRemoteId();

  public Boolean getOval();

  public void setOval(Boolean oval);

  public void setPrezzo(Double prezzo);

  public Double getPrezzo();

}

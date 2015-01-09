package it.mate.copymob.shared.model;

import java.io.Serializable;

public interface Timbro extends Serializable {

  public void setImage(String image);

  public String getImage();

  public void setCodice(String codice);

  public String getCodice();

  public void setNome(String nome);

  public String getNome();

  public void setId(Integer id);

  public Integer getId();

}

package it.mate.protons.shared.model;

import java.io.Serializable;

public interface PrincipioAttivo extends Serializable {

  public void setPath(String path);

  public String getPath();

  public void setNome(String nome);

  public String getNome();

  public void setId(Integer id);

  public Integer getId();

}

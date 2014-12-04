package it.mate.therapyreminder.shared.model;

import java.io.Serializable;

public interface Paziente extends Serializable {

  public Integer getId();

  public void setId(Integer id);

  public String getNome();

  public void setNome(String nome);
  
}

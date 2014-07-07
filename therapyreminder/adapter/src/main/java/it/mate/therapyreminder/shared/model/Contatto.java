package it.mate.therapyreminder.shared.model;

import java.io.Serializable;

public interface Contatto extends Serializable {
  
  public final static String TIPO_MEDICO = "D";

  public final static String TIPO_TUTOR = "T";

  public void setId(Integer id);

  public Integer getId();
  
  public String getTipo();

  public void setTipo(String tipo);

  public void setTelefono(String phoneNumber);

  public String getTelefono();

  public void setEmail(String email);

  public String getEmail();

  public void setNome(String name);

  public String getNome();
  
  public String getIndirizzo();

  public void setIndirizzo(String indirizzo);

  public String getOrari();

  public void setOrari(String orari);

}

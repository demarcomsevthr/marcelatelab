package it.mate.econyx.shared.model;

import java.io.Serializable;

public interface Indirizzo extends Serializable {

  public void setEmail(String email);

  public String getEmail();

  public void setTelefono(String telefono);

  public String getTelefono();

  public void setCitta(String citta);

  public String getCitta();

  public void setCap(String cap);

  public String getCap();

  public void setVia(String via);

  public String getVia();

  public void setCognome(String cognome);

  public String getCognome();

  public void setNome(String nome);

  public String getNome();

  public void setSesso(String sesso);

  public String getSesso();

  public void setAzienda(String azienda);

  public String getAzienda();

  public void setId(String id);

  public String getId();

}

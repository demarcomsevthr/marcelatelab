package it.mate.copymob.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Applicazione extends Serializable {

  public void setId(Integer id);

  public Integer getId();

  public void setNome(String nome);

  public String getNome();

  public void setDataInizio(Date dataInizio);

  public Date getDataInizio();

  public void setDataFine(Date dataFine);

  public Date getDataFine();

  /*
  public void setPrincipiAttivi(List<PrincipioAttivo> principiAttivi);
  */

  public List<PrincipioAttivo> getPrincipiAttivi();

}

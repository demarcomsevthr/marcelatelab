package it.mate.therapyreminder.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface Prescrizione extends Serializable {

  public void setId(Integer id);

  public Integer getId();

  public void setTipoRicorrenza(String tipoRicorrenza);

  public String getTipoRicorrenza();

  public void setIdComposizione(Long idComposizione);

  public Long getIdComposizione();

  public void setQuantita(Double quantita);

  public Double getQuantita();

  public void setDataFine(Date dataFine);

  public Date getDataFine();

  public void setDataInizio(Date dataInizio);

  public Date getDataInizio();

  public void setNome(String nome);

  public String getNome();

  public String getCodUdM();

  public void setCodUdM(String codUdM);

  public Integer getValoreRicorrenza();

  public void setValoreRicorrenza(Integer valoreRicorrenza);
  
}

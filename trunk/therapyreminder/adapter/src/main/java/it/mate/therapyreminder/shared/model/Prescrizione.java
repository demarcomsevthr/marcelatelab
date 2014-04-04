package it.mate.therapyreminder.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface Prescrizione extends Serializable {
  
  public final static String TIPO_RICORRENZA_GIORNALIERA = "G";

  public final static String TIPO_RICORRENZA_SETTIMANALE = "S";

  public final static String TIPO_RICORRENZA_MENSILE = "M";

  public final static String TIPO_ORARI_A_INTERVALLI = "I";

  public final static String TIPO_ORARI_FISSI = "F";

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
  
  public String getTipoRicorrenzaOraria();

  public void setTipoRicorrenzaOraria(String tipoRicorrenzaOraria);

  public Integer getIntervalloOrario();

  public void setIntervalloOrario(Integer intervalloOrario);

  public String getOrari();

  public void setOrari(String orari);
  
}

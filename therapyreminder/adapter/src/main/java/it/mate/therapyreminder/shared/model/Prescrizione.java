package it.mate.therapyreminder.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Prescrizione extends Serializable {
  
  public final static String TIPO_RICORRENZA_GIORNALIERA = "G";

  public final static String TIPO_RICORRENZA_OGNI_GIORNO = "G1";

  public final static String TIPO_RICORRENZA_GIORNI_ALTERNI = "G2";

  public final static String TIPO_RICORRENZA_SETTIMANALE = "S";

  public final static String TIPO_RICORRENZA_MENSILE = "M";

  public final static String TIPO_RICORRENZA_TRIMESTRALE = "T3";

  public final static String TIPO_RICORRENZA_SEMESTRALE = "T6";

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

  public List<Dosaggio> getDosaggi();

  public void setDosaggi(List<Dosaggio> dosaggi);
  
  public boolean hasEqualSomministrazioneOf(Prescrizione that);
  
  public boolean isPersistent();

  public void setUltimoAvvisoRiordino(Date ultimoAvvisoRiordino);

  public Date getUltimoAvvisoRiordino();

  public void setQtaRimanente(Double qtaRimanente);

  public Double getQtaRimanente();

  public void setQtaPerAvviso(Double qtaPerAvviso);

  public Double getQtaPerAvviso();

  public void setQtaPerConfez(Double qtaPerConfez);

  public Double getQtaPerConfez();

  public boolean isGstAvvisoRiordino();

  public void setFlgGstAvvisoRiordino(Integer flgGstAvvisoRiordino);

  public Integer getFlgGstAvvisoRiordino();

  public Double getNumConfezioni();
  
  public void setGstAvvisoRiordino(Boolean flgGstAvvisoRiordino);
  
  public Contatto getTutor();
  
  public void setTutor(Contatto tutor);
  
  public Date getActualDataFine();
  
  public Date getDataLimiteSviluppoSomministrazioni();

  public void setDataLimiteSviluppoSomministrazioni(Date dataLimiteSviluppoSomministrazioni);

}

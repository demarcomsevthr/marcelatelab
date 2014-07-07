package it.mate.therapyreminder.server.model;

import it.mate.commons.server.model.HasKey;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class SomministrazioneDs implements HasKey, Somministrazione {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  private Integer localId;
  
  @Persistent
  private Date data;
  
  @Persistent
  private Double quantita;
  
  @Persistent
  private String orario;

  @Persistent
  private Integer stato;
  
  @Persistent
  private String language;

  
  //////////////////////////////////////////////////////////////////////
  // INFORMAZIONI SOLO LATO SERVER

  @Persistent (dependentKey="false", defaultFetchGroup="true")
  private Key devInfoId;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  private Key accountId;
  
  @Persistent
  private String nomeTutor;
  
  @Persistent
  private String emailTutor;
  
  @Persistent
  private String telefonoTutor;
  
  @Persistent
  private String nomeFarmaco;
  
  //////////////////////////////////////////////////////////////////////

  
  @Override
  public String toString() {
    return "SomministrazioneDs [id=" + id + ", localId=" + localId + ", data=" + data + ", quantita=" + quantita + ", orario=" + orario + ", stato=" + stato
        + ", language=" + language + ", devInfoId=" + devInfoId + ", accountId=" + accountId + ", nomeTutor=" + nomeTutor + ", emailTutor=" + emailTutor
        + ", telefonoTutor=" + telefonoTutor + ", nomeFarmaco=" + nomeFarmaco + "]";
  }


  @Override
  public void setPrescrizione(Prescrizione prescrizione) {
    if (prescrizione != null) {
      this.nomeFarmaco = prescrizione.getNome();
      setTutor(prescrizione.getTutor());
    }
  }
  
  public void setTutor(Contatto tutor) {
    this.nomeTutor = tutor != null ? tutor.getNome() : null;
    this.emailTutor = tutor != null ? tutor.getEmail() : null;
    this.telefonoTutor = tutor != null ? tutor.getTelefono() : null;
  }

  //////////////////////////////////////////////////////////////////////

  @Override
  public Key getKey() {
    return id;
  }
  
  @Override
  public void setRemoteId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  @Override
  public String getRemoteId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }
  
  public void setLocalId(Integer id) {
    this.localId = id;
  }

  public Integer getLocalId() {
    return localId;
  }

  @Deprecated  // usare setLocalId
  @Override
  public void setId(Integer id) {
    setLocalId(id);
  }

  @Deprecated  // usare getLocalId
  @Override
  public Integer getId() {
    return getLocalId();
  }

  
  
  ///////////////////////////////////////////////////////////////////////
  
  public String getDevInfoId() {
    return devInfoId != null ? KeyFactory.keyToString(devInfoId) : null;
  }

  public void setDevInfoId(String devInfoId) {
    this.devInfoId = devInfoId != null ? KeyFactory.stringToKey(devInfoId) : null;
  }

  public Key getAccountId() {
    return accountId;
  }

  public void setAccountId(Key accountId) {
    this.accountId = accountId;
  }
  
  public String getNomeTutor() {
    return nomeTutor;
  }

  public void setNomeTutor(String nomeTutor) {
    this.nomeTutor = nomeTutor;
  }

  public String getEmailTutor() {
    return emailTutor;
  }

  public void setEmailTutor(String emailTutor) {
    this.emailTutor = emailTutor;
  }

  public String getTelefonoTutor() {
    return telefonoTutor;
  }

  public void setTelefonoTutor(String telefonoTutor) {
    this.telefonoTutor = telefonoTutor;
  }
  
  public String getNomeFarmaco() {
    return nomeFarmaco;
  }

  public void setNomeFarmaco(String nomeFarmaco) {
    this.nomeFarmaco = nomeFarmaco;
  }


  ///////////////////////////////////////////////////////////////////////

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Double getQuantita() {
    return quantita;
  }

  public void setQuantita(Double quantita) {
    this.quantita = quantita;
  }

  public String getOrario() {
    return orario;
  }

  public void setOrario(String orario) {
    this.orario = orario;
  }

  public Integer getStato() {
    return stato;
  }

  public void setStato(Integer stato) {
    this.stato = stato;
  }
  
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }


  /////////////////////////////////////////////////////////////////////////////////
  
  @Override
  public void setEseguita() {

  }

  @Override
  public boolean isEseguita() {
    return false;
  }

  @Override
  public void setSchedulata() {

  }

  @Override
  public boolean isSchedulata() {
    return false;
  }

  @Override
  public boolean isAnnullata() {
    return false;
  }

  @Override
  public void setAnnullata() {

  }
  
  @Override
  public Prescrizione getPrescrizione() {
    return null;
  }

  /////////////////////////////////////////////////////////////////////
  
}

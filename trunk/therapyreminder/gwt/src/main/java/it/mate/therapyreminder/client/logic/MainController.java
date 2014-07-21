package it.mate.therapyreminder.client.logic;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.phgcommons.client.plugins.CalendarEvent;
import it.mate.phgcommons.client.plugins.CalendarPlugin;
import it.mate.phgcommons.client.plugins.LocalNotificationsPlugin;
import it.mate.phgcommons.client.utils.IterationUtil;
import it.mate.phgcommons.client.utils.IterationUtil.ItemDelegate;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.PhonegapLog;
import it.mate.phgcommons.client.utils.Time;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.impl.AccountTx;
import it.mate.therapyreminder.shared.model.impl.PrescrizioneTx;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.ui.client.MGWT;

public class MainController {

  private MainDao dao = AppClientFactory.IMPL.getGinjector().getPrescrizioniDao();
  
  private static MainController instance;
  
  public static MainController getInstance() {
    if (instance == null)
      instance = new MainController();
    return instance;
  }
  
  protected MainController() {
    // setting dao error delegate
    dao.setErrorDelegate(new Delegate<String>() {
      public void execute(String errorMessage) {
        PhgUtils.log(errorMessage);
        PhgDialogUtils.showMessageDialog(errorMessage);
      }
    });

    if (!OsDetectionUtils.isDesktop()) {
      checkOrphanNotifications();
      LocalNotificationsPlugin.setOnCancel(new LocalNotificationsPlugin.JSOEventCallback() {
        public void handleEvent(String id, String state, String json) {
          PhgUtils.log("RECEIVED CANCEL NOTIFICATION EVENT: id="+id);
        }
      });
    }
  }
  
  private void checkOrphanNotifications() {
    final ObjectWrapper<Timer> timer = new ObjectWrapper<Timer>();
    timer.set(GwtUtils.createTimer(1000, new Delegate<Void>() {
      public void execute(Void element) {
        if (MainController.getInstance().isReady()) {
          timer.get().cancel();
          
          // purge orphan notifications
          LocalNotificationsPlugin.getScheduledIds(new Delegate<List<String>>() {
            public void execute(List<String> ids) {
              MainController.getInstance().purgeNotificationIds(ids);
            }
          });
          
        }
      }
    }));
  }
  
  public boolean isReady() {
    return dao != null && dao.isReady();
  }
  
  public void createPrescrizioneWithDefaults(Delegate<Prescrizione> delegate) {
    Prescrizione prescrizione = new PrescrizioneTx();
    prescrizione.setDataInizio(new Date());
    prescrizione.setQuantita(1d);
    prescrizione.setTipoRicorrenza(Prescrizione.TIPO_RICORRENZA_OGNI_GIORNO);
    prescrizione.setValoreRicorrenza(1);
    prescrizione.setCodUdM("01");
    prescrizione.setTipoRicorrenzaOraria(Prescrizione.TIPO_ORARI_FISSI);
    prescrizione.setIntervalloOrario(1);
    delegate.execute(prescrizione);
  }
  
  public void findSomministrazioniScadute(final Delegate<List<Somministrazione>> delegate) {
    Date dataRiferimento = new Date();
    dao.findSomministrazioniScadute(dataRiferimento, new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> somministrazioni) {
        if (somministrazioni == null || somministrazioni.size() <= 0) {
          delegate.execute(null);
        } else {
          delegate.execute(somministrazioni);
        }
      }
    });
  }
  
  public void sviluppaSomministrazioniInBackground() {
    if (sviluppaSomministrazioni$operazioneInCorso) {
      return;
    }
    dao.findAllPrescrizioniAttive(new Date(), new Delegate<List<Prescrizione>>() {
      public void execute(List<Prescrizione> prescrizioni) {
        if (prescrizioni == null || prescrizioni.size() == 0) {
          return;
        }
        for (Prescrizione prescrizione : prescrizioni) {
          sviluppaSomministrazioni(prescrizione, null);
        }
      }
    });
  }
  
  public void annullaSomministrazioni(final List<Somministrazione> somministrazioni, Delegate<Void> resultDelegate) {
    iterateSomministrazioniForAnnullamento(somministrazioni.iterator(), resultDelegate);
  }
  
  private void iterateSomministrazioniForAnnullamento(final Iterator<Somministrazione> it, final Delegate<Void> resultDelegate) {
    if (it.hasNext()) {
      Somministrazione somministrazione = it.next();
      somministrazione.setAnnullata();
      updateSomministrazione(somministrazione, new Delegate<Somministrazione>() {
        public void execute(Somministrazione element) {
          iterateSomministrazioniForAnnullamento(it, resultDelegate);
        }
      }, new Delegate<Somministrazione>() {
        public void execute(Somministrazione element) { }
      });
    } else {
      resultDelegate.execute(null);
    }
  }
  
  public void updateSomministrazione(final Somministrazione newSomministrazione, final Delegate<Somministrazione> somministrazioneAggiornataDelegate, 
      final Delegate<Somministrazione> farmacoDaRiordinareDelegate) {
    dao.findSomministrazioneById(newSomministrazione.getId(), new Delegate<Somministrazione>() {
      public void execute(final Somministrazione oldSomministrazione) {
        dao.saveSomministrazione(newSomministrazione, new Delegate<Somministrazione>() {
          public void execute(Somministrazione somministrazioneAggiornata) {
            if (isRemoteSomministrazione(newSomministrazione)) {
              saveRemoteSomministrazione(newSomministrazione, new Delegate<Somministrazione>() {
                public void execute(Somministrazione result) {
                  PhgUtils.log("updated remote somministrazione " + result);
                }
              });
            }
            
            // CHECK: FARMACO DA RIORDINARE
            if (!oldSomministrazione.isEseguita() && newSomministrazione.isEseguita()) {
              double qtaSomministrata = newSomministrazione.getQuantita();
              Prescrizione prescrizione = somministrazioneAggiornata.getPrescrizione();
              
              if (prescrizione.getQtaPerAvviso() != null && prescrizione.getQtaPerAvviso() > 0) {
                double qtaRimanente = prescrizione.getQtaRimanente();
                double qtaPerRiordino = prescrizione.getQtaPerAvviso();
                if (qtaRimanente - qtaSomministrata > 0) {
                  qtaRimanente -= qtaSomministrata;
                  prescrizione.setQtaRimanente(qtaRimanente);
                  dao.savePrescrizione(prescrizione, new Delegate<Prescrizione>() {
                    public void execute(Prescrizione result) {

                    }
                  });
                }
                if (qtaRimanente < qtaPerRiordino) {
                  farmacoDaRiordinareDelegate.execute(somministrazioneAggiornata);
                  return;
                }
              }
              
            }
            
            somministrazioneAggiornataDelegate.execute(somministrazioneAggiornata);
            
          }
        });
      }
    });
  }
  
  
  public void findSomministrazioniNonEseguiteInMemoria(final Delegate<List<Somministrazione>> resultsDelegate) {
    Date today = new Date();
    dao.findAllPrescrizioniAttive(today, new Delegate<List<Prescrizione>>() {
      public void execute(List<Prescrizione> prescrizioniAttive) {
        if (prescrizioniAttive != null) {
          List<Somministrazione> results = new ArrayList<Somministrazione>();
          iteratePrescrizioniAttiveForSviluppoInMemoria(prescrizioniAttive.iterator(), results, resultsDelegate);
        }
      }
    });
  }
  
  private void iteratePrescrizioniAttiveForSviluppoInMemoria(final Iterator<Prescrizione> it, final List<Somministrazione> results, final Delegate<List<Somministrazione>> resultsDelegate) {
    if (it.hasNext()) {
      Prescrizione prescrizione = it.next();
      setDataLimiteSviluppoSomministrazioniInMemoria(prescrizione);
      sviluppaSomministrazioniInMemoria(prescrizione, new Delegate<List<Somministrazione>>() {
        public void execute(List<Somministrazione> somministrazioni) {
          if (somministrazioni != null && somministrazioni.size() > 0) {
            results.addAll(somministrazioni);
          }
          iteratePrescrizioniAttiveForSviluppoInMemoria(it, results, resultsDelegate);
        }
      });
    } else {
      resultsDelegate.execute(results);
    }
  }
  
  private void setDataLimiteSviluppoSomministrazioniInMemoria(Prescrizione prescrizione) {
    Date dataLimiteSviluppoSomministrazioni = new Date();
    if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_GIORNALIERA)) {
      CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, +30);
      prescrizione.setDataLimiteSviluppoSomministrazioni(dataLimiteSviluppoSomministrazioni);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_SETTIMANALE)) {
      CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, +90);
      prescrizione.setDataLimiteSviluppoSomministrazioni(dataLimiteSviluppoSomministrazioni);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_MENSILE)) {
      CalendarUtil.addMonthsToDate(dataLimiteSviluppoSomministrazioni, +12);
      prescrizione.setDataLimiteSviluppoSomministrazioni(dataLimiteSviluppoSomministrazioni);
    }
  }

  public void sviluppaSomministrazioniInMemoria(final Prescrizione prescrizione, final Delegate<List<Somministrazione>> resultsDelegate) {
    dao.findSomministrazioniSchedulateByPrescrizione(prescrizione, new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> somministrazioniPersistenti) {
        Date dataUltimaSomministrazione = CalendarUtil.copyDate(prescrizione.getDataInizio());
        CalendarUtil.addDaysToDate(dataUltimaSomministrazione, -1);
        if (somministrazioniPersistenti != null && somministrazioniPersistenti.size() > 0) {
          dataUltimaSomministrazione = CalendarUtil.copyDate(somministrazioniPersistenti.get(somministrazioniPersistenti.size() - 1).getData());
        }
        if (prescrizione.getDataFine() != null) {
          if (dataUltimaSomministrazione.after(prescrizione.getDataFine())) {
            resultsDelegate.execute(somministrazioniPersistenti);
            return;
          }
        }
        final boolean inMemory = true;
        sviluppaRicorrenzaSuccessiva(prescrizione, dataUltimaSomministrazione, somministrazioniPersistenti, inMemory, new Delegate<List<Somministrazione>>() {
          public void execute(List<Somministrazione> somministrazioniTotali) {
            resultsDelegate.execute(somministrazioniTotali);
          }
        });
        
      }
    });
  }
  
  

  private Delegate<Prescrizione> sviluppaSomministrazioni$completionDelegate = null;
  
  private boolean sviluppaSomministrazioni$operazioneInCorso = false;
  
  public void sviluppaSomministrazioni(final Prescrizione prescrizione, final Delegate<Prescrizione> completionDelegate) {
    PhgUtils.log("SVILUPPO SOMMINISTRAZIONI per " + prescrizione);
    this.sviluppaSomministrazioni$operazioneInCorso = true;
    this.sviluppaSomministrazioni$completionDelegate = new Delegate<Prescrizione>() {
      public void execute(Prescrizione prescrizione) {
//      PhgUtils.log("FINE SVILUPPO SOMMINISTRAZIONI per " + prescrizione);
        sviluppaSomministrazioni$operazioneInCorso = false;
        if (completionDelegate != null) {
          completionDelegate.execute(prescrizione);
        }
      }
    };
    dao.findLastSomministrazioneByPrescrizione(prescrizione, new Delegate<Somministrazione>() {
      public void execute(Somministrazione ultimaSomministrazione) {
        Date dataUltimaSomministrazione = null;
        if (ultimaSomministrazione != null) {
          dataUltimaSomministrazione = ultimaSomministrazione.getData();
        }
        sviluppaSomministrazioniAPartireDa(prescrizione, dataUltimaSomministrazione);
      }
    });
  }
  
  private void sviluppaSomministrazioniAPartireDa(final Prescrizione prescrizione, Date dataUltimaSomministrazione) {
    
    final boolean inMemory = false;
    
    final Delegate<List<Somministrazione>> completionDelegate = new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> somministrazioni) {
        if (somministrazioni != null && somministrazioni.size() > 0) {
          PhonegapLog.log("EXECUTING REMOTE SAVE WITH " + somministrazioni.size() + " NEW SOMMINISTRAZIONI");
          saveRemoteSomministrazioni(somministrazioni, new Delegate<List<Somministrazione>>() {
            public void execute(List<Somministrazione> somministrazioniSincronizzate) {
              if (somministrazioniSincronizzate == null) {
                sviluppaSomministrazioni$completionDelegate.execute(prescrizione);
              } else {
                
                //TODO: SPOSTATO SOTTO
                /*
                final ObjectWrapper<Delegate<Iterator<Somministrazione>>> innerDelegate = new ObjectWrapper<Delegate<Iterator<Somministrazione>>>();
                innerDelegate.set(new Delegate<Iterator<Somministrazione>>() {
                    public void execute(final Iterator<Somministrazione> it) {
                      if (it.hasNext()) {
                        Somministrazione somministrazioneInRemoto = it.next();
                        if (somministrazioneInRemoto.getRemoteId() != null) {
                          dao.saveRemoteSomministrazione(somministrazioneInRemoto, new Delegate<Somministrazione>() {
                            public void execute(Somministrazione updateRemoteSomministrazione) {
                              PhgUtils.log("updated remote id somministrazione");
                              innerDelegate.get().execute(it);
                            }
                          });
                        } else {
                          innerDelegate.get().execute(it);
                        }
                      } else {
                        // finish processing
                        sviluppaSomministrazioni$completionDelegate.execute(prescrizione);
                      }
                    }
                  }
                );
                innerDelegate.get().execute(somministrazioniSincronizzate.iterator());
                */
                
                sviluppaSomministrazioni$completionDelegate.execute(prescrizione);
                
              }
            }
          });
          
        } else {
          sviluppaSomministrazioni$completionDelegate.execute(prescrizione);
        }
      }
    };

    if (dataUltimaSomministrazione == null) {
      iterateDosaggiForInsert(prescrizione, prescrizione.getDataInizio(), prescrizione.getDosaggi().iterator(), 
          new ArrayList<Somministrazione>(), inMemory, new Delegate<List<Somministrazione>>() {
        public void execute(List<Somministrazione> somministrazioni) {
          sviluppaRicorrenzaSuccessiva(prescrizione, prescrizione.getDataInizio(), somministrazioni, inMemory, completionDelegate);
        }
      });
    } else {
      sviluppaRicorrenzaSuccessiva(prescrizione, dataUltimaSomministrazione, new ArrayList<Somministrazione>(), inMemory, completionDelegate);
    }
  }
  
  private void sviluppaRicorrenzaSuccessiva(final Prescrizione prescrizione, Date dataUltimaSomministrazione,
      List<Somministrazione> somministrazioni, final boolean inMemory, final Delegate<List<Somministrazione>> completionDelegate) {
    final Date nextDataSomministrazione = CalendarUtil.copyDate(dataUltimaSomministrazione);
    if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_GIORNALIERA)) {
      int incremento = prescrizione.getValoreRicorrenza();
      CalendarUtil.addDaysToDate(nextDataSomministrazione, incremento);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_SETTIMANALE)) {
      int incremento = 7 * prescrizione.getValoreRicorrenza();
      CalendarUtil.addDaysToDate(nextDataSomministrazione, incremento);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_MENSILE)) {
      int incremento = prescrizione.getValoreRicorrenza();
      CalendarUtil.addMonthsToDate(nextDataSomministrazione, incremento);
    }
    Date dataLimiteSviluppoSomministrazioni = getDataLimiteSviluppoSomministrazioni(prescrizione);
    if (nextDataSomministrazione.after(dataLimiteSviluppoSomministrazioni)) {
      completionDelegate.execute(somministrazioni);
      return;
    }
    if (prescrizione.getActualDataFine() != null && nextDataSomministrazione.after(prescrizione.getActualDataFine())) {
      completionDelegate.execute(somministrazioni);
      return;
    }
    iterateDosaggiForInsert(prescrizione, nextDataSomministrazione, prescrizione.getDosaggi().iterator(), 
        somministrazioni, inMemory, new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> somministrazioni) {
        sviluppaRicorrenzaSuccessiva(prescrizione, nextDataSomministrazione, somministrazioni, inMemory, completionDelegate);
      }
    });
  }
  
  private void iterateDosaggiForInsert(final Prescrizione prescrizione, final Date dataSomministrazione, final Iterator<Dosaggio> it, 
      final List<Somministrazione> results, final boolean inMemory, final Delegate<List<Somministrazione>> completionDelegate) {
    if (it.hasNext()) {
      Dosaggio dosaggio = it.next();
      Somministrazione somministrazione = new SomministrazioneTx(prescrizione);
      
      if (Time.fromString(dosaggio.getOrario()) == null) {
        completionDelegate.execute(results);
      }
      
      Time.fromString(dosaggio.getOrario()).setInDate(dataSomministrazione);
      if (dataSomministrazione.before(new Date())) {
        iterateDosaggiForInsert(prescrizione, dataSomministrazione, it, results, inMemory, completionDelegate);
        return;
      }
      somministrazione.setData(CalendarUtil.copyDate(dataSomministrazione));
      somministrazione.setOrario(dosaggio.getOrario());
      Double qta = dosaggio.getQuantita();
      if (qta == null)
        qta = prescrizione.getQuantita();
      somministrazione.setQuantita(qta);
      somministrazione.setSchedulata();
      
      if (inMemory) {
        Date now = new Date();
        somministrazione.setId((int)now.getTime());
        results.add(somministrazione);
        iterateDosaggiForInsert(prescrizione, dataSomministrazione, it, results, inMemory, completionDelegate);
      } else {
        dao.saveSomministrazione(somministrazione, new Delegate<Somministrazione>() {
          public void execute(Somministrazione somministrazione) {
            saveCalEvent(somministrazione);
            results.add(somministrazione);
            iterateDosaggiForInsert(prescrizione, dataSomministrazione, it, results, inMemory, completionDelegate);
          }
        });
      }
      
    } else {
      completionDelegate.execute(results);
    }
  }
  
  private Date getDataLimiteSviluppoSomministrazioni(Prescrizione prescrizione) {
    Date dataLimiteSviluppoSomministrazioni = new Date();
    if (prescrizione.getValoreRicorrenza() == null || prescrizione.getValoreRicorrenza() <= 0) {
      throw new ServiceException("Date range not set");
    }
    if (prescrizione.getDataLimiteSviluppoSomministrazioni() != null) {
      return prescrizione.getDataLimiteSviluppoSomministrazioni();
    }
    if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_GIORNALIERA)) {
      int limite = 5 * prescrizione.getValoreRicorrenza();
      CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, limite);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_SETTIMANALE)) {
      int limite = 7 * 5 * prescrizione.getValoreRicorrenza();
      CalendarUtil.addDaysToDate(dataLimiteSviluppoSomministrazioni, limite);
    } else if (prescrizione.getTipoRicorrenza().equals(Prescrizione.TIPO_RICORRENZA_MENSILE)) {
      int limite = 5 * prescrizione.getValoreRicorrenza();
      CalendarUtil.addMonthsToDate(dataLimiteSviluppoSomministrazioni, limite);
    }
    return dataLimiteSviluppoSomministrazioni;
  }
  
  public void cancellaSomministrazioni(final Prescrizione prescrizione, final Delegate<Void> endDelegate) {
    dao.findSomministrazioniSchedulateByPrescrizione(prescrizione, new Delegate<List<Somministrazione>>() {
      public void execute(final List<Somministrazione> somministrazioni) {
        if (somministrazioni == null || somministrazioni.size() == 0) {
          endDelegate.execute(null);
          return;
        }
        iterateSomministrazioniForDelete(somministrazioni.iterator(), new Delegate<Void>() {
          public void execute(Void element) {
            PhonegapLog.log("EXECUTING REMOTE DELETE WITH " + somministrazioni.size() + " SOMMINISTRAZIONI");
            deleteRemoteSomministrazioni(somministrazioni, new Delegate<List<Somministrazione>>() {
              public void execute(List<Somministrazione> somministrazioni) {
                dao.deleteSomministrazioni(somministrazioni, endDelegate);
              }
            });
          }
        });
      }
    });
  }
  
  private void iterateSomministrazioniForDelete(Iterator<Somministrazione> it, Delegate<Void> endDelegate) {
    if (it.hasNext()) {
      Somministrazione somministrazione = it.next();
      deleteCalEvent(somministrazione);
      iterateSomministrazioniForDelete(it, endDelegate);
    } else {
      endDelegate.execute(null);
    }
  }
  
  public static boolean isScaduta(Somministrazione somministrazione) {
    Date NOW = new Date();
    Date datetimeSomministrazione = somministrazione.getData();
    if (Time.fromString(somministrazione.getOrario()) != null) {
      Time.fromString(somministrazione.getOrario()).setInDate(datetimeSomministrazione);
      return somministrazione.isSchedulata() && datetimeSomministrazione.before(NOW);
    } else {
      return false;
    }
  }
  
  public static String validatePrescrizione(Prescrizione prescrizione) {
    if (prescrizione.getNome() == null || prescrizione.getNome().trim().length() == 0) {
      return "Manca il nome del farmaco";
    }
    if (prescrizione.getDosaggi() == null) {
      return "Manca l'orario di assunzione";
    }
    for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
      if (dosaggio.getOrario() == null) {
        return "Orario di assunzione non impostato";
      }
    }
    if (prescrizione.isGstAvvisoRiordino()) {
      if (prescrizione.getQtaPerConfez() <= 0) {
        return "Quantità per confezione errata";
      }
    }
    return null;
  }
  
  public void checkDataConnectionAvailable(final Delegate<Boolean> delegate) {
    checkDataConnectionAvailable(true, delegate);
  }
  
  public void checkDataConnectionAvailable(final boolean showMsgDialog, final Delegate<Boolean> delegate) {
    if (isOnlineMode()) {
      AppClientFactory.IMPL.getRemoteFacade().checkConnection(new AsyncCallback<Boolean>() {
        public void onSuccess(Boolean result) {
          delegate.execute(true);
        }
        public void onFailure(Throwable caught) {
          if (showMsgDialog) {
            PhgDialogUtils.showMessageDialog(AppMessages.IMPL.MainController_checkConnectionIfOnlineMode_msg1());
          }
          delegate.execute(false);
        }
      });
    } else {
      delegate.execute(true);
    }
  }
  
  public void setOnlineMode(boolean onlineMode) {
    PhgUtils.setLocalStorageItem("onlineMode", ""+onlineMode);
  }
  
  public boolean isOnlineMode() {
    String value = PhgUtils.getLocalStorageItem("onlineMode");
    return ("true".equalsIgnoreCase(value));
  }
  
  public void setUseCalendar(boolean value) {
    PhgUtils.setLocalStorageItem("useCalendar", ""+value);
  }
  
  public boolean isUseCalendar() {
    String value = PhgUtils.getLocalStorageItem("useCalendar");
    if (value == null) {
      value = "true";
    }
    return ("true".equalsIgnoreCase(value));
  }
  
  public void setDevInfoIdInLocalStorage(String devInfoId) {
    PhgUtils.setLocalStorageItem("devInfoId", devInfoId);
  }

  public String getDevInfoIdFromLocalStorage() {
    return PhgUtils.getLocalStorageItem("devInfoId");
  }

  public void setAccountInLocalStorage(Account account) {
    AccountTx tx = (AccountTx)account;
    PhgUtils.setLocalStorageItem("account", JSONUtils.stringify(tx.asJS()));
  }
  
  public Account getAccountFromLocalStorage() {
    String accountJson = PhgUtils.getLocalStorageItem("account");
    if (accountJson != null && accountJson.contains("{")) {
      Account account = AccountTx.fromJS(JSONUtils.parse(accountJson));
      return account;
    }
    return null;
  }
  
  public void synchSomministrazioniInBackground() {
    dao.findSomministrazioniDaSincronizzare(new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> somministrazioniDaSincronizzare) {
        if (somministrazioniDaSincronizzare != null && somministrazioniDaSincronizzare.size() > 0) {
          PhgUtils.log("trovate " + somministrazioniDaSincronizzare.size() + " da sincronizzare");
          saveRemoteSomministrazioni(somministrazioniDaSincronizzare, new Delegate<List<Somministrazione>>() {
            public void execute(List<Somministrazione> somministrazioniSincronizzate) {
              if (somministrazioniSincronizzate != null) {
                PhgUtils.log("sincronizzate " + somministrazioniSincronizzate.size() + " in background");
              }
            }
          });
        }
      }
    });
  }
  
  private boolean isRemoteSomministrazione(Somministrazione somministrazione) {
    if (isOnlineMode()) {
      if (somministrazione.getPrescrizione().getTutor() != null) {
        return true;
      }
    }
    return false;
  }
  
  private void saveRemoteSomministrazione (Somministrazione somministrazione, final Delegate<Somministrazione> delegate) {
    List<Somministrazione> somministrazioni = new ArrayList<Somministrazione>();
    somministrazioni.add(somministrazione);
    saveRemoteSomministrazioni(somministrazioni, new Delegate<List<Somministrazione>>() {
      public void execute(List<Somministrazione> results) {
        Somministrazione result = (results != null && results.size() > 0) ? results.get(0) : null;
        delegate.execute(result);
      }
    });
  }
   
  //TODO: REVISIONE BACKGROUND TASKS
  private void saveRemoteSomministrazioni (List<Somministrazione> somministrazioni, final Delegate<List<Somministrazione>> completionDelegate) {
    final List<Somministrazione> somministrazioniDaSalvareInRemoto = new ArrayList<Somministrazione>();
    for (Somministrazione somministrazione : somministrazioni) {
      if (isRemoteSomministrazione(somministrazione)) {
        somministrazioniDaSalvareInRemoto.add(somministrazione);
      }
    }
    
    if (somministrazioniDaSalvareInRemoto.size() == 0) {
      completionDelegate.execute(somministrazioni);
    } else {
      
      PhgUtils.getCurrentLanguage(new Delegate<String>() {
        public void execute(final String language) {

          Account account = getAccountFromLocalStorage();
          String devInfoId = getDevInfoIdFromLocalStorage();

          List<RpcMap> somministrazioniDaSalvareInRemotoMap = new ArrayList<RpcMap>();
          for (Somministrazione somministrazioneDaSalvareInRemoto : somministrazioniDaSalvareInRemoto) {
            somministrazioneDaSalvareInRemoto.setLanguage(language);
            somministrazioniDaSalvareInRemotoMap.add(((SomministrazioneTx)somministrazioneDaSalvareInRemoto).toRpcMap());
          }
          RpcMap accountMap = ((AccountTx)account).toRpcMap();
          PhgUtils.log("before save remote somministrazioni");
          AppClientFactory.IMPL.getRemoteFacade().saveSomministrazioni(somministrazioniDaSalvareInRemotoMap, accountMap, devInfoId, new AsyncCallback<List<RpcMap>>() {
            public void onSuccess(List<RpcMap> somministrazioniMap) {
              PhgUtils.log("saved remote somministrazioni");
              if (somministrazioniMap == null) {
                completionDelegate.execute(null);
              } else {
                
                List<Somministrazione> somministrazioniSincronizzate = new ArrayList<Somministrazione>();
                for (RpcMap somministrazioneMap : somministrazioniMap) {
                  somministrazioniSincronizzate.add(new SomministrazioneTx().fromRpcMap(somministrazioneMap));
                }
                
                completionDelegate.execute(somministrazioniSincronizzate);
                
                final ObjectWrapper<Delegate<Iterator<Somministrazione>>> innerDelegate = new ObjectWrapper<Delegate<Iterator<Somministrazione>>>();
                innerDelegate.set(new Delegate<Iterator<Somministrazione>>() {
                    public void execute(final Iterator<Somministrazione> it) {
                      if (it.hasNext()) {
                        Somministrazione somministrazioneInRemoto = it.next();
                        if (somministrazioneInRemoto.getRemoteId() != null) {
                          dao.saveRemoteSomministrazione(somministrazioneInRemoto, new Delegate<Somministrazione>() {
                            public void execute(Somministrazione updateRemoteSomministrazione) {
                              PhgUtils.log("updated remote id somministrazione");
                              innerDelegate.get().execute(it);
                            }
                          });
                        } else {
                          innerDelegate.get().execute(it);
                        }
                      /*
                      } else {
                        // finish processing
                        sviluppaSomministrazioni$completionDelegate.execute(prescrizione);
                       */
                      }
                    }
                  }
                );
                innerDelegate.get().execute(somministrazioniSincronizzate.iterator());
                
              }
            }
            public void onFailure(Throwable caught) {
              
              // TODO: 11/07/2014 - UPDATE flag synchRequired = TRUE
              PhgUtils.log("REMOTE SAVE FAILURE - " + caught.getClass().getName() + " - " + caught.getMessage());
              //processFailure(null, caught);
              
              PhgUtils.log("FAILURE PROCESSING .1 - somministrazioniDaSalvareInRemoto.size = " + somministrazioniDaSalvareInRemoto.size());

              IterationUtil.create(somministrazioniDaSalvareInRemoto, 
                new ItemDelegate<Somministrazione>() {
                  public void handleItem(Somministrazione item, final IterationUtil<Somministrazione> iteration) {
                    PhgUtils.log("FAILURE PROCESSING .2");
                    SomministrazioneTx somministrazione = (SomministrazioneTx)item;
                    PhgUtils.log("FAILURE PROCESSING .3");
                    somministrazione.setNeedSynchronization(1);
                    PhgUtils.log("FAILURE PROCESSING .4 - somministrazione = " + somministrazione);
                    dao.updateNeedSynchronizationSomministrazione(somministrazione, new Delegate<SomministrazioneTx>() {
                      public void execute(SomministrazioneTx result) {
                        PhgUtils.log("FAILURE PROCESSING .5");
                        iteration.next();
                      }
                    });
                  }
                });
              
              completionDelegate.execute(null);
              
            }
          });
          
        }
      });
      

      /******** DA RIMUOVERE
      final ObjectWrapper<Delegate<Iterator<Somministrazione>>> innerDelegate = new ObjectWrapper<Delegate<Iterator<Somministrazione>>>();
      innerDelegate.set(new Delegate<Iterator<Somministrazione>>() {
          public void execute(final Iterator<Somministrazione> it) {
            if (it.hasNext()) {
              PhgUtils.getCurrentLanguage(new Delegate<String>() {
                public void execute(String language) {
                  it.next().setLanguage(language);
                  innerDelegate.get().execute(it);
                }
              });
            } else {
              
              
            }
          }
        }
      );
      innerDelegate.get().execute(somministrazioniDaSalvareInRemoto.iterator());
      ***********/
      

      
    }

  }
  
  private void deleteRemoteSomministrazioni (final List<Somministrazione> somministrazioni, final Delegate<List<Somministrazione>> delegate) {
    if (somministrazioni == null) {
      delegate.execute(somministrazioni);
    } else {
      List<Somministrazione> somministrazioiniDaCancellare = new ArrayList<Somministrazione>();
      for (Somministrazione somministrazione : somministrazioni) {
        if (somministrazione.getRemoteId() != null) {
          somministrazioiniDaCancellare.add(somministrazione);
        }
      }
      if (somministrazioiniDaCancellare.size() > 0) {
        
        List<RpcMap> somministrazioiniDaCancellareMap = new ArrayList<RpcMap>();
        for (Somministrazione somministrazioneDaCancellare : somministrazioiniDaCancellare) {
          somministrazioiniDaCancellareMap.add(((SomministrazioneTx)somministrazioneDaCancellare).toRpcMap());
        }
        
        AppClientFactory.IMPL.getRemoteFacade().deleteSomministrazioni(somministrazioiniDaCancellareMap, new AsyncCallback<Void>() {
          public void onSuccess(Void result) {
            delegate.execute(somministrazioni);
          }
          public void onFailure(Throwable caught) {
            delegate.execute(somministrazioni);
          }
        });
      } else {
        delegate.execute(somministrazioni);
      }
    }
  }
  
  @SuppressWarnings("unused")
  private void processFailure(String message, Throwable caught) {
    String popupTitle = "Alert";
    String popupMsg = "Failure";
    String logMsg = null;
    if (message != null) {
      popupMsg = message;
    } else if (caught != null) {
      logMsg = caught.getClass().getName()+" - "+caught.getMessage();
      if (caught instanceof InvocationException) {
//      popupMsg = "Maybe data connection is not active";
        popupMsg = null;
      } else {
        if (caught.getMessage() != null) {
          popupMsg = caught.getMessage();
        } else {
          String cls = caught.getClass().getName();
          cls = cls.substring(cls.lastIndexOf(".") + 1); 
          popupMsg = cls;
        }
      }
      popupTitle = "Error";
      caught.printStackTrace();
    }
    if (logMsg != null)
      PhgUtils.log(logMsg);
    if (popupMsg != null) {
      PhgDialogUtils.showMessageDialog(popupMsg, popupTitle, PhgDialogUtils.BUTTONS_OK);
    }
  }


  private void saveCalEvent (Somministrazione somministrazione) {
    CalendarEvent calEvent = createCalEvent(somministrazione.getPrescrizione(), somministrazione, false);
    PhonegapLog.log("creating " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    if (LocalNotificationsPlugin.isInstalled()) {
      LocalNotificationsPlugin.createEvent(createCalEvent(somministrazione.getPrescrizione(), somministrazione, true));
    }
    if (isUseCalendar()) {
      CalendarPlugin.createEvent(calEvent);
    }
  }
  
  private void deleteCalEvent (Somministrazione somministrazione) {
    CalendarEvent calEvent = createCalEvent(somministrazione.getPrescrizione(), somministrazione, false);
    PhonegapLog.log("deleting " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    if (LocalNotificationsPlugin.isInstalled()) {
      LocalNotificationsPlugin.deleteEvent(createCalEvent(somministrazione.getPrescrizione(), somministrazione, true));
    }
    if (isUseCalendar()) {
      CalendarPlugin.deleteEvent(calEvent);
    }
  }
  
  private CalendarEvent createCalEvent(Prescrizione prescrizione, Somministrazione somministrazione, boolean isNotification) {
    CalendarEvent calEvent = new CalendarEvent();
    
    if (somministrazione != null) {
      calEvent.setStartDate(somministrazione.getData());
    } else {
      calEvent.setStartDate(prescrizione.getDataInizio());
    }
    
    if (somministrazione != null) {
      calEvent.setEndDate(CalendarUtil.copyDate(somministrazione.getData()));
      Time endTime = Time.fromDate(calEvent.getEndDate());
      endTime.incMinutes(5).setInDate(calEvent.getEndDate());
    } else {
      if (prescrizione.getDataFine() != null) {
        calEvent.setEndDate(prescrizione.getDataFine());
      } else {
        calEvent.setEndDate(GwtUtils.getDate(31, 12, 2099));
      }
    }
    
    if (somministrazione != null) {
      calEvent.setTitle(prescrizione.getNome() + " at " + somministrazione.getOrario());
      if (MGWT.getOsDetection().isIOs()) {
        calEvent.setNotes("Tap here: therapy://open");
      } else {
        calEvent.setNotes("Keep the pill!");
      }
    }
    
    calEvent.setLocation("#"+prescrizione.getId());
    
    if (LocalNotificationsPlugin.isInstalled() && isNotification) {
      calEvent.setNotes("Tap here");
      if (somministrazione != null) {
        long notId = prescrizione.getId() * 100000 + somministrazione.getId();
        calEvent.setNotificationId("" + notId);
      }
    }
    
    return calEvent;
  }
  
  public void purgeNotificationIds(List<String> ids) {
    if (ids == null)
      return;
    for (final String notificationId : ids) {
      int len = notificationId.length();
      if (len > 5) {
        int idSomministrazione = Integer.parseInt(notificationId.substring(len - 5, len));
        PhgUtils.log("finding somministrazione with id " + idSomministrazione);
        dao.findSomministrazioneById(idSomministrazione, new Delegate<Somministrazione>() {
          public void execute(Somministrazione somministrazione) {
            if (somministrazione == null) {
              PhgUtils.log("deleting notification with id " + notificationId);
              CalendarEvent calEvent = new CalendarEvent();
              calEvent.setNotificationId(notificationId);
              LocalNotificationsPlugin.deleteEvent(calEvent);
            }
          }
        });
      }
    }
  }

  public void findCalEvents (Prescrizione prescrizione) {
    CalendarEvent calEvent = createCalEvent(prescrizione, null, false);
    PhonegapLog.log("finding " + calEvent);
    if (OsDetectionUtils.isDesktop())
      return;
    CalendarPlugin.findEvent(calEvent, new Delegate<List<CalendarEvent>>() {
      public void execute(List<CalendarEvent> events) {
        for (CalendarEvent event : events) {
          PhonegapLog.log("found " + event);
        }
      }
    });
  }
  
}
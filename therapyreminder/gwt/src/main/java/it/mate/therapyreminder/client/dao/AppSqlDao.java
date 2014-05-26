package it.mate.therapyreminder.client.dao;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapLog;
import it.mate.phgcommons.client.utils.WebSQLDao;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.DosaggioTx;
import it.mate.therapyreminder.shared.model.impl.PrescrizioneTx;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;
import it.mate.therapyreminder.shared.model.impl.UdMTx;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppSqlDao extends WebSQLDao {
  
  private final static boolean DROP_TABLES_ON_OPEN_DATABASE = false;
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
  private final static String UDM_FIELDS = "codice, descrizione, sequenza"; 
  
  private final static String PRESCRIZIONI_FIELDS_0 = 
      "nome, dataInizio, dataFine, " + 
      "quantita, codUdM, idComposizione, " +
      "tipoRicorrenza, valoreRicorrenza," +
      "tipoRicorrenzaOraria, intervalloOrario";

  private final static String PRESCRIZIONI_FIELDS_1 = 
      "flgGstAvvisoRiordino, qtaPerConfez, qtaPerAvviso, qtaRimanente, ultimoAvvisoRiordino ";
  
  private final static String PRESCRIZIONI_FIELDS = PRESCRIZIONI_FIELDS_0 + ", " + PRESCRIZIONI_FIELDS_1;
      
  private final static String DOSAGGI_FIELDS = "idPrescrizione, quantita, orario";
  
  private final static String SOMMINISTRAZIONI_FIELDS = "idPrescrizione, data, quantita, orario, stato";
  
  public AppSqlDao() {
    super("TherapiesDB", ESTIMATED_SIZE, migrationCallbacks, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapLog.log("created db therapies");
      }
    }, new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (DROP_TABLES_ON_OPEN_DATABASE) {
          dropDBImpl(tr);
        }
      }
    });
  }
  
  private static void dropDBImpl(SQLTransaction tr) {
    PhonegapLog.log("dropping all tables");
    tr.doExecuteSql("DROP TABLE IF EXISTS version");
    tr.doExecuteSql("DROP TABLE IF EXISTS udm");
    tr.doExecuteSql("DROP TABLE IF EXISTS prescrizioni");
    tr.doExecuteSql("DROP TABLE IF EXISTS dosaggi");
    tr.doExecuteSql("DROP TABLE IF EXISTS somministrazioni");
  }
  
  public void dropDB(final Delegate<Void> delegate) {
    PhonegapLog.log("resetting db");
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        dropDBImpl(tr);
        delegate.execute(null);
      }
    });
  }
  
  private final static MigratorCallback MIGRATION_CALLBACK_0 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db therapies to version " + number);

      /*
       * NOTA BENE: se cambia il default delle udm occorre cambiare anche:
       * VEDI ANCHE PrescrizioneTx.codUdM
       */
      PhonegapLog.log("creating table udm");
      tr.doExecuteSql("CREATE TABLE udm (" + UDM_FIELDS + " )");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('C', 'Compress/a/e', 10)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('F', 'Fial/a/e', 20)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('S', 'Suppost/a/e', 30)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('O', 'Ovul/o/i', 40)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('G', 'Gocc/ia/e', 50)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('B', 'Bustin/a/e', 60)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('G', 'Garz/a/e', 70)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('L', 'Flacon/e/i', 80)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('P', 'Capsul/a/e', 90)");
      tr.doExecuteSql("INSERT INTO udm (" + UDM_FIELDS + ") VALUES ('T', 'Confett/o/i', 100)");
      
      PhonegapLog.log("creating table prescrizioni");
      tr.doExecuteSql("CREATE TABLE prescrizioni (id "+SERIAL_ID+", " + PRESCRIZIONI_FIELDS + " )");

      PhonegapLog.log("creating table dosaggi");
      tr.doExecuteSql("CREATE TABLE dosaggi (" + DOSAGGI_FIELDS + " )");

      PhonegapLog.log("creating table somministrazioni");
      tr.doExecuteSql("CREATE TABLE somministrazioni (id "+SERIAL_ID+", " + SOMMINISTRAZIONI_FIELDS + " )");

      /* TEST MIGRATIONS
      tr.doExecuteSql("CREATE TABLE therapies (id "+SERIAL_ID+", name)");
      tr.doExecuteSql("INSERT INTO therapies (name) VALUES ('prova1')");
      */
      
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_1 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db therapies to version " + number);
      
      PhonegapLog.log("altering table prescrizioni");
      tr.doExecuteSql("ALTER TABLE prescrizioni ADD COLUMN flgGstAvvisoRiordino");
      tr.doExecuteSql("ALTER TABLE prescrizioni ADD COLUMN qtaPerConfez");
      tr.doExecuteSql("ALTER TABLE prescrizioni ADD COLUMN qtaPerAvviso");
      tr.doExecuteSql("ALTER TABLE prescrizioni ADD COLUMN qtaRimanente");
      tr.doExecuteSql("ALTER TABLE prescrizioni ADD COLUMN ultimoAvvisoRiordino");
      
    }
  };
  
  /** PROVA MIGRATION >> NON CANCELLARE, MI SERVIRA' QUANDO AVRO' INIZIATO A FARE RELEASE!
  
  private final static MigratorCallback MIGRATION_CALLBACK_2 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db therapies to version " + number);
      tr.doExecuteSql("ALTER TABLE therapies ADD COLUMN created");
      tr.doExecuteSql("INSERT INTO therapies (name, created) VALUES ('prova2', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_3 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db therapies to version " + number);
      tr.doExecuteSql("DELETE FROM therapies WHERE id = 2");
      tr.doExecuteSql("INSERT INTO therapies (name, created) VALUES ('prova3', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_4 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db therapies to version " + number);
      tr.doExecuteSql("ALTER TABLE therapies ADD COLUMN createdTm");
      Date NOW = new Date();
      tr.doExecuteSql("INSERT INTO therapies (name, created, createdTm) VALUES (?, ?, ?)", 
          new Object[] {"prova4", 2014, NOW.getTime()});
    }
  };
  */
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0 ,MIGRATION_CALLBACK_1 /* ,MIGRATION_CALLBACK_2 ,MIGRATION_CALLBACK_3 ,MIGRATION_CALLBACK_4 */ 
  };
  
  public void findAllUdM(final Delegate<List<UdM>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT " + UDM_FIELDS + " FROM udm ORDER BY sequenza", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<UdM> results = new ArrayList<UdM>();
            if (rs.getRows().getLength() > 0) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                UdM item = new UdMTx();
                item.setCodice(rs.getRows().getValueString(it, "codice"));
                item.setDescrizione(rs.getRows().getValueString(it, "descrizione"));
                item.setSequenza(rs.getRows().getValueInt(it, "sequenza"));
                results.add(item);
              }
            }
            delegate.execute(results);
          }
        });
      }
    });
  }
  
  public void findAllPrescrizioni(final Delegate<List<Prescrizione>> delegate) {
    /* NON FUNZIONA CON LA READ TRANSACTION!
    db.doReadTransaction(new SQLTransactionCallback() {
     */
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + PRESCRIZIONI_FIELDS + " FROM prescrizioni", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Prescrizione> results = new ArrayList<Prescrizione>();
            if (rs.getRows().getLength() > 0) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                results.add(flushRSToPrescrizione(rs, it));
              }
            }
            iteratePrescrizioniForRead(results.iterator(), tr, delegate, results);
          }
        });
      }
    });
  }
  
  public void findAllPrescrizioniAttive(final Date dataRiferimento, final Delegate<List<Prescrizione>> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String sql = "SELECT id, " + PRESCRIZIONI_FIELDS + " FROM prescrizioni";
        sql += " WHERE dataFine IS NULL OR dataFine >= ?";
        tr.doExecuteSql(sql, new Object[]{dateAsLong(dataRiferimento)}, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Prescrizione> results = new ArrayList<Prescrizione>();
            if (rs.getRows().getLength() > 0) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                results.add(flushRSToPrescrizione(rs, it));
              }
            }
            iteratePrescrizioniForRead(results.iterator(), tr, delegate, results);
          }
        });
      }
    });
  }
  
  public void findPrescrizioneById(final Integer id, final Delegate<Prescrizione> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String sql = "SELECT id, " + PRESCRIZIONI_FIELDS + " FROM prescrizioni";
        sql += " WHERE id = ?";
        tr.doExecuteSql(sql, new Object[]{id}, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Prescrizione> results = new ArrayList<Prescrizione>();
            if (rs.getRows().getLength() == 1) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                results.add(flushRSToPrescrizione(rs, it));
              }
            } else if (rs.getRows().getLength() <= 0) {
              throw new DaoException("Not found");
            } else {
              throw new DaoException("Data integrity error");
            }
            iteratePrescrizioniForRead(results.iterator(), tr, new Delegate<List<Prescrizione>>() {
              public void execute(List<Prescrizione> results) {
                delegate.execute(results.get(0));
              }
            }, results);
          }
        });
      }
    });
  }
  
  private Prescrizione flushRSToPrescrizione(SQLResultSet rs, int it) {
    SQLResultSetRowList rows = rs.getRows();
    Prescrizione prescrizione = new PrescrizioneTx();
    prescrizione.setId(rows.getValueInt(it, "id"));
    prescrizione.setNome(rows.getValueString(it, "nome"));
    prescrizione.setDataInizio(longAsDate(rows.getValueLong(it, "dataInizio")));
    prescrizione.setDataFine(longAsDate(rows.getValueLong(it, "dataFine")));
    prescrizione.setQuantita(rows.getValueDouble(it, "quantita"));
    prescrizione.setTipoRicorrenza(rows.getValueString(it, "tipoRicorrenza"));
    prescrizione.setCodUdM(rows.getValueString(it, "codUdM"));
    prescrizione.setValoreRicorrenza(rows.getValueInt(it, "valoreRicorrenza"));
    prescrizione.setTipoRicorrenzaOraria(rows.getValueString(it, "tipoRicorrenzaOraria"));
    prescrizione.setIntervalloOrario(rows.getValueInt(it, "intervalloOrario"));
    prescrizione.setFlgGstAvvisoRiordino(rows.getValueInt(it, "flgGstAvvisoRiordino"));
    prescrizione.setQtaPerConfez(rows.getValueDouble(it, "qtaPerConfez"));
    prescrizione.setQtaPerAvviso(rows.getValueDouble(it, "qtaPerAvviso"));
    prescrizione.setQtaRimanente(rows.getValueDouble(it, "qtaRimanente"));
    prescrizione.setUltimoAvvisoRiordino(longAsDate(rows.getValueLong(it, "ultimoAvvisoRiordino")));
    return prescrizione;
  }
  
  private void iteratePrescrizioniForRead(final Iterator<Prescrizione> it, SQLTransaction tr, final Delegate<List<Prescrizione>> delegate, final List<Prescrizione> results) {
    if (it.hasNext()) {
      final Prescrizione prescrizione = it.next();
      tr.doExecuteSql("SELECT " + DOSAGGI_FIELDS + " FROM dosaggi WHERE idPrescrizione = ?", new Object[] {prescrizione.getId()}, 
          new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              SQLResultSetRowList rows = rs.getRows();
              if (rows.getLength() > 0) {
                for (int it = 0; it < rows.getLength(); it++) {
                  Dosaggio dosaggio = new DosaggioTx(prescrizione);
                  dosaggio.setQuantita(rows.getValueDouble(it, "quantita"));
                  dosaggio.setOrario(rows.getValueString(it, "orario"));
                  prescrizione.getDosaggi().add(dosaggio);
                }
              }
              iteratePrescrizioniForRead(it, tr, delegate, results);
            }
          });
    } else {
      delegate.execute(results);
    }
  }
  
  public void savePrescrizione(final Prescrizione prescrizione, final Delegate<Prescrizione> delegate) {
//  PhonegapLog.log("saving " + prescrizione);
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (prescrizione.getId() == null) {
          tr.doExecuteSql("INSERT INTO prescrizioni (" + PRESCRIZIONI_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
              new Object[] {
                prescrizione.getNome(), 
                dateAsLong(prescrizione.getDataInizio()), 
                dateAsLong(prescrizione.getDataFine()), 
                prescrizione.getQuantita(), 
                prescrizione.getCodUdM(),
                prescrizione.getIdComposizione(), 
                prescrizione.getTipoRicorrenza(), 
                prescrizione.getValoreRicorrenza(),
                prescrizione.getTipoRicorrenzaOraria(), 
                prescrizione.getIntervalloOrario(),
                //////////////////////////////////////
                prescrizione.getFlgGstAvvisoRiordino(),
                prescrizione.getQtaPerConfez(),
                prescrizione.getQtaPerAvviso(),
                prescrizione.getQtaRimanente(),
                dateAsLong(prescrizione.getUltimoAvvisoRiordino())
                //////////////////////////////////////
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  prescrizione.setId(rs.getInsertId());
                  if (prescrizione.getDosaggi() != null) {
                    for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
                      tr.doExecuteSql("INSERT INTO dosaggi (" + DOSAGGI_FIELDS + ") VALUES (?, ?, ?) ", 
                          new Object[] {dosaggio.getPrescrizione().getId(), dosaggio.getQuantita(), dosaggio.getOrario()});
                    }
                  }
                  PhonegapLog.log("Inserted " + prescrizione);
                  delegate.execute(prescrizione);
                }
              });
        } else {
          String sql = "UPDATE prescrizioni SET ";
          sql += " nome = ?";
          sql += " ,dataInizio = ?";
          sql += " ,dataFine = ?";
          sql += " ,quantita = ?";
          sql += " ,codUdM = ?";
          sql += " ,idComposizione = ?";
          sql += " ,tipoRicorrenza = ?";
          sql += " ,valoreRicorrenza = ?";
          sql += " ,tipoRicorrenzaOraria = ?";
          sql += " ,intervalloOrario = ?";
          
          sql += " ,flgGstAvvisoRiordino = ?";
          sql += " ,qtaPerConfez = ?";
          sql += " ,qtaPerAvviso = ?";
          sql += " ,qtaRimanente = ?";
          sql += " ,ultimoAvvisoRiordino = ?";
          
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {

              prescrizione.getNome(), 
              dateAsLong(prescrizione.getDataInizio()), 
              dateAsLong(prescrizione.getDataFine()), 
              prescrizione.getQuantita(), 
              prescrizione.getCodUdM(),
              prescrizione.getIdComposizione(), 
              prescrizione.getTipoRicorrenza(), 
              prescrizione.getValoreRicorrenza(),
              prescrizione.getTipoRicorrenzaOraria(), 
              prescrizione.getIntervalloOrario(), 

              prescrizione.getFlgGstAvvisoRiordino(),
              prescrizione.getQtaPerConfez(),
              prescrizione.getQtaPerAvviso(),
              prescrizione.getQtaRimanente(),
              dateAsLong(prescrizione.getUltimoAvvisoRiordino())
              
              , prescrizione.getId()
              
            }, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              PhonegapLog.log("Updated " + prescrizione);
              tr.doExecuteSql("DELETE FROM dosaggi WHERE idPrescrizione = ? ", 
                  new Object[] {prescrizione.getId()},
                  new SQLStatementCallback() {
                    public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                      if (prescrizione.getDosaggi() != null) {
                        for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
                          tr.doExecuteSql("INSERT INTO dosaggi (" + DOSAGGI_FIELDS + ") VALUES (?, ?, ?) ", 
                              new Object[] {dosaggio.getPrescrizione().getId(), dosaggio.getQuantita(), dosaggio.getOrario()});
                        }
                      }
                    }
                  }
              );
              
              delegate.execute(prescrizione);
            }
          });
        }
      }
    });
  }
  
  public void deletePrescrizioni(final List<Prescrizione> prescrizioni, final Delegate<Void> delegate) {
    if (prescrizioni == null || prescrizioni.size() == 0) {
      delegate.execute(null);
    }
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(final SQLTransaction tr) {
        iteratePrescrizioniForDelete(prescrizioni.iterator(), tr, delegate);
      }
    });
  }
  
  private void iteratePrescrizioniForDelete(final Iterator<Prescrizione> it, SQLTransaction tr, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      final Prescrizione prescrizione = it.next();
      PhonegapLog.log("deleting " + prescrizione);
      tr.doExecuteSql("DELETE FROM prescrizioni WHERE id = ?", new Object[] {prescrizione.getId()}, new SQLStatementCallback() {
        public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
          tr.doExecuteSql("DELETE FROM dosaggi WHERE idPrescrizione = ?", new Object[] {prescrizione.getId()},
            new SQLStatementCallback() {
              public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                iteratePrescrizioniForDelete(it, tr, delegate);
              }
            }
          );
        }
      });
    } else {
      delegate.execute(null);
    }
  }
  
  public void deleteSomministrazioni(final List<Somministrazione> somministrazioni, final Delegate<Void> delegate) {
    if (somministrazioni == null || somministrazioni.size() == 0) {
      delegate.execute(null);
    }
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(final SQLTransaction tr) {
        iterateSomministrazioniForDelete(somministrazioni.iterator(), tr, delegate);
      }
    });
  }
  
  private void iterateSomministrazioniForDelete(final Iterator<Somministrazione> it, SQLTransaction tr, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      final Somministrazione somministrazione = it.next();
      tr.doExecuteSql("DELETE FROM somministrazioni WHERE id = ?", new Object[] {somministrazione.getId()}, new SQLStatementCallback() {
        public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
          PhonegapLog.log("Deleted " + somministrazione);
          iterateSomministrazioniForDelete(it, tr, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }
  
  private Somministrazione flushRSToSomministrazione(SQLResultSet rs, int it, Prescrizione prescrizione) {
    Somministrazione result = new SomministrazioneTx(prescrizione);
    result.setId(rs.getRows().getValueInt(it, "id"));
    result.setData(longAsDate(rs.getRows().getValueLong(it, "data")));
    result.setQuantita(rs.getRows().getValueDouble(it, "quantita"));
    result.setOrario(rs.getRows().getValueString(it, "orario"));
    result.setStato(rs.getRows().getValueInt(it, "stato"));
    return result;
  }
  
  public void findSomministrazioniScadute(final Date dataRiferimento, final Delegate<List<Somministrazione>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String sql = "SELECT id, " + SOMMINISTRAZIONI_FIELDS + " FROM somministrazioni";
        sql += " WHERE stato = ? AND data <= ?";
        sql += " ORDER BY data";
        tr.doExecuteSql(sql, new Object[] {Somministrazione.STATO_SCHEDULATA, dateAsLong(dataRiferimento)}, 
          new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            if (rs.getRows().getLength() > 0) {
              new RSToSomministrazioniIterator(rs, delegate);
            } else {
              delegate.execute(null);
            }
          }
        });
      }
    });
  }
  
  public void findSomministrazioniNonEseguite(final Delegate<List<Somministrazione>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String sql = "SELECT id, " + SOMMINISTRAZIONI_FIELDS + " FROM somministrazioni";
        sql += " WHERE stato = ?";
        sql += " ORDER BY data";
        tr.doExecuteSql(sql, new Object[] {Somministrazione.STATO_SCHEDULATA}, 
          new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            if (rs.getRows().getLength() > 0) {
              new RSToSomministrazioniIterator(rs, delegate);
            }
          }
        });
      }
    });
  }
  
  protected class RSToSomministrazioniIterator {
    SQLResultSet rs;
    List<Somministrazione> results = new ArrayList<Somministrazione>();
    Delegate<List<Somministrazione>> delegate;
    Map<Integer, Prescrizione> prescrizioniMap = new HashMap<Integer, Prescrizione>();
    protected RSToSomministrazioniIterator(SQLResultSet rs, Delegate<List<Somministrazione>> delegate) {
      this.rs = rs;
      this.delegate = delegate;
      iterate(0);
    }
    private void iterate(final int it) {
      if (it < rs.getRows().getLength()) {
        Integer idPrescrizione = rs.getRows().getValueInt(it, "idPrescrizione");
        Prescrizione prescrizione = null;
        if (prescrizioniMap.containsKey(idPrescrizione)) {
          prescrizione = prescrizioniMap.get(idPrescrizione);
          results.add(flushRSToSomministrazione(rs, it, prescrizione));
          iterate(it + 1);
        } else {
          findPrescrizioneById(idPrescrizione, new Delegate<Prescrizione>() {
            public void execute(Prescrizione prescrizione) {
              results.add(flushRSToSomministrazione(rs, it, prescrizione));
              iterate(it + 1);
            }
          });
        }
      } else {
        delegate.execute(results);
      }
    }
  }

  public void findSomministrazioniSchedulateByPrescrizione(final Prescrizione prescrizione, final Delegate<List<Somministrazione>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String sql = "SELECT id, " + SOMMINISTRAZIONI_FIELDS + " FROM somministrazioni";
        sql += " WHERE idPrescrizione = ? AND stato = ?";
        sql += " ORDER BY data";
        tr.doExecuteSql(sql, new Object[] {prescrizione.getId(), Somministrazione.STATO_SCHEDULATA}, 
          new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Somministrazione> results = new ArrayList<Somministrazione>();
            if (rs.getRows().getLength() > 0) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                results.add(flushRSToSomministrazione(rs, it, prescrizione));
              }
            }
            delegate.execute(results);
          }
        });
      }
    });
  }

  public void findLastSomministrazioneByPrescrizione(final Prescrizione prescrizione, final Delegate<Somministrazione> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String sql = "SELECT id, " + SOMMINISTRAZIONI_FIELDS + " FROM somministrazioni";
        sql += " WHERE idPrescrizione = ?";
        sql += " ORDER BY data DESC";
        tr.doExecuteSql(sql, new Object[] {prescrizione.getId()}, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            if (rs.getRows().getLength() > 0) {
              delegate.execute(flushRSToSomministrazione(rs, 0, prescrizione));
            } else {
              delegate.execute(null);
            }
          }
        });
      }
    });
  }
  
  public void saveSomministrazione(final Somministrazione somministrazione, final Delegate<Somministrazione> delegate) {
    //PhonegapLog.log("saving " + somministrazione);
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (somministrazione.getId() == null) {
          tr.doExecuteSql("INSERT INTO somministrazioni (" + SOMMINISTRAZIONI_FIELDS + ") VALUES (?, ?, ?, ?, ?)", 
              new Object[] {
                somministrazione.getPrescrizione().getId(), 
                dateAsLong(somministrazione.getData()),
                somministrazione.getQuantita(), 
                somministrazione.getOrario(),
                somministrazione.getStato()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  somministrazione.setId(rs.getInsertId());
                  PhonegapLog.log("Inserted " + somministrazione);
                  delegate.execute(somministrazione);
                }
              });
        } else {
          String sql = "UPDATE somministrazioni SET ";
          sql += "  idPrescrizione = ?";
          sql += " ,data = ?";
          sql += " ,quantita = ?";
          sql += " ,orario = ?";
          sql += " ,stato = ?";
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              somministrazione.getPrescrizione().getId(), 
              dateAsLong(somministrazione.getData()),
              somministrazione.getQuantita(), 
              somministrazione.getOrario(),
              somministrazione.getStato()
              , somministrazione.getId()
            }, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              PhonegapLog.log("Updated " + somministrazione);
              delegate.execute(somministrazione);
            }
          });
        }
      }
    });
  }
  
}

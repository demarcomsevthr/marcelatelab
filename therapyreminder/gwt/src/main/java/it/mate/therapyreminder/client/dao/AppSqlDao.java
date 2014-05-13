package it.mate.therapyreminder.client.dao;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapUtils;
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
import java.util.Iterator;
import java.util.List;

public class AppSqlDao extends WebSQLDao {
  
  private final static boolean DROP_TABLES_ON_OPEN_DATABASE = false;
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
  private final static String UDM_FIELDS = "codice, descrizione, sequenza"; 
  
  private final static String PRESCRIZIONI_FIELDS = "nome, dataInizio, dataFine, " + 
      "quantita, codUdM, idComposizione, tipoRicorrenza, valoreRicorrenza," +
      "tipoRicorrenzaOraria, intervalloOrario";
  
  private final static String DOSAGGI_FIELDS = "idPrescrizione, quantita, orario";
  
  private final static String SOMMINISTRAZIONI_FIELDS = "idPrescrizione, data, quantita, orario, stato";
  
  public AppSqlDao() {
    super("TherapiesDB", ESTIMATED_SIZE, migrationCallbacks, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapUtils.log("created db therapies");
      }
    }, new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        //TODO: ATTENZIONE QUESTA CALLBACK SERVE SOLO PER IL DEBUG LOCALE, RIMUOVER IN RELEASE
        if (DROP_TABLES_ON_OPEN_DATABASE) {
          dropDBImpl(tr);
        }
      }
    });
  }
  
  private static void dropDBImpl(SQLTransaction tr) {
    PhonegapUtils.log("dropping all tables");
    tr.doExecuteSql("DROP TABLE IF EXISTS version");
    tr.doExecuteSql("DROP TABLE IF EXISTS udm");
    tr.doExecuteSql("DROP TABLE IF EXISTS prescrizioni");
    tr.doExecuteSql("DROP TABLE IF EXISTS dosaggi");
    tr.doExecuteSql("DROP TABLE IF EXISTS somministrazioni");
  }
  
  public void dropDB(final Delegate<Void> delegate) {
    PhonegapUtils.log("resetting db");
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        dropDBImpl(tr);
        delegate.execute(null);
      }
    });
  }
  
  private final static MigratorCallback MIGRATION_CALLBACK_0 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapUtils.log("updating db therapies to version " + number);

      /*
       * NOTA BENE: se cambia il default delle udm occorre cambiare anche:
       * VEDI ANCHE PrescrizioneTx.codUdM
       */
      PhonegapUtils.log("creating table udm");
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
      
      PhonegapUtils.log("creating table prescrizioni");
      tr.doExecuteSql("CREATE TABLE prescrizioni (id "+SERIAL_ID+", " + PRESCRIZIONI_FIELDS + " )");

      PhonegapUtils.log("creating table dosaggi");
      tr.doExecuteSql("CREATE TABLE dosaggi (" + DOSAGGI_FIELDS + " )");

      PhonegapUtils.log("creating table somministrazioni");
      tr.doExecuteSql("CREATE TABLE somministrazioni (id "+SERIAL_ID+", " + SOMMINISTRAZIONI_FIELDS + " )");

      /* TEST MIGRATIONS
      tr.doExecuteSql("CREATE TABLE therapies (id "+SERIAL_ID+", name)");
      tr.doExecuteSql("INSERT INTO therapies (name) VALUES ('prova1')");
      */
      
    }
  };
  
  /** PROVA MIGRATION >> NON CANCELLARE, MI SERVIRA' QUANDO AVRO' INIZIATO A FARE RELEASE!
  
  private final static MigratorCallback MIGRATION_CALLBACK_1 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapUtils.log("updating db therapies to version " + number);
      tr.doExecuteSql("CREATE TABLE drugs (id "+SERIAL_ID+", name, created)");
      tr.doExecuteSql("INSERT INTO drugs (name, created) VALUES ('prova', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_2 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapUtils.log("updating db therapies to version " + number);
      tr.doExecuteSql("ALTER TABLE therapies ADD COLUMN created");
      tr.doExecuteSql("INSERT INTO therapies (name, created) VALUES ('prova2', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_3 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapUtils.log("updating db therapies to version " + number);
      tr.doExecuteSql("DELETE FROM therapies WHERE id = 2");
      tr.doExecuteSql("INSERT INTO therapies (name, created) VALUES ('prova3', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_4 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapUtils.log("updating db therapies to version " + number);
      tr.doExecuteSql("ALTER TABLE therapies ADD COLUMN createdTm");
      Date NOW = new Date();
      tr.doExecuteSql("INSERT INTO therapies (name, created, createdTm) VALUES (?, ?, ?)", 
          new Object[] {"prova4", 2014, NOW.getTime()});
    }
  };
  */
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0 /* ,MIGRATION_CALLBACK_1 ,MIGRATION_CALLBACK_2 ,MIGRATION_CALLBACK_3 ,MIGRATION_CALLBACK_4 */ 
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
    /* NON FUNZIONA BENE LA READ TRANSACTION
    db.doReadTransaction(new SQLTransactionCallback() {
     */
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + PRESCRIZIONI_FIELDS + " FROM prescrizioni", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Prescrizione> results = new ArrayList<Prescrizione>();
            SQLResultSetRowList rows = rs.getRows();
            if (rows.getLength() > 0) {
              for (int it = 0; it < rows.getLength(); it++) {
                final Prescrizione prescrizione = new PrescrizioneTx();
                prescrizione.setId(rows.getValueInt(it, "id"));
                prescrizione.setNome(rows.getValueString(it, "nome"));
                prescrizione.setDataInizio(new Date(rows.getValueLong(it, "dataInizio")));
                prescrizione.setQuantita(rows.getValueDouble(it, "quantita"));
                prescrizione.setTipoRicorrenza(rows.getValueString(it, "tipoRicorrenza"));
                prescrizione.setCodUdM(rows.getValueString(it, "codUdM"));
                prescrizione.setValoreRicorrenza(rows.getValueInt(it, "valoreRicorrenza"));
                prescrizione.setTipoRicorrenzaOraria(rows.getValueString(it, "tipoRicorrenzaOraria"));
                prescrizione.setIntervalloOrario(rows.getValueInt(it, "intervalloOrario"));
//              item.setOrari(rows.getValueString(it, "orari"));
                results.add(prescrizione);
              }
            }
            iteratePrescrizioniForRead(results.iterator(), tr, delegate, results);
          }
        });
      }
    });
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
    PhonegapUtils.log("saving " + prescrizione);
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (prescrizione.getId() == null) {
          tr.doExecuteSql("INSERT INTO prescrizioni (" + PRESCRIZIONI_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
              new Object[] {
                prescrizione.getNome(), 
                dateAsLong(prescrizione.getDataInizio()), dateAsLong(prescrizione.getDataFine()), 
                prescrizione.getQuantita(), prescrizione.getCodUdM(),
                prescrizione.getIdComposizione(), 
                prescrizione.getTipoRicorrenza(), prescrizione.getValoreRicorrenza(),
                prescrizione.getTipoRicorrenzaOraria(), prescrizione.getIntervalloOrario() /* ,pr.getOrari() */
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  prescrizione.setId(rs.getInsertId());
                  if (prescrizione.getDosaggi() != null) {
                    for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
                      tr.doExecuteSql("INSERT INTO dosaggi (" + DOSAGGI_FIELDS + ") VALUES (?, ?, ?) ", 
                          new Object[] {dosaggio.getPrescrizione().getId(), dosaggio.getQuantita(), dosaggio.getOrario()});
                    }
                  }
                  PhonegapUtils.log("Inserted " + prescrizione);
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
          /* sql += " ,orari = ?"; */
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              prescrizione.getNome(), 
              dateAsLong(prescrizione.getDataInizio()), dateAsLong(prescrizione.getDataFine()), 
              prescrizione.getQuantita(), prescrizione.getCodUdM(),
              prescrizione.getIdComposizione(), 
              prescrizione.getTipoRicorrenza(), prescrizione.getValoreRicorrenza(),
              prescrizione.getTipoRicorrenzaOraria(), prescrizione.getIntervalloOrario() /* ,pr.getOrari() */
              , prescrizione.getId()
            }, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              
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
      PhonegapUtils.log("deleting " + prescrizione);
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
      PhonegapUtils.log("deleting " + somministrazione);
      tr.doExecuteSql("DELETE FROM somministrazioni WHERE id = ?", new Object[] {somministrazione.getId()}, new SQLStatementCallback() {
        public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
          iterateSomministrazioniForDelete(it, tr, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }
  
  private Somministrazione flushSomministrazione(SQLResultSet rs, int it, Prescrizione prescrizione) {
    Somministrazione result = new SomministrazioneTx(prescrizione);
    result.setId(rs.getRows().getValueInt(it, "id"));
    result.setData(new Date(rs.getRows().getValueLong(it, "data")));
    result.setQuantita(rs.getRows().getValueDouble(it, "quantita"));
    result.setOrario(rs.getRows().getValueString(it, "orario"));
    result.setStato(rs.getRows().getValueInt(it, "stato"));
    return result;
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
                results.add(flushSomministrazione(rs, it, prescrizione));
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
              delegate.execute(flushSomministrazione(rs, 0, prescrizione));
            } else {
              delegate.execute(null);
            }
          }
        });
      }
    });
  }
  
  public void saveSomministrazione(final Somministrazione somministrazione, final Delegate<Somministrazione> delegate) {
    PhonegapUtils.log("saving " + somministrazione);
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
              delegate.execute(somministrazione);
            }
          });
        }
      }
    });
  }
  
}

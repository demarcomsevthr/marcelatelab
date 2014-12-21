package it.mate.protons.client.logic;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.PhonegapLog;
import it.mate.phgcommons.client.utils.WebSQLDao;
import it.mate.protons.shared.model.Applicazione;
import it.mate.protons.shared.model.PrincipioAttivo;
import it.mate.protons.shared.model.impl.ApplicazioneTx;
import it.mate.protons.shared.model.impl.PrincipioAttivoTx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**************************************************
 * 
 * [19/06/2014]
 * 
 * IMPORTANTISSIMO
 * 
 * NEI LOOP DI READ (iterateXXXForRead) NON CHIAMARE MAI METODI CHE CREANO NUOVE TRANSAZIONI
 * 
 * ES: iteratePrescrizioniForRead e findContattoById
 * 
 * DA JavaScriptException (InvalidStateError)
 * 
 *
 */

public class MainDao extends WebSQLDao {
  
  private final static boolean DROP_TABLES_ON_OPEN_DATABASE = false;
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
  private final static String APPLICAZIONI_FIELDS_0 = 
      "nome, dataInizio, dataFine, principiList ";

  private final static String APPLICAZIONI_FIELDS = APPLICAZIONI_FIELDS_0;
  
  private final static String PRINCIPI_FIELDS_0 = 
      "nome, path ";

  private final static String PRINCIPI_FIELDS = PRINCIPI_FIELDS_0;
  
  private List<PrincipioAttivo> cachePrincipiAttivi;
  
  
  
  public MainDao() {
    super("ProtonsDB", ESTIMATED_SIZE, migrationCallbacks, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapLog.log("created db protons");
      }
    }, new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (DROP_TABLES_ON_OPEN_DATABASE) {
          dropDBImpl(tr);
        }
      }
    });
  }
  
  public void dropDB(final Delegate<Void> delegate) {
    PhonegapLog.log("resetting db");
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        dropDBImpl(tr);
        db = null;
        initDB();
        delegate.execute(null);
      }
    });
  }
  
  private static void dropDBImpl(SQLTransaction tr) {
    PhonegapLog.log("dropping all tables");
    
    PhonegapLog.log("dropping table version");
    tr.doExecuteSql("DROP TABLE IF EXISTS version");
    
    PhonegapLog.log("dropping table applicazioni");
    tr.doExecuteSql("DROP TABLE IF EXISTS applicazioni");
    
    PhonegapLog.log("dropping table principi");
    tr.doExecuteSql("DROP TABLE IF EXISTS principi");
  }
  
  private final static MigratorCallback MIGRATION_CALLBACK_0 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db protons to version " + number);

      PhonegapLog.log("creating table applicazioni");
      tr.doExecuteSql("CREATE TABLE applicazioni (id "+SERIAL_ID+", " + APPLICAZIONI_FIELDS_0 + " )");

      createPrincipiAttivi(tr);
      
    }
  };
  
  private static void createPrincipiAttivi(SQLTransaction tr) {
    PhonegapLog.log("creating table principi");
    tr.doExecuteSql("CREATE TABLE principi (id "+SERIAL_ID+", " + PRINCIPI_FIELDS_0 + " )");

    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio uno',     'file1.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio due',     'file2.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio tre',     'file3.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio quattro', 'file4.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio cinque',  'file5.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio sei',     'file6.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio sette',   'file7.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio otto',    'file8.zdat')");
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Principio nove',    'file9.zdat')");
    
    tr.doExecuteSql("INSERT INTO principi (nome, path) VALUES ('Quantares',    'Quantares')");
  }
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0 
  };
  
  public void findAllApplicazioni(final Delegate<List<Applicazione>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + APPLICAZIONI_FIELDS + " FROM applicazioni ORDER BY id", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            new RSToApplicazioniIterator(rs, delegate);
          }
        });
      }
    });
  }
  
  protected class RSToApplicazioniIterator {
    SQLResultSet rs;
    List<Applicazione> results = new ArrayList<Applicazione>();
    Delegate<List<Applicazione>> delegate;
    protected RSToApplicazioniIterator(SQLResultSet rs, Delegate<List<Applicazione>> delegate) {
      this.rs = rs;
      this.delegate = delegate;
      ensureCachePrincipiAttivi(new Delegate<Void>() {
        public void execute(Void element) {
          iterate(0);
        }
      });
    }
    private void iterate(final int it) {
      if (it < rs.getRows().getLength()) {
        Applicazione result = flushRSToApplicazione(rs, it);
        results.add(result);
        iterate(it + 1);
      } else {
        delegate.execute(results);
      }
    }
    private Applicazione flushRSToApplicazione(SQLResultSet rs, int it) {
      SQLResultSetRowList rows = rs.getRows();
      Applicazione result = new ApplicazioneTx();
      result.setId(rows.getValueInt(it, "id"));
      result.setNome(rows.getValueString(it, "nome"));
      result.setDataInizio(longAsDate(rows.getValueLong(it, "dataInizio")));
      result.setDataFine(longAsDate(rows.getValueLong(it, "dataFine")));
      String principiList = rows.getValueString(it, "principiList");
      if (principiList != null) {
        String[] tokens = principiList.split("\\|");
        for (String token : tokens) {
          if (!"".equals(token)) {
            int idPrincipio = Integer.parseInt(token);
            for (PrincipioAttivo principio : cachePrincipiAttivi) {
              if (principio.getId().intValue() == idPrincipio) {
                result.getPrincipiAttivi().add(principio);
              }
            }
          }
        }
      }
      
      PhgUtils.log("flushed " + result);
      
      return result;
    }
  }
  
  public void saveApplicazione(final Applicazione entity, final Delegate<Applicazione> delegate) {
    
    PhgUtils.log("updating " + entity);
    
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        String principiList = "";
        if (entity.getPrincipiAttivi() != null) {
          for (int it = 0; it < entity.getPrincipiAttivi().size(); it++) {
            if (it > 0) {
              principiList += "|";
            }
            principiList += entity.getPrincipiAttivi().get(it).getId();
          }
        }
        final String fPrincipiList = principiList;
        if (entity.getId() == null) {
          tr.doExecuteSql("INSERT INTO applicazioni (" + APPLICAZIONI_FIELDS + ") VALUES (?, ?, ?, ?)", 
              new Object[] {
                entity.getNome(), 
                dateAsLong(entity.getDataInizio()), 
                dateAsLong(entity.getDataFine()), 
                fPrincipiList
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  entity.setId(rs.getInsertId());
                  PhonegapLog.log("Inserted " + entity);
                  delegate.execute(entity);
                }
              });
        } else {
          String sql = "UPDATE applicazioni SET ";
          sql += " nome = ?";
          sql += " ,dataInizio = ?";
          sql += " ,dataFine = ?";
          sql += " ,principiList = ?";
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              entity.getNome(), 
              dateAsLong(entity.getDataInizio()), 
              dateAsLong(entity.getDataFine()), 
              fPrincipiList,
              entity.getId()
            }, new SQLStatementCallback() {
              public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                PhonegapLog.log("Updated " + entity);
                delegate.execute(entity);
              }
            });
        }
      }
    });
  }
  
  private void ensureCachePrincipiAttivi(final Delegate<Void> delegate) {
    findAllPrincipiAttivi(new Delegate<List<PrincipioAttivo>>() {
      public void execute(List<PrincipioAttivo> results) {
        delegate.execute(null);
      }
    });
  }
  
  public void findAllPrincipiAttivi(final Delegate<List<PrincipioAttivo>> delegate) {
    if (this.cachePrincipiAttivi != null) {
      delegate.execute(cloneList(cachePrincipiAttivi));
    } else {
      db.doReadTransaction(new SQLTransactionCallback() {
        public void handleEvent(SQLTransaction tr) {
          tr.doExecuteSql("SELECT id, " + PRINCIPI_FIELDS + " FROM principi ORDER BY id", null, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              List<PrincipioAttivo> results = new ArrayList<PrincipioAttivo>();
              if (rs.getRows().getLength() > 0) {
                for (int it = 0; it < rs.getRows().getLength(); it++) {
                  results.add(flushRSToPrincipioAttivo(rs, it));
                }
              }
              cachePrincipiAttivi = results;
              delegate.execute(cloneList(results));
            }
          });
        }
      });
    }
  }
  
  private <T> List<T> cloneList(List<T> items) {
    if (items == null) {
      return null;
    }
    List<T> results = new ArrayList<T>();
    for (T item : items) {
      results.add(item);
    }
    return results;
  }
  
  private PrincipioAttivo flushRSToPrincipioAttivo(SQLResultSet rs, int it) {
    SQLResultSetRowList rows = rs.getRows();
    PrincipioAttivo result = new PrincipioAttivoTx();
    result.setId(rows.getValueInt(it, "id"));
    result.setNome(rows.getValueString(it, "nome"));
    result.setPath(rows.getValueString(it, "path"));
    return result;
  }
  
  public void savePrincipioAttivo(final PrincipioAttivo entity, final Delegate<PrincipioAttivo> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (entity.getId() == null) {
          tr.doExecuteSql("INSERT INTO principi (" + PRINCIPI_FIELDS + ") VALUES (?, ?)", 
              new Object[] {
                entity.getNome(), 
                entity.getPath()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  entity.setId(rs.getInsertId());
                  PhonegapLog.log("Inserted " + entity);
                  delegate.execute(entity);
                }
              });
        } else {
          String sql = "UPDATE principi SET ";
          sql += " nome = ?";
          sql += " ,path = ?";
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              entity.getNome(), 
              entity.getPath(),
              entity.getId()
            }, new SQLStatementCallback() {
              public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                PhonegapLog.log("Updated " + entity);
                delegate.execute(entity);
              }
            });
        }
      }
    });
  }
  
  
  public void deleteApplicazioni(final List<Applicazione> applicazioni, final Delegate<Void> delegate) {
    if (applicazioni == null || applicazioni.size() == 0) {
      delegate.execute(null);
    }
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(final SQLTransaction tr) {
        iterateApplicazioniForDelete(applicazioni.iterator(), tr, delegate);
      }
    });
  }
  
  private void iterateApplicazioniForDelete(final Iterator<Applicazione> it, SQLTransaction tr, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      final Applicazione applicazione = it.next();
      PhonegapLog.log("deleting " + applicazione);
      tr.doExecuteSql("DELETE FROM applicazioni WHERE id = ?", new Object[] {applicazione.getId()}, new SQLStatementCallback() {
        public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
          iterateApplicazioniForDelete(it, tr, delegate);
        }
      });
    } else {
      PhonegapLog.log("calling finish delegate");
      delegate.execute(null);
    }
  }
  
  public void resetPrincipiAttivi(final Delegate<Void> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("DROP TABLE principi",
            null, new SQLStatementCallback() {
              public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                createPrincipiAttivi(tr);
                PhonegapLog.log("Reset principi done");
                GwtUtils.deferredExecution(1000, delegate);
              }
            });
      }
    });
  }
  
}

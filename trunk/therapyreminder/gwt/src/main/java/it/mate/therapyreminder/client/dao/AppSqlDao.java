package it.mate.therapyreminder.client.dao;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.WebSQLDao;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.PrescrizioneTx;
import it.mate.therapyreminder.shared.model.impl.UdMTx;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AppSqlDao extends WebSQLDao {
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
  private final static String UDM_FIELDS = "codice, descrizione, sequenza"; 
  
  public AppSqlDao() {
    super("TherapiesDB", ESTIMATED_SIZE, migrationCallbacks, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapUtils.log("created db therapies");
      }
    });
  }
  
  private final static MigratorCallback MIGRATION_CALLBACK_0 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapUtils.log("updating db therapies to version " + number);
      
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
      
      tr.doExecuteSql("CREATE TABLE prescrizioni (id "+SERIAL_ID+", " + PRESCRIZIONI_FIELDS + " )");

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
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + PRESCRIZIONI_FIELDS + " FROM prescrizioni", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Prescrizione> results = new ArrayList<Prescrizione>();
            SQLResultSetRowList rows = rs.getRows();
            if (rows.getLength() > 0) {
              for (int it = 0; it < rows.getLength(); it++) {
                Prescrizione item = new PrescrizioneTx();
                item.setId(rows.getValueInt(it, "id"));
                item.setNome(rows.getValueString(it, "nome"));
                item.setDataInizio(new Date(rows.getValueLong(it, "dataInizio")));
                item.setQuantita(rows.getValueDouble(it, "quantita"));
                item.setTipoRicorrenza(rows.getValueString(it, "tipoRicorrenza"));
                item.setCodUdM(rows.getValueString(it, "codUdM"));
                item.setValoreRicorrenza(rows.getValueInt(it, "valoreRicorrenza"));
                results.add(item);
              }
            }
            delegate.execute(results);
          }
        });
      }
    });
  }
  
  private final static String PRESCRIZIONI_FIELDS = "nome, dataInizio, dataFine, quantita, codUdM, idComposizione, tipoRicorrenza, valoreRicorrenza";
  
  public void updatePrescrizione(final Prescrizione pr, final Delegate<Prescrizione> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (pr.getId() == null) {
          tr.doExecuteSql("INSERT INTO prescrizioni (" + PRESCRIZIONI_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
              new Object[] {
                pr.getNome(), 
                dateAsLong(pr.getDataInizio()), dateAsLong(pr.getDataFine()), 
                pr.getQuantita(), pr.getCodUdM(),
                pr.getIdComposizione(), 
                pr.getTipoRicorrenza(), pr.getValoreRicorrenza()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  pr.setId(rs.getInsertId());
                  delegate.execute(pr);
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
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              pr.getNome(), 
              dateAsLong(pr.getDataInizio()), dateAsLong(pr.getDataFine()), 
              pr.getQuantita(), pr.getCodUdM(),
              pr.getIdComposizione(), 
              pr.getTipoRicorrenza(), pr.getValoreRicorrenza()
              , pr.getId()
            }, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              delegate.execute(pr);
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
        deletePrescrizioniWithIterator(prescrizioni.iterator(), tr, delegate);
      }
    });
  }
  
  private void deletePrescrizioniWithIterator(final Iterator<Prescrizione> it, SQLTransaction tr, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      Prescrizione prescrizione = it.next();
      tr.doExecuteSql("DELETE FROM prescrizioni WHERE id = ?", new Object[] {prescrizione.getId()}, new SQLStatementCallback() {
        public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
          deletePrescrizioniWithIterator(it, tr, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }

}

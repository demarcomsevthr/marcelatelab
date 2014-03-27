package it.mate.therapyreminder.client.dao;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.WebSQLDao;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.impl.PrescrizioneTx;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AppSqlDao extends WebSQLDao {
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
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
      tr.doExecuteSql("CREATE TABLE therapies (id "+SERIAL_ID+", name)");
      tr.doExecuteSql("INSERT INTO therapies (name) VALUES ('prova1')");
      
      // VERO
      tr.doExecuteSql("CREATE TABLE prescrizioni (id "+SERIAL_ID+", " + PRESCRIZIONI_FIELDS + " )");
      
    }
  };
  
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
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0 ,MIGRATION_CALLBACK_1 ,MIGRATION_CALLBACK_2 ,MIGRATION_CALLBACK_3 ,MIGRATION_CALLBACK_4 
  };
  
  private final static String PRESCRIZIONI_FIELDS = "nome, dataInizio, dataFine, quantita, idComposizione, tipoRicorrenza"; 
  
  public void savePrescrizione(final Prescrizione pr, final Delegate<Prescrizione> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (pr.getId() == null) {
          tr.doExecuteSql("INSERT INTO prescrizioni (" + PRESCRIZIONI_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?)", 
              new Object[] {
                pr.getNome(), dateAsLong(pr.getDataInizio()),
                dateAsLong(pr.getDataFine()), pr.getQuantita(),
                pr.getIdComposizione(), pr.getTipoRicorrenza()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  pr.setId(rs.getInsertId());
                  delegate.execute(pr);
                }
              });
        } else {
          String sql = "UPDATE prescrizioni SET ";
          sql += " nome = ? , ";
          sql += " dataInizio = ? , ";
          sql += " dataFine = ? , ";
          sql += " quantita = ? , ";
          sql += " idComposizione = ? , ";
          sql += " tipoRicorrenza = ? ";
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              pr.getNome(), dateAsLong(pr.getDataInizio()),
              dateAsLong(pr.getDataFine()), pr.getQuantita(),
              pr.getIdComposizione(), pr.getTipoRicorrenza()
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
  
  public void findAllPrescrizioni(final Delegate<List<Prescrizione>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + PRESCRIZIONI_FIELDS + " FROM prescrizioni", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            List<Prescrizione> results = new ArrayList<Prescrizione>();
            if (rs.getRows().getLength() > 0) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                Prescrizione prescrizione = new PrescrizioneTx();
                prescrizione.setId(rs.getRows().getValueInt(it, "id"));
                prescrizione.setNome(rs.getRows().getValueString(it, "nome"));
                prescrizione.setDataInizio(new Date(rs.getRows().getValueLong(it, "dataInizio")));
                prescrizione.setQuantita(rs.getRows().getValueDouble(it, "quantita"));
                results.add(prescrizione);
              }
            }
            delegate.execute(results);
          }
        });
      }
    });
  }
  
  public void deletePrescrizioni(final List<Prescrizione> prescrizioni, final Delegate<Void> delegate) {
    if (prescrizioni == null || prescrizioni.size() == 0) {
      delegate.execute(null);
    }
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(final SQLTransaction tr) {
        deletePrescrizioneWithIterator(prescrizioni.iterator(), tr, delegate);
      }
    });
  }
  
  private void deletePrescrizioneWithIterator(final Iterator<Prescrizione> it, SQLTransaction tr, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      Prescrizione prescrizione = it.next();
      tr.doExecuteSql("DELETE FROM prescrizioni WHERE id = ?", new Object[] {prescrizione.getId()}, new SQLStatementCallback() {
        public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
          deletePrescrizioneWithIterator(it, tr, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }

}

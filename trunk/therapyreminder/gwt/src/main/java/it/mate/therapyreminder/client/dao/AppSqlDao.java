package it.mate.therapyreminder.client.dao;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.WebSQLDao;
import it.mate.therapyreminder.shared.model.Prescrizione;

import java.util.Date;

public class AppSqlDao extends WebSQLDao {
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
  private final static String PRESCRIZIONI_FIELDS = "nome, dataInizio, dataFine, quantita, idComposizione, tipoRicorrenza"; 
  
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
  
  public void savePrescrizione(final Prescrizione prescrizione, final Delegate<Prescrizione> delegate) {
    
    PhonegapUtils.log("before transaction");
    db.transactionImpl(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (prescrizione.getId() == null) {
          
          PhonegapUtils.log("before insert");
          tr.doExecuteSql("INSERT INTO prescrizioni (" + PRESCRIZIONI_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?)", 
              new Object[] {
                prescrizione.getNome(), dateAsLong(prescrizione.getDataInizio()),
                dateAsLong(prescrizione.getDataFine()), prescrizione.getQuantita(),
                prescrizione.getIdComposizione(), prescrizione.getTipoRicorrenza()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  PhonegapUtils.log("Inserted id = " + rs.getInsertId());
                  prescrizione.setId(rs.getInsertId());
                  
                  delegate.execute(prescrizione);
                  
                }
              });
          
        } else {
          
          //TODO: update
          
        }
      }
    }, null, null);
    
  }

}

package it.mate.therapyreminder.client.dao;

import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.WebSQLDao;

import java.util.Date;

public class AppSqlDao extends WebSQLDao {
  
  public AppSqlDao() {
    super("TherapiesDB", 5 * 1024 * 1024, migrationCallbacks, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapUtils.log("created db therapies");
      }
    });
  }
  
  private final static MigratorCallback MIGRATION_CALLBACK_0 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("CREATE TABLE therapies (id "+SERIAL_ID+", name)");
      transaction.doExecuteSql("INSERT INTO therapies (name) VALUES ('prova1')");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_1 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("CREATE TABLE drugs (id "+SERIAL_ID+", name, created)");
      transaction.doExecuteSql("INSERT INTO drugs (name, created) VALUES ('prova', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_2 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("ALTER TABLE therapies ADD COLUMN created");
      transaction.doExecuteSql("INSERT INTO therapies (name, created) VALUES ('prova2', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_3 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("DELETE FROM therapies WHERE id = 2");
      transaction.doExecuteSql("INSERT INTO therapies (name, created) VALUES ('prova3', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_4 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("ALTER TABLE therapies ADD COLUMN createdTm");
      Date NOW = new Date();
      transaction.doExecuteSql("INSERT INTO therapies (name, created, createdTm) VALUES (?, ?, ?)", 
          new Object[] {"prova4", 2014, NOW.getTime()});
    }
  };
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0 ,MIGRATION_CALLBACK_1 ,MIGRATION_CALLBACK_2 ,MIGRATION_CALLBACK_3 ,MIGRATION_CALLBACK_4 
  };

}

package it.mate.therapyreminder.client.dao;

import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.WebSQLDao;

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
      transaction.doExecuteSql("CREATE TABLE therapies (id, name)");
      transaction.doExecuteSql("INSERT INTO therapies VALUES (1, 'prova')");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_1 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("CREATE TABLE drugs (id primary key, name, created)");
      transaction.doExecuteSql("INSERT INTO drugs VALUES (1, 'prova', 2014)");
    }
  };
  
  private final static MigratorCallback MIGRATION_CALLBACK_2 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction transaction) {
      PhonegapUtils.log("updating db therapies to version " + number);
      transaction.doExecuteSql("ALTER TABLE therapies ADD COLUMN created");
      transaction.doExecuteSql("INSERT INTO therapies VALUES (2, 'prova2', 2014)");
    }
  };
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0, MIGRATION_CALLBACK_1, MIGRATION_CALLBACK_2
  };

}
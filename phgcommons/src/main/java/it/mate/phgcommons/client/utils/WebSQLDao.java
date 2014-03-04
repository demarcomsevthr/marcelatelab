package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public abstract class WebSQLDao {
  
  protected WindowDatabase db;
  
  private String name;
  
  private Long estimatedSize;
  
  private DatabaseCallback creationCallback;
  
  protected WebSQLDao(String name, long estimatedSize, MigratorCallback migrationCallbacks[], DatabaseCallback creationCallback) {
    this.name = name;
    this.estimatedSize = estimatedSize;
    this.creationCallback = creationCallback;
    openDatabase();
    doMigrations(migrationCallbacks);
  }
  
  protected void openDatabase() {
    this.db = WindowDatabase.openDatabase(name, "new", estimatedSize, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapUtils.log("creation callback");
      }
    });
  }
  
  private void doMigrations(final MigratorCallback migrationCallbacks[]) {
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        new Migrator(WebSQLDao.this, migrationCallbacks);
      }
    });
    /*
    new TimerUtil(500, new TimerUtil.Delegate() {
      public boolean canCancel() {
        if (db == null) {
          PhonegapUtils.log("db not ready");
          return false;
        } else {
          PhonegapUtils.log("db ready - version = '" + db.getVersion() + "'");
          new Migrator(WebSQLDao.this, migrationCallbacks);
          return true;
        }
      }
    });
    */
  }
  
  protected static class Migrator {
    MigratorCallback callbacks[];
    WebSQLDao dao;
    public Migrator(WebSQLDao dao, MigratorCallback callbacks[]) {
      this.dao = dao;
      this.callbacks = callbacks;
      int initialVersion = dao.db.getVersionInt();
      doMigration(initialVersion);
    }
    private void doMigration(final int number) {
      if (number < callbacks.length) {
//      dao.openDatabase();
        GwtUtils.deferredExecution(500, new Delegate<Void>() {
          public void execute(Void element) {
            PhonegapUtils.log("current version = '" + dao.db.getVersion() + "'");
            String oldVersion = number == 0 ? dao.db.getVersion() : ("" + (number - 1));
            String newVersion = ""+number;
            PhonegapUtils.log("try changing version: oldVersion = '" + oldVersion + "' newVersion = " + newVersion);
            dao.db.changeVersionImpl(oldVersion, newVersion, new SQLTransactionCallback() {
              public void handleEvent(SQLTransaction transaction) {
                callbacks[number].doMigration(number, transaction);
                doMigration(number + 1);
              }
            }, new SQLVoidCallback() {
              public void handleEvent() {
//              PhonegapUtils.log("change version success callback");
                doMigration(number + 1);
              }
            });
          }
        });
      }
    }
    
  }
  
  
  public static class WindowDatabase extends JavaScriptObject {
    protected WindowDatabase() { }
    private static WindowDatabase openDatabase (String name, String version, long estimatedSize, DatabaseCallback creationCallback) {
      return openDatabaseImpl(name, version, estimatedSize, creationCallback).cast();
    }
    private static native JavaScriptObject openDatabaseImpl (String name, String version, Long estimatedSize, DatabaseCallback creationCallback) /*-{
      var jsCreationCallback = null;
      if (creationCallback != null) {
        jsCreationCallback = $entry(function(db) {
          creationCallback.@it.mate.phgcommons.client.utils.WebSQLDao.DatabaseCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao$WindowDatabase;)(db);
        });
      }
      return $wnd.openDatabase(name, version, name, estimatedSize, jsCreationCallback);
    }-*/;
    public final native int getVersionInt() /*-{
      return parseInt(this.version) || 0;
    }-*/;
    public final native String getVersion() /*-{
      return this.version;
    }-*/;
    protected final native void changeVersionImpl(String oldVersion, String newVersion, SQLTransactionCallback callback, SQLVoidCallback successCallback) /*-{
      var jsCallback = null;
      if (callback != null) {
        jsCallback = $entry(function(tr) {
          callback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLTransactionCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao$SQLTransaction;)(tr);
        });
      }
      var jsSuccessCallback = null;
      if (successCallback != null) {
        jsSuccessCallback = $entry(function() {
          successCallback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLVoidCallback::handleEvent()();
        });
      }
      this.changeVersion(oldVersion, newVersion, jsCallback, jsSuccessCallback);
    }-*/;
  }
  
  public static class SQLTransaction extends JavaScriptObject {
    protected SQLTransaction() { }
    @SuppressWarnings("unchecked")
    public final void doExecuteSql(String sqlStatement) {
      executeSqlImpl(sqlStatement, (JsArray<JavaScriptObject>)JsArray.createArray().cast(), null, null);
    }
    public final void doExecuteSql(String sqlStatement, JsArray<JavaScriptObject> arguments) {
      executeSqlImpl(sqlStatement, arguments, null, null);
    }
    public final void doExecuteSql(String sqlStatement, JsArray<JavaScriptObject> arguments, SQLStatementCallback callback) {
      executeSqlImpl(sqlStatement, arguments, callback, null);
    }
    public final void doExecuteSql(String sqlStatement, JsArray<JavaScriptObject> arguments, SQLStatementCallback callback, SQLStatementErrorCallback errorCallback) {
      executeSqlImpl(sqlStatement, arguments, callback, errorCallback);
    }
    public final native void executeSqlImpl (String sqlStatement, JsArray<JavaScriptObject> arguments, SQLStatementCallback callback, SQLStatementErrorCallback errorCallback) /*-{
      this.executeSql(sqlStatement, arguments, callback, errorCallback);
    }-*/;
  }
  
  public interface DatabaseCallback {
    public void handleEvent(WindowDatabase db);
  }
  
  protected static interface SQLVoidCallback {
    public void handleEvent();
  }
  
  protected static interface SQLTransactionCallback {
    public void handleEvent(SQLTransaction transaction);
  }
  
  protected static interface SQLStatementCallback {
    public void handleEvent(SQLTransaction transaction, JavaScriptObject resultSet);
  }
  
  protected static interface SQLStatementErrorCallback {
    public void handleEvent(SQLTransaction transaction, JavaScriptObject error);
  }
  
  protected interface MigratorCallback {
    public void doMigration(int number, SQLTransaction transaction);
  }
  
  // TODO: CONTINUARE QUI
  
}

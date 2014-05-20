package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;


/**
 * 
 * SPECIFICATION REFERENCE:
 * 
 *   http://www.w3.org/TR/webdatabase/
 * 
 * 
 * [04/03/2014]
 *  L'UTILIZZO DI db.changeVersion HA DATO PROBLEMI QUINDI DECIDO DI GESTIRE LA VERSION SU UNA TABLE USER-DEFINED
 *
 */

public abstract class WebSQLDao {
  
  protected static WindowDatabase db;
  
  private String name;
  
  private Long estimatedSize;
  
  private DatabaseCallback creationCallback;
  
  private final static String sNULL = null;
  
  protected final static String SERIAL_ID = "INTEGER PRIMARY KEY AUTOINCREMENT"; 
  
  private boolean ready = false;
  
  protected WebSQLDao(String name, long estimatedSize, final MigratorCallback migrationCallbacks[], DatabaseCallback creationCallback, final SQLTransactionCallback openCallback) {
    this.name = name;
    this.estimatedSize = estimatedSize;
    this.creationCallback = creationCallback;
    if (db == null) {
      openDatabase(new SQLTransactionCallback() {
        public void handleEvent(SQLTransaction tr) {
          if (openCallback != null)
            openCallback.handleEvent(tr);
          doMigrations(migrationCallbacks);
        }
      });
    }
  }
  
  public boolean isReady() {
    return ready;
  }
  
  protected void openDatabase(SQLTransactionCallback openCallback) {
    db = WindowDatabase.openDatabase(name, "", estimatedSize, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapUtils.log("creation callback");
        if (creationCallback != null)
          creationCallback.handleEvent(db);
      }
    }, openCallback);
  }
  
  public static class WindowDatabase extends JavaScriptObject {
    protected WindowDatabase() { }
    private static WindowDatabase openDatabase (String name, String version, long estimatedSize, DatabaseCallback creationCallback, SQLTransactionCallback openCallback) {
      final WindowDatabase db = openDatabaseImpl(name, version, estimatedSize, creationCallback).cast();
      final SQLTransactionCallback migrationCallback = new SQLTransactionCallback() {
        public void handleEvent(SQLTransaction transaction) {
          PhonegapUtils.log("creating table version if not exists");
          transaction.doExecuteSql("CREATE TABLE IF NOT EXISTS version (number)");
          PhonegapUtils.log("select count on table version");
          transaction.doExecuteSql("SELECT COUNT(*) AS c FROM version", null, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction transaction, SQLResultSet resultSet) {
              if (resultSet.getRows().getLength() == 0 || resultSet.getRows().getValueInt(0, "c") == 0) {
                PhonegapUtils.log("INITIALIZING TABLE VERSION");
                transaction.doExecuteSql("INSERT INTO version VALUES (-1)");
              }
            }
          });
        }
      };
      if (openCallback != null) {
        db.transactionImpl(openCallback, null, new SQLVoidCallback() {
          public void handleEvent() {
            db.transactionImpl(migrationCallback, null, null);
          }
        });
      } else {
        db.transactionImpl(migrationCallback, null, null);
      }
      return db;
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
    protected final native int getVersionInt() /*-{
      return parseInt(this.version) || 0;
    }-*/;
    protected final native String getVersion() /*-{
      return this.version;
    }-*/;
    protected final void getVersionAsync(final int retry, final Delegate<Integer> delegate) {
      if (retry > 10)
        return;
      GwtUtils.deferredExecution(250, new Delegate<Void>() {
        public void execute(Void element) {
          readTransactionImpl(new SQLTransactionCallback() {
            public void handleEvent(SQLTransaction transaction) {
              transaction.executeSqlImpl("SELECT number FROM version", null, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction transaction, SQLResultSet resultSet) {
                  if (resultSet.getRows().getLength() > 0) {
                    delegate.execute(resultSet.getRows().getValueInt(0, "number"));
                  } else {
                    getVersionAsync(retry + 1, delegate);
                  }
                }
              }, null);
            }
          }, null, null);
        }
      });
    }
    protected final void updateVersion(final Integer newVersion, final SQLVoidCallback callback) {
      transactionImpl(new SQLTransactionCallback() {
        public void handleEvent(SQLTransaction transaction) {
          transaction.doExecuteSql("UPDATE version SET number = ?", new Object[]{newVersion});
          callback.handleEvent();
        }
      }, null, null);
    }
    protected final native void doDebug(JavaScriptObject jso) /*-{
      $wnd.glbDebugHook(jso);
    }-*/;
    
    /* 
    @Deprecated
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

    public final void doTransaction(SQLTransactionCallback callback) {
      transactionImpl(callback, null, null);
    }
    
    public final void doTransaction(SQLTransactionCallback callback, SQLVoidCallback successCallback) {
      transactionImpl(callback, null, successCallback);
    }
    
    public final void doTransaction(SQLTransactionCallback callback, SQLTransactionErrorCallback errorCallback, SQLVoidCallback successCallback) {
      transactionImpl(callback, errorCallback, successCallback);
    }
    
    protected final native void transactionImpl(SQLTransactionCallback callback, SQLTransactionErrorCallback errorCallback, SQLVoidCallback successCallback) /*-{
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
      var jsErrorCallback = null;
      if (errorCallback != null) {
        jsErrorCallback = $entry(function(er) {
          errorCallback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLTransactionErrorCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao$SQLError;)(er);
        });
      } else {
        jsErrorCallback = $entry(function(er) {
          var txt = @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(er);
          @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)('error - ' + txt);
        });
      }
      this.transaction(jsCallback, jsErrorCallback, jsSuccessCallback);
    }-*/;
    
    public final void doReadTransaction(SQLTransactionCallback callback) {
      readTransactionImpl(callback, null, null);
    }
    
    public final void doReadTransaction(SQLTransactionCallback callback, SQLVoidCallback successCallback) {
      readTransactionImpl(callback, null, successCallback);
    }
    
    public final void doReadTransaction(SQLTransactionCallback callback, SQLTransactionErrorCallback errorCallback, SQLVoidCallback successCallback) {
      readTransactionImpl(callback, errorCallback, successCallback);
    }
    
    protected final native void readTransactionImpl(SQLTransactionCallback callback, SQLTransactionErrorCallback errorCallback, SQLVoidCallback successCallback) /*-{
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
      var jsErrorCallback = null;
      if (errorCallback != null) {
        jsErrorCallback = $entry(function(er) {
          errorCallback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLTransactionErrorCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao$SQLError;)(er);
        });
      } else {
        jsErrorCallback = $entry(function(er) {
          var txt = @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(er);
          @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)('error - ' + txt);
        });
      }
      this.readTransaction(jsCallback, jsErrorCallback, jsSuccessCallback);
    }-*/;
    
    
  }
  
  public static class SQLTransaction extends JavaScriptObject {
    protected SQLTransaction() { }
    public final void doExecuteSql(String sqlStatement) {
      doExecuteSql(sqlStatement, null, null, null);
    }
    public final void doExecuteSql(String sqlStatement, Object[] arguments) {
      doExecuteSql(sqlStatement, arguments, null, null);
    }
    public final void doExecuteSql(String sqlStatement, Object[] arguments, SQLStatementCallback callback) {
      doExecuteSql(sqlStatement, arguments, callback, null);
    }
    public final void doExecuteSql(String sqlStatement, Object[] arguments, SQLStatementCallback callback, SQLStatementErrorCallback errorCallback) {
      JsArrayMixed jsArguments = JavaScriptObject.createArray().cast();
      if (arguments != null) {
        for (Object arg : arguments) {
          if (arg == null) {
            jsArguments.push((String)sNULL);
          } else if (arg instanceof String) {
            jsArguments.push((String)arg);
          } else if (arg instanceof Double || arg.getClass() == Double.TYPE) {
            Double value = (Double)arg;
            jsArguments.push((double)value);
          } else if (arg instanceof Integer || arg.getClass() == Integer.TYPE) {
            int value = (Integer)arg;
            jsArguments.push((double)value);
          } else if (arg instanceof Long || arg.getClass() == Long.TYPE) {
            long value = (Long)arg;
            jsArguments.push((double)value);
          } else {
            throw new RuntimeException("SQLTransaction::doExecuteSql: cannot pass arg of type " + arg.getClass());
          }
        }
      }
      executeSqlImpl(sqlStatement, jsArguments, callback, errorCallback);
    }
    protected final native void executeSqlImpl (String sqlStatement, JsArrayMixed arguments, SQLStatementCallback callback, SQLStatementErrorCallback errorCallback) /*-{
      var jsCallback = null;
      if (callback != null) {
        jsCallback = $entry(function(tr, rs) {
          callback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLStatementCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao$SQLTransaction;Lit/mate/phgcommons/client/utils/WebSQLDao$SQLResultSet;)(tr, rs);
        });
      }
      var jsErrorCallback = null;
      if (errorCallback != null) {
        jsErrorCallback = $entry(function(tr, er) {
          errorCallback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLStatementErrorCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao$SQLTransaction;Lit/mate/phgcommons/client/utils/WebSQLDao$SQLError;)(tr, er);
        });
      } else {
        jsErrorCallback = $entry(function(tr, er) {
          var txt = @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(er);
          @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)('error - ' + txt);
        });
      }
      this.executeSql(sqlStatement, arguments, jsCallback, jsErrorCallback);
    }-*/;
  }
  
  public static class SQLError extends JavaScriptObject {
    protected SQLError() { }
    public final native Short getCode() /*-{
      return this.code;
    }-*/;
    public final native String getMessage() /*-{
      return this.message;
    }-*/;
  }
  
  public static class SQLResultSet extends JavaScriptObject {
    protected SQLResultSet() { }
    public final native int getInsertId() /*-{
      return Math.floor(this.insertId);
    }-*/;
    public final native int getRowsAffected() /*-{
      return Math.floor(this.rowsAffected);
    }-*/;
    public final native SQLResultSetRowList getRows() /*-{
      return this.rows;
    }-*/;
  }
  
  public static class SQLResultSetRowList extends JavaScriptObject {
    protected SQLResultSetRowList() { }
    public final native int getLength() /*-{
      return Math.floor(this.length);
    }-*/;
    public final native JavaScriptObject getRowAsJSO(int row) /*-{
      return this.item(row);
    }-*/;
    public final native Object getValue(int row, String name) /*-{
      return this.item(row)[name];
    }-*/;
    public final native int getValueInt(int row, String name) /*-{
      return Math.floor(this.item(row)[name]);
    }-*/;
    public final Long getValueLong(int row, String name) {
      Double doubleValue = getValueDouble(row, name);
      return doubleValue != null ? doubleValue.longValue() : null;
    }
    public final Double getValueDouble(int row, String name) {
      if (isNull(row, name)) {
        return null;
      }
      return getValueDoubleImpl(row, name);
    }
    public final native double getValueDoubleImpl(int row, String name) /*-{
      return this.item(row)[name];
    }-*/;
    public final native String getValueString(int row, String name) /*-{
      return this.item(row)[name];
    }-*/;
    public final native boolean isNull(int row, String name) /*-{
      return (this.item(row)[name] == null);
    }-*/;
  }
  
  public interface DatabaseCallback {
    public void handleEvent(WindowDatabase db);
  }
  
  protected static interface SQLVoidCallback {
    public void handleEvent();
  }
  
  protected static interface SQLTransactionCallback {
    public void handleEvent(SQLTransaction tr);
  }
  
  protected static interface SQLTransactionErrorCallback {
    public void handleEvent(SQLError error);
  }
  
  protected static interface SQLStatementCallback {
    public void handleEvent(SQLTransaction tr, SQLResultSet rs);
  }
  
  protected static interface SQLStatementErrorCallback {
    public void handleEvent(SQLTransaction tr, SQLError error);
  }
  
  protected interface MigratorCallback {
    public void doMigration(int number, SQLTransaction tr);
  }
  
  protected Long dateAsLong(Date date) {
    if (date == null) {
      return null;
    }
    return date.getTime();
  }
  
  private void doMigrations(final MigratorCallback migrationCallbacks[]) {
    new Migrator(WebSQLDao.this, migrationCallbacks);
  }

  protected static class Migrator {
    MigratorCallback callbacks[];
    WindowDatabase db;
    WebSQLDao dao;
    public Migrator(WebSQLDao dao, MigratorCallback callbacks[]) {
      this.db = dao.db;
      this.callbacks = callbacks;
      this.dao = dao;
      db.getVersionAsync(0, new Delegate<Integer>() {
        public void execute(Integer currentVersion) {
          PhonegapUtils.log(">>> CURRENT DB VERSION = " + currentVersion);
          doMigration(currentVersion);
        }
      });
    }
    private void doMigration(final int number) {
      final Integer newVersion = number + 1;
      if (newVersion < callbacks.length) {
        GwtUtils.deferredExecution(500, new Delegate<Void>() {
          public void execute(Void element) {
            db.transactionImpl(new SQLTransactionCallback() {
              public void handleEvent(SQLTransaction transaction) {
                callbacks[newVersion].doMigration(newVersion, transaction);
              }
            }, null, new SQLVoidCallback() {
              public void handleEvent() {
                db.updateVersion(newVersion, new SQLVoidCallback() {
                  public void handleEvent() {
                    doMigration(newVersion);
                  }
                });
              }
            });
          }
        });
      } else {
        PhonegapUtils.log("db is ready");
        dao.ready = true;
      }
    }
  }
  
}

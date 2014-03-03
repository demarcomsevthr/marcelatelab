package it.mate.phgcommons.client.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class WebSQLDao {

  public WebSQLDao() {

  }
  
  private void openDatabase() {

  }
  

  /*
   * 


Database openDatabase(in DOMString name, in DOMString version, in DOMString displayName, 
in unsigned long estimatedSize, in optional DatabaseCallback creationCallback)

   * 
   */
  
  
  
  public static class WindowDatabase extends JavaScriptObject {
    protected WindowDatabase() { }
    private static WindowDatabase openDatabase (String name, String version, String displayName, long estimatedSize, DatabaseCallback creationCallback) {
      return openDatabaseImpl(name, version, displayName, estimatedSize, creationCallback).cast();
    }
    private static native JavaScriptObject openDatabaseImpl (String name, String version, String displayName, long estimatedSize, DatabaseCallback creationCallback) /*-{
      var jsCreationCallback = null;
      if (creationCallback != null) {
        jsCreationCallback = $entry(function(db) {
          creationCallback.@it.mate.phgcommons.client.utils.WebSQLDao.DatabaseCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao/WindowDatabase;)(db);
        });
      }
      return $wnd.openDatabase(name, version, displayName, estimatedSize, jsCreationCallback);
    }-*/;
    public native int getVersion() /*-{
      return parseInt(this.version) || 0;
    }-*/;
    protected native void changeVersionImpl(String oldVersion, String newVersion, SQLTransactionCallback callback, SQLVoidCallback successCallback) /*-{
      var jsCallback = null;
      if (callback != null) {
        jsCallback = $entry(function(tr) {
          callback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLTransactionCallback::handleEvent(Lit/mate/phgcommons/client/utils/WebSQLDao/SQLTransaction;)(tr);
        });
      }
      var jsSuccessCallback = null;
      if (successCallback != null) {
        jsSuccessCallback = $entry(function(tr) {
          successCallback.@it.mate.phgcommons.client.utils.WebSQLDao.SQLVoidCallback::handleEvent()();
        });
      }
      this.changeVersion(oldVersion, newVersion, jsCallback, jsSuccessCallback);
    }-*/;
  }
  
  public static class SQLTransaction extends JavaScriptObject {
    protected SQLTransaction() { }
    public native void executeSqlImpl (String sqlStatement, JsArray<JavaScriptObject> arguments, SQLStatementCallback callback, SQLStatementErrorCallback errorCallback) /*-{
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
  
  public interface MigratorCallback {
    public void doMigration(int number, SQLTransaction transaction);
  }
  
  // TODO: CONTINUARE QUI
  
  protected static class Migrator {

    MigratorCallback callbacks[];
    
    public Migrator(WindowDatabase db, MigratorCallback callbacks[]) {
      this.callbacks = callbacks;
      int initialVersion = db.getVersion();
    }
    
    private void doMigration(int number) {
      if (number < callbacks.length) {
        
      }
    }
    
  }

}

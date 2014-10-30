package it.mate.phgcommons.client.plugins;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOStringCallback;
import it.mate.phgcommons.client.utils.callbacks.JSOSuccess;

import com.google.gwt.core.client.JavaScriptObject;

public class FileSystemPlugin {
  
  /* 5 MB */
  private final static int DEFAULT_SIZE = 5 * 1024 * 1024;

  /*
   * Esempio:
   *   sourceFile = "www/data/test.txt"
   *   destPath = "protoph/workArea"
   * 
   */
  public static void copyApplicationFileToTmpDir (final String sourceFile, final String destPath, final Delegate<String> delegate) {
    PhgUtils.log("COPYING " + sourceFile + " TO " + destPath);
    final JSOStringCallback failure = new JSOStringCallback() {
      public void handle(String errorCode) {
        PhgUtils.log("COPYING ERROR CODE = " + errorCode);
        delegate.execute(null);
      }
    };
    getApplicationFileImpl(sourceFile, new JSOSuccess() {
      public void handle(final JavaScriptObject sourceFileEntry) {
        //PhgUtils.log("source file = " + JSONUtils.stringify(sourceFileEntry));
        getTempDirImpl(new JSOSuccess() {
          public void handle(JavaScriptObject tempDirEntry) {
            //PhgUtils.log("temp dir = " + JSONUtils.stringify(tempDirEntry));
            createDirIfNotExistsImpl(tempDirEntry, destPath, new JSOSuccess() {
              public void handle(JavaScriptObject destDirEntry) {
                //PhgUtils.log("dest dir = " + JSONUtils.stringify(destDirEntry));
                copyFileImpl(sourceFileEntry, destDirEntry, null, new JSOSuccess() {
                  public void handle(JavaScriptObject destFileEntry) {
                    PhgUtils.log("copied file entry " + JSONUtils.stringify(destFileEntry));
                    String result = GwtUtils.getJsPropertyString(destFileEntry, "fullPath");
                    delegate.execute(result);
                  }
                }, failure);
              }
            }, failure);
          }
        }, failure);
      }
    }, failure);
  }

  public static void deleteTmpDir (final String dirPath, final Delegate<String> delegate) {
    PhgUtils.log("DELETING " + dirPath);
    final JSOStringCallback failure = new JSOStringCallback() {
      public void handle(String errorCode) {
        PhgUtils.log("DELETING ERROR CODE = " + errorCode);
        delegate.execute(null);
      }
    };
    getTempFileImpl(dirPath, new JSOSuccess() {
      public void handle(final JavaScriptObject dirEntry) {
        //PhgUtils.log("dest dir = " + JSONUtils.stringify(dirEntry));
        deleteDirImpl(dirEntry, new JSOSuccess() {
          public void handle(JavaScriptObject deletedDirEntry) {
            PhgUtils.log("deleted dir entry " + JSONUtils.stringify(dirEntry));
            if (deletedDirEntry != null) {
              String result = GwtUtils.getJsPropertyString(deletedDirEntry, "fullPath");
              delegate.execute(result);
            } else {
              delegate.execute(null);
            }
          }
        }, failure);
      }
    }, failure);
  }

  public static native boolean isInstalled () /*-{
    return typeof ($wnd.requestFileSystem) != 'undefined' && typeof ($wnd.resolveLocalFileSystemURL) != 'undefined';
  }-*/;

  /*****************************************************************************************************************/
  
  private static native void requestPersistentFileSystemImpl (int size, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileSystem) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileSystem);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    $wnd.requestFileSystem($wnd.PERSISTENT, size, jsSuccess, jsFailure);    
  }-*/;

  private static native void requestTemporaryFileSystemImpl (int size, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileSystem) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileSystem);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    $wnd.requestFileSystem($wnd.TEMPORARY, size, jsSuccess, jsFailure);    
  }-*/;

  private static native void getRootFileImpl (String src, JavaScriptObject fs, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    fs.root.getFile(src, {}, jsSuccess, jsFailure);
  }-*/;

  private static native void getApplicationFileImpl (String src, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    $wnd.resolveLocalFileSystemURL($wnd.cordova.file.applicationDirectory + src, jsSuccess, jsFailure);
  }-*/;

  private static native void getTempFileImpl (String src, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    $wnd.resolveLocalFileSystemURL($wnd.cordova.file.tempDirectory + src, jsSuccess, jsFailure);
  }-*/;

  private static native void getTempDirImpl (JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    $wnd.resolveLocalFileSystemURL($wnd.cordova.file.tempDirectory, jsSuccess, jsFailure);
  }-*/;

  private static native void createDirIfNotExistsImpl (JavaScriptObject rootDirEntry, String path, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    var jsCreateDirFn = $entry(function (rootDirEntry, folders) {
      if (folders[0] == '.' || folders[0] == '') {
        folders = folders.slice(1);
      }
      rootDirEntry.getDirectory(folders[0], {create: true}, function(dirEntry) {
        // Recursively add the new subfolder (if we still have another to create).
        if (folders.length > 1) {
          jsCreateDirFn(dirEntry, folders.slice(1));
        } else {
          jsSuccess(dirEntry);
        }
      }, jsFailure);
    });
    jsCreateDirFn(rootDirEntry, path.split('/'));
  }-*/;

  private static native void copyFileImpl (JavaScriptObject fileEntry, JavaScriptObject destDirEntry, String newName, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    if (newName == null) {
      newName = fileEntry.name;
    }
    fileEntry.copyTo(destDirEntry, newName, jsSuccess, jsFailure);
  }-*/;
  
  private static native void deleteDirImpl (JavaScriptObject dirEntry, JSOSuccess success, JSOStringCallback failure) /*-{
    var jsSuccess = $entry(function(fileEntry) {
      success.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(fileEntry);
    });
    var jsFailure = $entry(function(error) {
      failure.@it.mate.phgcommons.client.utils.callbacks.JSOStringCallback::handle(Ljava/lang/String;)(error.code);
    });
    dirEntry.removeRecursively(jsSuccess, jsFailure);
  }-*/;

}

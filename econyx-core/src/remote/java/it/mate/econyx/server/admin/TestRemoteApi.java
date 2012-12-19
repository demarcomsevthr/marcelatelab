package it.mate.econyx.server.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class TestRemoteApi {

  private final static String APPID = "copycartest"; 
  
  private RemoteApiInstaller installer;
  
  public static void main(String[] args) {
    
    TestRemoteApi tester = new TestRemoteApi();
    
    try {
      tester.doTest();
    } catch (Throwable th) {
      th.printStackTrace();
    }
    
  }
  
  /**
   * 
   * SEE https://developers.google.com/appengine/docs/java/datastore/metadataqueries?hl=it-IT
   * 
   */
  
  private void doTest() throws Exception {
    installRemoteApi();
    
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    
    Query q;

    System.out.println("Printing namespaces...");
    q = new Query(Entities.NAMESPACE_METADATA_KIND);
    for (Entity e : ds.prepare(q).asIterable()) {
      if (e.getKey().getId() != 0) {
        System.out.println("<default>");
      } else {
        System.out.println(e.getKey().getName());
      }
    }
    
    System.out.println("\nPrinting kinds...");
    q = new Query(Entities.KIND_METADATA_KIND);
    for (Entity e : ds.prepare(q).asIterable()) {
      if (acceptEntity(e))
        System.out.println(entityToString(e));
    }
    
    System.out.println("\nPrinting properties...");
    q = new Query(Entities.PROPERTY_METADATA_KIND);
    for (Entity e : ds.prepare(q).asIterable()) {
      if (acceptEntity(e))
        System.out.println(entityToString(e));
    }
    
    System.out.println("\n\n");
    
    System.out.println("Dumping entities...");
    q = new Query(Entities.KIND_METADATA_KIND);
    for (Entity e : ds.prepare(q).asIterable()) {
      if (acceptEntity(e))
        exportEntity(ds, e);
    }
    
    uninstallRemoteApi();
  }
  
  private void exportEntity(DatastoreService ds, Entity kindEntity) throws Exception {
    String kindName = kindEntity.getKey().getName();
    System.out.println(String.format("\n\nDumping entity %s...\n", kindName));
    
    Query q = new Query(kindName);
    
    PreparedQuery pq = ds.prepare(q);

    /*
    FetchOptions options = FetchOptions.Builder.withDefaults();
    pq.asIterable(options);
    */
    
    int nr = 0;
    for (Entity result : pq.asIterable()) {
      nr++;
      for (String key : result.getProperties().keySet()) {
        Object value = result.getProperty(key);
        printResultPropertyValue(nr, key, value);
      }
      if ("ProductDs".equals(kindName)) {
        Object value = result.getProperty("htmls");
        printResultPropertyValue(nr, "htmls", value);
      }
      System.out.println();
    }
  }
  
  private void printResultPropertyValue(int nr, String key, Object value) {
    if (value == null)
      value = new NullValue();
    System.out.println(String.format("RESULT N. %s - property %s - value = {class = %s ; value = %s}", nr, key, value.getClass().getName(), value));
  }
  
  private boolean acceptEntity(Entity e) {
    if (e.getKey().getName().startsWith("_"))
      return false;
    if (e.getParent() != null && e.getParent().getName().startsWith("_"))
      return false;
    return true;
  }
  
  private String entityToString(Entity e) {
    String msg = "Entity {";
    if (e.getKey().getParent() != null) {
      msg = detailToString(msg, "parent", e.getKey().getParent().getName());
    }
    msg = detailToString(msg, "appId", e.getAppId() );
    if (!"".equals(e.getNamespace()))
      msg = detailToString(msg, "namespace", e.getNamespace() );
    msg = detailToString(msg, "kind", e.getKind() );
    msg = detailToString(msg, "key.name", e.getKey().getName() );
    if (!"".equals(e.getKey().getNamespace()))
      msg = detailToString(msg, "key.namespace", e.getKey().getNamespace() );
    if (!e.getKind().equals(e.getKey().getKind()))
      msg = detailToString(msg, "key.kind", e.getKey().getKind() );
    if (e.getKey().getId() != 0)
      msg = detailToString(msg, "key.id", "" + e.getKey().getId() );
    
    for (String key : e.getProperties().keySet()) {
      Object value = e.getProperty(key);
      if (value != null)
        msg = detailToString(msg, "property["+key+"]", value.toString());
    }
    
    msg += "}";
    return msg;
  }
  
  private String detailToString (String msg, String name, String value) {
    if (!msg.endsWith("{"))
      msg += " , ";
    msg += name + ": " + value;
    return msg;
  }
  
  private void installRemoteApi() throws Exception {
    String username = readLine("username: ");
    String pwd = readLine("password: ");
    RemoteApiOptions options = new RemoteApiOptions()
          .server(APPID+".appspot.com", 443)
          .credentials( username, pwd );
    installer = new RemoteApiInstaller();
    System.out.println("Installing remote api...\n");
    installer.install(options);
  }

  private void uninstallRemoteApi() throws Exception {
    installer.uninstall();
  }
  
  private String readLine(String prompt) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.print(prompt);
    return reader.readLine();
  }
  
}

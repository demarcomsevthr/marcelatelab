package it.mate.copymob.client.logic;

import it.mate.copymob.client.factories.AppClientFactory;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.TimbroTx;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.plugins.FileSystemPlugin;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public class TimbriInitializer {

  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private final static int NUMBER_OF_ITEMS = 5;
  
  private final static String dataPath = "www/main/data";
  
  public static void doRun() {
    new TimbriInitializer().run();
  }
  
  protected TimbriInitializer() {  }

  protected void run() {
    
    dao.findAllTimbri(new Delegate<List<Timbro>>() {
      public void execute(List<Timbro> timbri) {
        if (timbri == null || timbri.size() == 0) {
          
          iterateDataFiles(0, new ArrayList<Timbro>(), new Delegate<List<Timbro>>() {
            public void execute(List<Timbro> results) {
              
              for (Timbro timbro : results) {
                
                dao.saveTimbro(timbro, new Delegate<Timbro>() {
                  public void execute(Timbro element) {
                    
                  }
                });
                
              }
              
            }
          });
          
        }
      }
    });
    
  }
  
  private Timbro createTimbro(int index, String imgData) {
    int n = index + 1;
    Timbro result = new TimbroTx();
    result.setCodice("T" + n);
    result.setNome("TIMBRO " + n);
    result.setImage(imgData);
    result.setWidth(1200d);
    result.setHeight(800d);
    result.setOval(false);
    return result;
  }
  
  private void iterateDataFiles(final int it, final List<Timbro> results, final Delegate<List<Timbro>> endDelegate) {
    if (it < NUMBER_OF_ITEMS) {
      if (OsDetectionUtils.isDesktop()) {
        readFromLocalhost("http://127.0.0.1:8888/.image?name=timbro"+it+".jpg", new Delegate<String>() {
          public void execute(String imgData) {
            results.add(createTimbro(it, imgData));
            iterateDataFiles(it + 1, results, endDelegate);
          }
        });
      } else {
        if (FileSystemPlugin.isInstalled()) {
          String fileName = "timbro" + it + ".jpg";
          FileSystemPlugin.readApplicationFileAsEncodedData(dataPath + "/" + fileName, new Delegate<String>() {
            public void execute(String imgData) {
              if (imgData == null) {
                PhgUtils.log("RESULT NULL");
              } else {
                PhgUtils.log("READ: " + imgData.substring(0, 200));
              }
              results.add(createTimbro(it, imgData));
              iterateDataFiles(it + 1, results, endDelegate);
            }
          });
        } else {
          PhgUtils.log("FILE SYSTEM PLUGIN NOT INSTALLED!");
        }
      }
    } else {
      endDelegate.execute(results);
    }
  }
  
  /**
   * 
   * PER CONTROLLARE LA CORRETTEZZA DEL B64 UTILIZZARE:
   * 
   * http://www.motobit.com/util/base64-decoder-encoder.asp
   * 
   */
  
  private void readFromLocalhost(String uri, final Delegate<String> imageDelegate) {
    RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, uri);
    rb.setHeader("Content-Type", "application/base64");
    try {
      rb.sendRequest(null, new RequestCallback() {
        public void onResponseReceived(Request request, Response response) {
          if (200 == response.getStatusCode()) {
//          String imgB64 = Base64UtilsClient.toBase64(response.getText().getBytes());
//          String imgB64 = PhgUtils.btoa(response.getText());
            String imgB64 = response.getText();
            
            /*
            imgB64 = GwtUtils.replaceEx(imgB64, "_", "/");
            imgB64 = GwtUtils.replaceEx(imgB64, "$", "+");
            */
            
            PhgUtils.log("RECEIVED " + imgB64);
            imageDelegate.execute(imgB64);
          } else {
            PhgUtils.log("status code error " + response.getStatusCode() + " - " + response.getStatusText());
          }
        }
        @Override
        public void onError(Request request, Throwable exception) {
          PhgUtils.log("response error");
        }
      });
    } catch (RequestException ex) {
      PhgUtils.log("request error");
    }
  }
  
}

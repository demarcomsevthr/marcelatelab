package it.mate.econyx.server.tasks;

import it.mate.econyx.server.util.AdaptersUtil;

import com.google.appengine.api.taskqueue.DeferredTask;

@SuppressWarnings("serial")
public class PortalDataUploadDeferredTask implements DeferredTask {

  // ATTENZIONE !!!
  // SONO COSTRETTO AD UTILIZZARE UN SINGLETON PER AGGIRARE IL LIMITE DEI 100KB PER PAYLOAD
  
  public PortalDataUploadDeferredTask(String uploadedPortalDataStream) {
    PortalDataStreamSingleton.setUploadedPortalDataStream(uploadedPortalDataStream);
  }

  @Override
  public void run() {
    AdaptersUtil.getPortalDataMarshaller().load(PortalDataStreamSingleton.getUploadedPortalDataStream());
  }
  
  public static class PortalDataStreamSingleton {
    
    private static String uploadedPortalDataStream;
    
    public static void setUploadedPortalDataStream(String uploadedPortalDataStream) {
      PortalDataStreamSingleton.uploadedPortalDataStream = uploadedPortalDataStream;
    }
    
    public static String getUploadedPortalDataStream() {
      return uploadedPortalDataStream;
    }
    
  }
  
  
}

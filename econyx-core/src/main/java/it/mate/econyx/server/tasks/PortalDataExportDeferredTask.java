package it.mate.econyx.server.tasks;

import it.mate.econyx.server.util.AdaptersUtil;

import com.google.appengine.api.taskqueue.DeferredTask;

@SuppressWarnings("serial")
public class PortalDataExportDeferredTask implements DeferredTask {
  
  private int exportMethod;
  
  private String jobId;
  
  public PortalDataExportDeferredTask() {

  }

  public PortalDataExportDeferredTask(String jobId, int exportMethod) {
    this.exportMethod = exportMethod;
    this.jobId = jobId;
  }

  @Override
  public void run() {
    AdaptersUtil.getPortalDataMarshaller().unloadDeferred(jobId, exportMethod);
  }
  
}

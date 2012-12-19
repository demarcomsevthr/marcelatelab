package it.mate.econyx.server.tasks;

import it.mate.econyx.server.util.AdaptersUtil;

import com.google.appengine.api.taskqueue.DeferredTask;

@SuppressWarnings("serial")
public class PortalDataInitializeDeferredTask implements DeferredTask {

  @Override
  public void run() {
    AdaptersUtil.getPortalDataMarshaller().load();
  }
  
}

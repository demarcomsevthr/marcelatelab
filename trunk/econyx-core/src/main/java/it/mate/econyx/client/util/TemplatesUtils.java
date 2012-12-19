package it.mate.econyx.client.util;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.events.BroadcastListener;
import it.mate.portlets.client.events.BroadcastManager;
import it.mate.portlets.client.events.PageTemplateChangeCompleteEvent;
import it.mate.portlets.client.events.PageTemplateChangeRequestEvent;
import it.mate.portlets.client.events.PageTemplateEvent;

public class TemplatesUtils {

  private static BroadcastListener broadcastListener;
  
  public static boolean isCurrentTemplate (String templateName) {
    
    // versione 12/10/2012
    /*
    if (templateName == null) {
      return AppClientFactory.IMPL.getPortalSessionState().getTemplateName() != null;
    } else {
      return templateName.equals(AppClientFactory.IMPL.getPortalSessionState().getTemplateName());
    }
    */
    
    // patch 14/09/2012 
    return (AppClientFactory.IMPL.getPortalSessionState().getTemplateName().equals(templateName));
    
    /* versione precedente: 
    return (AppClientFactory.IMPL.getPortalSessionState().getTemplateName() == null || 
        AppClientFactory.IMPL.getPortalSessionState().getTemplateName().equals(templateName));
     */
    
  }
  
  public static void changeCurrentTemplate(final String templateName, final Delegate<Void> pageTemplateChangeCompleteDelegate) {
    if (broadcastListener == null) {
      broadcastListener = new BroadcastListener() {
        public void onBroadcast(PageTemplateEvent event) {
          if (event instanceof PageTemplateChangeCompleteEvent) {
            onPageTemplateChangeCompleteEvent((PageTemplateChangeCompleteEvent)event, pageTemplateChangeCompleteDelegate);
          }
        }
      };
      BroadcastManager.get().addBroadcastListener(broadcastListener);
    }
    BroadcastManager.get().broadcast(new PageTemplateChangeRequestEvent(templateName));
  }
  
  private static void onPageTemplateChangeCompleteEvent(PageTemplateChangeCompleteEvent event, Delegate<Void> pageTemplateChangeCompleteDelegate) {
    AppClientFactory.IMPL.getPortalSessionState().setTemplateName(event.getName());
    pageTemplateChangeCompleteDelegate.execute(null);
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        BroadcastManager.get().removeBroadcastListener(broadcastListener);
        broadcastListener = null;
      }
    });
  }
  
}

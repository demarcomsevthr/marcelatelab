package it.mate.portlets.client.util;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.events.BroadcastListener;
import it.mate.portlets.client.events.BroadcastManager;
import it.mate.portlets.client.events.PageTemplateRenderRequestEvent;
import it.mate.portlets.client.events.PageTemplateChangeRequestEvent;
import it.mate.portlets.client.events.PageTemplateEvent;
import it.mate.portlets.client.layout.LayoutUtil;
import it.mate.portlets.client.ui.PagePortlet;
import it.mate.portlets.shared.model.PageTemplate;

import com.google.gwt.user.client.ui.HasWidgets;

public class CommonTemplatesUtils {

  private static final String DEFAULT_ROOT_PAGE = "root";
  
  private static final String DEFAULT_HOME_PAGE = "home";
  
  public interface PageTemplateRetriever {
    public void fetchTemplate(String name, Delegate<PageTemplate> pageDelegate);
  }
  
  public static void initTemplates(final HasWidgets initialPanel, final String initialPageName, final PageTemplateRetriever pageTemplateRetriever) {
    pageTemplateRetriever.fetchTemplate(DEFAULT_ROOT_PAGE, new Delegate<PageTemplate>() {
      public void execute(PageTemplate pageTemplate) {
        initialPanel.add(LayoutUtil.createWidget(pageTemplate.getWidgetFactory()));
        BroadcastManager.get().addBroadcastListener(new BroadcastListener() {
          public void onBroadcast(PageTemplateEvent event) {
            if (event instanceof PageTemplateChangeRequestEvent) {
              PageTemplateChangeRequestEvent pageTemplateChangeRequestEvent = (PageTemplateChangeRequestEvent)event;
              onPageTemplateChangeRequestEvent(pageTemplateChangeRequestEvent, pageTemplateRetriever);
            }
          }
        });
        String actualInitialPageName = initialPageName;
        if (pageTemplate.getWidgetFactory() instanceof PagePortlet.Factory && actualInitialPageName == null) {
          PagePortlet.Factory pagePortletFactory = (PagePortlet.Factory)pageTemplate.getWidgetFactory();
          GwtUtils.log(getClass(), "fetchPageFromServer.success", "initialPageName = " + pagePortletFactory.getInitialPageName());
          actualInitialPageName = pagePortletFactory.getInitialPageName();
        }
        if (actualInitialPageName == null)
          actualInitialPageName = DEFAULT_HOME_PAGE;
        BroadcastManager.get().broadcast(new PageTemplateChangeRequestEvent(actualInitialPageName));
      }
    });
  }
  
  private static void onPageTemplateChangeRequestEvent(PageTemplateChangeRequestEvent pageTemplateChangeRequestEvent, PageTemplateRetriever pageTemplateRetriever) {
    pageTemplateRetriever.fetchTemplate(pageTemplateChangeRequestEvent.getName(), new Delegate<PageTemplate>() {
      public void execute(PageTemplate pageTemplate) {
        BroadcastManager.get().broadcast(new PageTemplateRenderRequestEvent(pageTemplate.getName(), pageTemplate.getWidgetFactory()));
      }
    });
  }
  
}

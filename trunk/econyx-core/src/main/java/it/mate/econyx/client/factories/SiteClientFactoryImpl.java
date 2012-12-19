package it.mate.econyx.client.factories;

import it.mate.econyx.client.css.CoreClientBundle;
import it.mate.econyx.client.events.PortalInitEvent;
import it.mate.econyx.client.events.PortalPageChangingEvent;
import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.places.AppPlaceHistoryMapper;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.econyx.client.util.PortalPageCacheUtil;
import it.mate.econyx.client.util.PortalUtils;
import it.mate.econyx.client.util.TemplatesUtils;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.util.PropertyConstants;
import it.mate.gwtcommons.client.factories.BaseClientFactoryImpl;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.HashChangeUtil;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.client.ui.PagePortlet;
import it.mate.portlets.client.util.CommonTemplatesUtils;
import it.mate.portlets.shared.model.PageTemplate;
import it.mate.portlets.shared.services.PortalServiceAsync;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SiteClientFactoryImpl extends BaseClientFactoryImpl<AppGinjector> 
    implements AppClientFactory, PortalSessionStateChangeEvent.Handler, PortalPageChangingEvent.Handler {
  
  private static PlaceHistoryMapper placeHistoryMapper;
  
  private PortalSessionState portalSessionState = new PortalSessionState(PortalSessionState.MODULE_SITE);
  
  private HandlerRegistration portalSessionStateChangeRegistration;
  
  private HandlerRegistration portalPageChangeRegistration;
  
  private final PortalInitCompleteDelegate portalInitCompleteDelegate = new PortalInitCompleteDelegate();
  
  
  
  
  // 12/10/2012
  // PROVATO A GESTIRE LA CACHE MA NON FUNZIONA SE SI RIUTILIZZA UN TEMPLATE IN CACHE
  // NON REINIZIALIZZA CORRETTAMENTE LE PORTLET
  private static final boolean PAGE_TEMPLATE_CACHE_ENABLED = true; 
  
  private static Map<String, PageTemplate> pageTamplateCache = new HashMap<String, PageTemplate>();
  
  
  private String originalTitle;
  
  
  
  @Override
  protected SiteGinjector createGinjector() {
    return GWT.create(SiteGinjector.class);
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public <G extends AppGinjector> G getConcreteGinjector(Class<G> ginClass) {
    return ((G)AppClientFactory.IMPL.getGinjector());
  }
  
  public void initMvp(SimplePanel panel, BaseActivityMapper activityMapper) {
    super.initMvp(panel, getPlaceHistoryMapper(), activityMapper);
  }
  
  @Override
  public PlaceHistoryMapper getPlaceHistoryMapper() {
    if (placeHistoryMapper == null)
      placeHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);
    return placeHistoryMapper;
  }
  
  @Override
  public void initModule(Panel portalPanel) {
    
    portalPanel.setVisible(false);
    
    PortalUtils.setPortalPanel(portalPanel);
    
    PortalUtils.showLoadingPanel();
    
    AppClientFactory.IMPL.getEventBus().fireEvent(new PortalInitEvent(portalPanel));
    
    CoreClientBundle.INSTANCE.core().ensureInjected();
    
    PortalSessionState.activateStateMonitor(getEventBus());
    
    portalSessionStateChangeRegistration = getEventBus().addHandler(PortalSessionStateChangeEvent.TYPE, this);
    
    portalPageChangeRegistration = getEventBus().addHandler(PortalPageChangingEvent.TYPE, this);
    
    this.originalTitle = Window.getTitle();
    
    retrievePortalSessionState();
    
  }
  
  private void retrievePortalSessionState() {
    String startingPageCode = Window.Location.getParameter("page");
    if (startingPageCode == null) {
      startingPageCode = Window.Location.getHash();
      if (startingPageCode != null && startingPageCode.startsWith("#")) {
        startingPageCode = startingPageCode.substring(1);
      }
    }
    if (startingPageCode == null) {
      startingPageCode = "";
    }
    getGinjector().getPortalPageService().findByCode(startingPageCode, new AsyncCallback<PortalPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
        completePortalInitialization(null, null);
      }
      public void onSuccess(final PortalPage startingPage) {
        if (PropertiesHolder.getBoolean(PropertyConstants.STORE_PORTAL_STATE_IN_HTTP_SESSION)) {
          getGinjector().getGeneralService().retrievePortalSessionState(PortalSessionState.MODULE_SITE, new AsyncCallback<PortalSessionState>() {
            public void onFailure(Throwable caught) {
              Window.alert(caught.getMessage());
              completePortalInitialization(null, startingPage);
            }
            public void onSuccess(PortalSessionState portalSessionState) {
              // 28/11/2012
              completePortalInitialization(portalSessionState, startingPage);
              /*
              if (portalSessionState == null) {
                portalSessionState = new PortalSessionState(PortalSessionState.MODULE_SITE);
              }
              portalSessionState.setCurrentPage(startingPage);
              if (startingPage != null) {
                portalSessionState.setTemplateName(startingPage.getTemplateName());
              }
              setPortalSessionState(portalSessionState);
              portalInitCompleteDelegate.onPortalInitComplete(new StartingPortalContext(portalSessionState.getTemplateName(), true));
              getEventBus().fireEvent(new PortalSessionStateChangeEvent(portalSessionState));
              */
            }
          });
        } else {
          completePortalInitialization(null, startingPage);
          /*
          PortalSessionState portalSessionState = new PortalSessionState(PortalSessionState.MODULE_SITE);
          portalSessionState.setCurrentPage(startingPage);
          if (startingPage != null) {
            portalSessionState.setTemplateName(startingPage.getTemplateName());
          }
          setPortalSessionState(portalSessionState);
          portalInitCompleteDelegate.onPortalInitComplete(new StartingPortalContext(portalSessionState.getTemplateName(), true));
          getEventBus().fireEvent(new PortalSessionStateChangeEvent(portalSessionState));
          */
        }
      }
    });
    initHashChangeListener();
  }
  
  private void completePortalInitialization(PortalSessionState portalSessionState, PortalPage startingPage) {
    if (portalSessionState == null) {
      portalSessionState = new PortalSessionState(PortalSessionState.MODULE_SITE);
    }
    portalSessionState.setCurrentPage(startingPage);
    if (startingPage != null) {
      portalSessionState.setTemplateName(startingPage.getTemplateName());
    }
    setPortalSessionState(portalSessionState);
    portalInitCompleteDelegate.onPortalInitComplete(new StartingPortalContext(portalSessionState.getTemplateName(), true));
    getEventBus().fireEvent(new PortalSessionStateChangeEvent(portalSessionState));
  }
  
  @Override
  public void onPortalSessionStateChange(PortalSessionStateChangeEvent event) {
    GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "############################");
    GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "changed portal session state");
    GwtUtils.log(getClass(), "portalSessionStateChangeEvent", "event = " + event);
    if (PropertiesHolder.getBoolean(PropertyConstants.STORE_PORTAL_STATE_IN_HTTP_SESSION)) {
      getGinjector().getGeneralService().storePortalSessionState(event.getState(), new AsyncCallback<Void>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Void result) {
        }
      });
    }
  }
  

  @Override
  public void onPortalPageChanging(PortalPageChangingEvent event) {
    GwtUtils.log(getClass(), "PortalPageChangingEvent", "=================================================================================================================================");
    GwtUtils.log(getClass(), "PortalPageChangingEvent", "changed portal page");
    GwtUtils.log(getClass(), "PortalPageChangingEvent", "page = " + event.getPage() != null ? event.getPage().getName() : "null!");
    
    final PortalPage changingPage = event.getPage();
    
    // patch 14/09/2012 (ottimizza il processo di cambio template)
    if (AppClientFactory.IMPL.getPortalSessionState().getTemplateName() == null) {
      AppClientFactory.IMPL.getPortalSessionState().setTemplateName(changingPage.getTemplateName());
    }
    
    boolean isCurrentPageChanged = !(changingPage.getId().equals(AppClientFactory.IMPL.getPortalSessionState().getCurrentPageId()));
    
    boolean isCurrentTemplateChanged = !(TemplatesUtils.isCurrentTemplate(changingPage.getTemplateName()));
    
    if (event.forceReloadPage() || isCurrentPageChanged || isCurrentTemplateChanged) {
      GwtUtils.log(getClass(), "onPageChange", "setting new page in session = " + changingPage.getName() + " templateName = " + changingPage.getTemplateName());
      if (TemplatesUtils.isCurrentTemplate(changingPage.getTemplateName())) {
        AppClientFactory.IMPL.getPortalSessionState().setCurrentPage(changingPage);
        goToPageAndUpdateLocation(changingPage);
      } else {
        AppClientFactory.IMPL.getPortalSessionState().setCurrentPage(changingPage);
        TemplatesUtils.changeCurrentTemplate(changingPage.getTemplateName(), new Delegate<Void>() {
          public void execute(Void element) {
            goToPageAndUpdateLocation(changingPage);
          }
        });
      }
    }
    
  }
  
  private void goToPageAndUpdateLocation(PortalPage page) {
    GwtUtils.setLocationHash(page.getCode());
    if (page.getName() != null)
      Window.setTitle(originalTitle + " - " + page.getName());
    getPlaceController().goTo(new PortalPagePlace(PortalPagePlace.VIEW, page));
  }

  private class StartingPortalContext {
    String initialTemplateName;
    boolean enquePortalPageChangeEvent = false;
    boolean portalInitialized = false;
    public StartingPortalContext() {
      this(null, false);
    }
    public StartingPortalContext(String initialTemplateName, boolean enquePortalPageChangeEvent) {
      this.initialTemplateName = initialTemplateName;
      this.enquePortalPageChangeEvent = enquePortalPageChangeEvent;
    }
    public String getInitialTemplateName() {
      return initialTemplateName;
    }
    public boolean enquePortalPageChangeEvent() {
      return enquePortalPageChangeEvent;
    }
    public void setPortalInitialized(boolean portalInitialized) {
      this.portalInitialized = portalInitialized;
    }
    public boolean isPortalInitialized() {
      return portalInitialized;
    }
  }

  public PortalSessionState getPortalSessionState() {
    return portalSessionState;
  }

  public void setPortalSessionState(PortalSessionState portalSessionState) {
    this.portalSessionState = portalSessionState;
  }
  
  @PreDestroy
  public void onDestroy() {
    GwtUtils.log(getClass(), "onDestroy", "funziona la destroy");
    portalSessionStateChangeRegistration.removeHandler();
    portalPageChangeRegistration.removeHandler();
  }
  
  public class PortalInitCompleteDelegate {
    public void onPortalInitComplete(StartingPortalContext initPortalContext) {
      CommonTemplatesUtils.initTemplates(RootPanel.get(), initPortalContext.getInitialTemplateName(), new SitePageTemplateRetriever(initPortalContext));
    }
  }
  
  public class SitePageTemplateRetriever implements CommonTemplatesUtils.PageTemplateRetriever {
    
    private StartingPortalContext initPortalContext;
    
    public SitePageTemplateRetriever(StartingPortalContext initPortalContext) {
      super();
      this.initPortalContext = initPortalContext;
    }

    @Override
    public void fetchTemplate(String templateName, final Delegate<PageTemplate> secondTemplateFoundDelegate) {
      GwtUtils.log(getClass(), "fetchTemplate", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      GwtUtils.log(getClass(), "fetchTemplate", "templateName = " + templateName);
      
      final Delegate<PageTemplate> firstTemplateFoundDelegate = new Delegate<PageTemplate>() {
        public void execute(PageTemplate pageTemplate) {
          GwtUtils.log(getClass(), "fetchTemplate", "found template " + pageTemplate);
          secondTemplateFoundDelegate.execute(pageTemplate);
          if (!(pageTemplate.getWidgetFactory() instanceof PagePortlet.Factory) && !initPortalContext.isPortalInitialized() && initPortalContext.enquePortalPageChangeEvent()) {
            initPortalContext.setPortalInitialized(true);
            if (portalSessionState.getCurrentPageId() != null) {
              PortalPageCacheUtil.goToPage(portalSessionState.getCurrentPageId(), true);
            }
          }
        }
      };
      
      PageTemplate pageTemplate = pageTemplateCacheGet(templateName);
      if (pageTemplate != null) {
        firstTemplateFoundDelegate.execute(pageTemplate);
      } else {
        PortalServiceAsync portalService = AppClientFactory.IMPL.getConcreteGinjector(SiteGinjector.class).getPortalService();
        portalService.getPage(templateName, new AsyncCallback<PageTemplate>() {
          public void onSuccess(PageTemplate pageTemplate) {
            pageTemplateCachePut(pageTemplate);
            firstTemplateFoundDelegate.execute(pageTemplate);
          }
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
        });
      }
    }
    
  }
  
  private static void pageTemplateCachePut(PageTemplate pageTemplate) {
    pageTamplateCache.put(pageTemplate.getName(), pageTemplate);
  }
  
  private static PageTemplate pageTemplateCacheGet(String pageTemplateName) {
    if (PAGE_TEMPLATE_CACHE_ENABLED) {
      return pageTamplateCache.get(pageTemplateName);
    } else {
      return null;
    }
  }
  
  private void initHashChangeListener() {
    HashChangeUtil.addHashChangeHandler(new Delegate<String>() {
      public void execute(String currentHash) {
        PortalPageCacheUtil.goToPageByCode(currentHash);
      }
    });
  }
  
}

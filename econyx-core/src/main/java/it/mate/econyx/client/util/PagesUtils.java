package it.mate.econyx.client.util;

import it.mate.econyx.client.events.PortalPageChangingEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PagesUtils {
  
  private static Map<String, PortalPage> pagesCache;
  
  private static PortalPageServiceAsync portalPageService = AppClientFactory.IMPL.getGinjector().getPortalPageService();
  
  private static String pageContentRenderFinishedDivId;
  
  public static void goToPageByCode(String pageCode) {
    ensureCache();
    boolean found = false;
    for (PortalPage page : pagesCache.values()) {
      if (page.getCode() != null && page.getCode().equals(pageCode)) {
        found = true;
        goToPage(page);
        break;
      }
    }
    if (!found) {
      findByCode(pageCode, new Delegate<PortalPage>() {
        public void execute(PortalPage page) {
          goToPage(page);
        }
      });
    }
  }
  
  public static void goToPage(PortalPage page) {
    if (page.getId() == null) {
      onPageRetrieved(page, false);
    } else {
      goToPage(page.getId(), true);
    }
  }
  
  public static void goToPage(PortalPage page, boolean forceReloadPage) {
    goToPage(page.getId(), forceReloadPage);
  }
  
  public static void goToPage(String pageId) {
    goToPage(pageId, true);
  }
  
  public static void reloadCurrentPage() {
    String currentPageId = AppClientFactory.IMPL.getPortalSessionState().getCurrentPageId();
    if (currentPageId != null) {
      goToPage(currentPageId, true);
    }
  }
  
  public static void goToPage(String pageId, final boolean forceReloadPage) {
    ensureCache();
    
    pageContentRenderFinishedDivId = "pageContentRenderFinishedDiv" + System.currentTimeMillis();
    if (PropertiesHolder.getBoolean("client.PortalPageClientUtil.goToPage.showWaitPanel")) {
      GwtUtils.showWaitPanel();
      GwtUtils.onAvailable(pageContentRenderFinishedDivId, new Delegate<Element>() {
        public void execute(Element element) {
          GwtUtils.hideWaitPanel(true);
        }
      });
    }
    
    PortalPage cachedPage = pagesCache.get(pageId);
    if (cachedPage != null && cachedPage instanceof PortalFolderPage) {
      PortalFolderPage portalFolderPage = (PortalFolderPage)cachedPage;
      if (portalFolderPage.getChildreen() != null && portalFolderPage.getChildreen().size() > 0) {
        // OK
      } else {
        cachedPage = null;
      }
    }
    if (cachedPage != null && cachedPage instanceof WebContentPage) {
      WebContentPage webContentPage = (WebContentPage)cachedPage;
      if (webContentPage.getHtmls() != null && webContentPage.getHtmls().size() > 0) {
        // OK
      } else {
        cachedPage = null;
      }
    }
    
    Delegate<PortalPage> pageRetrievedDelegate = new Delegate<PortalPage>() {
      public void execute(PortalPage page) {
        onPageRetrieved(page, forceReloadPage);
      }
    };
    
    if (cachedPage != null) {
      GwtUtils.log(PagesUtils.class, "goToPage", "found page in cache " + cachedPage);
      pageRetrievedDelegate.execute(cachedPage);
    } else {
      findById(pageId, true, true, true, pageRetrievedDelegate);
    }
  }
  
  private static void onPageRetrieved(PortalPage page, boolean forceReloadPage) {
    if (page == null)
      return;
    putInCache(page);
    if (!forceReloadPage && AppClientFactory.IMPL.getPortalSessionState() != null) {
      if (AppClientFactory.IMPL.getPortalSessionState().getCurrentPageCode() != null) {
        if (AppClientFactory.IMPL.getPortalSessionState().getCurrentPageCode().equals(page.getCode())) {
          return;
        }
      }
    }
    AppClientFactory.IMPL.getEventBus().fireEvent(new PortalPageChangingEvent(page, forceReloadPage));
  }
  
  public static String getPageContentRenderFinishedDivId() {
    return pageContentRenderFinishedDivId;
  }
  
  public static void putInCache(PortalPage page) {
    ensureCache();
    if (AppClientFactory.isSiteModule) {
      pagesCache.put(page.getId(), page);
    }
  }
  
  public static PortalPage getFromCache(String id) {
    ensureCache();
    if (AppClientFactory.isSiteModule) {
      return pagesCache.get(id);
    } else {
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  private static void ensureCache() {
    String cacheAttrName = PagesUtils.class.getName()+".pagesCache";
    pagesCache = (Map<String, PortalPage>) GwtUtils.getClientAttribute(cacheAttrName);
    if (pagesCache == null) {
      pagesCache = new HashMap<String, PortalPage>();
      GwtUtils.setClientAttribute(cacheAttrName, PagesUtils.pagesCache);
    }
  }

  private static void findById(String id, final boolean fetchChildreen, final boolean fetchProducts, final boolean fetchHtmls, final Delegate<PortalPage> delegate) {
    GwtUtils.log(PagesUtils.class, "findById", "before portalPageService.findById");
    portalPageService.findById(id, fetchChildreen, fetchProducts, fetchHtmls, new AsyncCallback<PortalPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalPage page) {
        GwtUtils.log(PagesUtils.class, "findById", "after portalPageService.findById");
        delegate.execute(page);
      }
    });
  }
  
  private static void findByCode(String code, final Delegate<PortalPage> delegate) {
    portalPageService.findByCode(code, new AsyncCallback<PortalPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalPage result) {
        delegate.execute(result);
      }
    });
  }
  
}

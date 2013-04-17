package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.AbstractPortalFolderPageDs;
import it.mate.econyx.server.model.impl.AbstractPortalPageDs;
import it.mate.econyx.server.model.impl.AbstractWebContentPageDs;
import it.mate.econyx.server.model.impl.HtmlWebContentDs;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.VirtualPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.model.impl.PortalPageTx;
import it.mate.econyx.shared.model.impl.WebContentPageTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.FindCallback;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.model.utils.OneToManyAdapterSupport;
import it.mate.gwtcommons.server.utils.CacheUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.server.utils.KeyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.KeyFactory;

@Service
public class PortalPageAdapterImpl implements PortalPageAdapter {

  private static Logger logger = Logger.getLogger(PortalPageAdapterImpl.class);
      
  @Autowired private Dao dao;
  
  OneToManyAdapterSupport<AbstractWebContentPageDs, HtmlContent> htmlRelationshipSupport;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    
    htmlRelationshipSupport = new OneToManyAdapterSupport<AbstractWebContentPageDs, HtmlContent>(
        dao, "getHtmls", "setHtmls", "initializeHtmls", "getAttachedHtml", "setAttachedHtml");
    
  }
  
  public enum PortalPageTypes {

  }
  
  public List<PortalPage> findAllRoot() {
    return internalfindAllRoot("parentId == null");
  }

  public List<PortalPage> findAllRootMenu() {
    return internalfindAllRoot("parentId == null && visibleInMenu == true");
  }

  public List<PortalPage> findAllRootExplorer() {
    return internalfindAllRoot("parentId == null && visibleInExplorer == true");
  }

  private List<PortalPage> internalfindAllRoot(String condition) {
    List<AbstractPortalPageDs> pageDsList = sortByOrderNm(dao.findList(AbstractPortalPageDs.class, condition, null, null));
    List<PortalPage> pages = CloneUtils.clone(pageDsList, PortalPageTx.class, PortalPage.class);
    return pages;
  }
  
  public List<PortalPage> findAll() {
    List<AbstractPortalPageDs> dss = sortByName(dao.findAll(AbstractPortalPageDs.class));
    return CloneUtils.clone(dss, PortalPageTx.class, PortalPage.class);
  }
  
  public PortalPage create(PortalPage page) {

    // 17/04/2013
    if (page instanceof VirtualPage) {
      return page;
    }
    
    AbstractPortalPageDs pageDs = CloneUtils.clone (page, AbstractPortalPageDs.class);
    pageDs.setKey(KeyFactory.createKey(pageDs.getClass().getSimpleName(), KeyUtils.getRandomUUID()));
    
    if (pageDs instanceof AbstractWebContentPageDs) {

      if (htmlRelationshipSupport == null) {
        AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)pageDs;
        List<HtmlContent> htmlList = webContentPageDs.getHtmls();
        List<HtmlContent> attachedHtmlList = new ArrayList<HtmlContent>();
        if (htmlList != null) {
          for (HtmlContent html : htmlList) {
            HtmlWebContentDs htmlDs = (HtmlWebContentDs)html;
            if (htmlDs.getId() == null) {
              htmlDs = dao.create(htmlDs);
              attachedHtmlList.add(htmlDs);
            }
          }
          webContentPageDs.setHtmls(attachedHtmlList);
        }
      } else {
        AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)pageDs;
        htmlRelationshipSupport.onBeforeCreate(webContentPageDs);
      }
      
    }
    
    pageDs = dao.create( pageDs );
    
    if (pageDs.getParentId() != null) {
      PortalPage parent = internalFindById(pageDs.getParentId(), true, true);
      if (parent instanceof AbstractPortalFolderPageDs) {
        AbstractPortalFolderPageDs parentDs = (AbstractPortalFolderPageDs)parent;
        parentDs.attachChild(pageDs);
        update(parentDs);
      }
    }
    
    return CloneUtils.clone(pageDs, PortalPageTx.class);
  }
  
  public PortalPage update(PortalPage page) {
    
    // 17/04/2013
    if (page instanceof VirtualPage) {
      return page;
    }
    
    AbstractPortalPageDs pageDs = CloneUtils.clone (page, AbstractPortalPageDs.class);
    
    /** BUGFIX PortalPageAdapterImpl@20120828 **/
    if (pageDs instanceof AbstractWebContentPageDs) {
      if (htmlRelationshipSupport == null) {
        WebContentPage webContentPage = (WebContentPage)page;
        AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)pageDs;
        if (webContentPage.getHtmls() == null || webContentPage.getHtmls().size() == 0) {
          if (webContentPage.getId() != null) {
            AbstractWebContentPageDs attachedPageDs = (AbstractWebContentPageDs)internalFindById(webContentPage.getId(), false, true);
            webContentPageDs.setHtmls(attachedPageDs.getHtmls());
          }
        }
      } else {
        AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)pageDs;
        htmlRelationshipSupport.onBeforeUpdate(webContentPageDs);
      }
    }
    
    pageDs = dao.update(pageDs);
    pageDs = updateChildreen(pageDs);
    return CloneUtils.clone (pageDs,PortalPageTx.class);
  }

  public void delete(PortalPage page) {
    
    // 17/04/2013
    if (page instanceof VirtualPage) {
      return;
    }
    
    AbstractPortalPageDs pageToDeleteDs = CloneUtils.clone (page, AbstractPortalPageDs.class);
    if (pageToDeleteDs.getParentId() != null) {
      AbstractPortalPageDs parentPageDs = internalFindById(pageToDeleteDs.getParentId(), true, false);
      if (parentPageDs instanceof AbstractPortalFolderPageDs) {
        AbstractPortalFolderPageDs parentFolderDs = (AbstractPortalFolderPageDs)parentPageDs;
        parentFolderDs.removeChild(pageToDeleteDs);
        dao.update(parentFolderDs);
      }
    }
    if (pageToDeleteDs instanceof AbstractWebContentPageDs) {
      if (htmlRelationshipSupport == null) {
        AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)internalFindById(pageToDeleteDs.getId(), false, true);
        if (webContentPageDs.getHtmls() != null) {
          for (HtmlContent html : webContentPageDs.getHtmls()) {
            HtmlWebContentDs htmlDs = (HtmlWebContentDs)html;
            dao.delete(htmlDs);
          }
        }
      } else {
        AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)pageToDeleteDs;
        htmlRelationshipSupport.onBeforeDelete(webContentPageDs);
      }
    }
    dao.delete(pageToDeleteDs);
  }
  
  public PortalPage findById (String id) {
    return CloneUtils.clone(dao.findById(AbstractPortalPageDs.class, id), PortalPageTx.class);
  }
  
  public PortalPage findById (String pageId, final boolean resolveChildreen, final boolean resolveProducts, final boolean resolveHtmls) {
    AbstractPortalPageDs pageDs = internalFindById(pageId, resolveChildreen, resolveHtmls);
    PortalPage pageTx = CloneUtils.clone(pageDs, PortalPageTx.class);
    return pageTx;
  }
  
  public PortalPage findByCode(String code) {
    AbstractPortalPageDs pageDs = dao.findSingle(AbstractPortalPageDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code);
    return CloneUtils.clone(pageDs, PortalPageTx.class);
  }

  private AbstractPortalPageDs internalFindById (String pageId, boolean resolveChildreen, boolean resolveHtmls) {
    return internalFindById(AbstractPortalPageDs.class, pageId, resolveChildreen, resolveHtmls);
  }
  
  private <P extends AbstractPortalPageDs> P internalFindById (Class<P> resultClass, String pageId, final boolean resolveChildreen, final boolean resolveHtmls) {
    
    if (pageId == null)
      return null;
    
    if (pageId != null) {
      Object cachedPage = CacheUtils.get(pageId);
      if (cachedPage != null) {
        if (resolveChildreen && cachedPage instanceof AbstractPortalFolderPageDs) {
          AbstractPortalFolderPageDs portalFolderPageDs = (AbstractPortalFolderPageDs)cachedPage;
          if (portalFolderPageDs.getChildreen() == null || portalFolderPageDs.getChildreen().size() == 0) {
            CacheUtils.deleteByKey(pageId);
          }
        }
        if (resolveHtmls && cachedPage instanceof AbstractWebContentPageDs) {
          AbstractWebContentPageDs webContentPageDs = (AbstractWebContentPageDs)cachedPage;
          if (webContentPageDs.getHtmls() == null || webContentPageDs.getHtmls().size() == 0) {
            CacheUtils.deleteByKey(pageId);
          }
        }
      }
    }
    
    FindContext<P> context = new FindContext<P>(resultClass);
    context.setId(pageId);
//  context.setNotUseCache(resolveChildreen || resolveHtmls);
    context.setCallback(new FindCallback<P>() {
      public void processResultsInTransaction(P pageDs) {
        if (pageDs instanceof AbstractPortalFolderPageDs && resolveChildreen) {
          ((AbstractPortalFolderPageDs)pageDs).resolveChildreen();
        }
        if (pageDs instanceof AbstractWebContentPageDs && resolveHtmls) {
          ((AbstractWebContentPageDs)pageDs).initializeHtmls();
        }
      }
    });
    
    P pageDs = dao.findById(context);

    return pageDs;
  }
  
  public PortalFolderPage fetchChildreen (PortalFolderPage parent) {
    return (PortalFolderPage)findById(parent.getId(), true, false, false);
  }
  
  public ProductFolderPage fetchProducts (ProductFolderPage parent) {
    return (ProductFolderPage)findById(parent.getId(), false, true, false);
  }
  
  public WebContentPage fetchHtmls (WebContentPage contentPage) {
    return (WebContentPage)findById(contentPage.getId(), false, false, true);
  }
  
  private AbstractPortalPageDs updateChildreen (AbstractPortalPageDs parent) {
    if (parent instanceof AbstractPortalFolderPageDs) {
      AbstractPortalFolderPageDs parentFolder = (AbstractPortalFolderPageDs)parent;
      if (parentFolder.getChildreen() != null) {
        for (PortalPage child : parentFolder.getChildreen()) {
          AbstractPortalPageDs ds = (AbstractPortalPageDs)child;
          update(ds);
        }
      }
      return parent;
    } else {
      return parent;
    }
  }
  
  @Override
  public WebContentPage updateHtmlContent (String pageId, HtmlContent html) {
    if (pageId == null)
      return null;
    
    AbstractWebContentPageDs pageDs = null;
    
    if (htmlRelationshipSupport == null) {
      pageDs = internalFindById(AbstractWebContentPageDs.class, pageId, false, true);
      HtmlWebContentDs htmlDs = (HtmlWebContentDs)pageDs.getHtmlContent(html.getType());
      if (htmlDs == null) {
        htmlDs = CloneUtils.clone(html, HtmlWebContentDs.class);
        htmlDs = dao.create(htmlDs);
        pageDs.attachHtml(htmlDs);
        pageDs = dao.update(pageDs);
      } else {
        htmlDs.setContent(html.getContent());
        htmlDs = dao.update(htmlDs);
      }
    } else {
      pageDs = internalFindById(AbstractWebContentPageDs.class, pageId, false, true);
      HtmlContent detachedHtml = CloneUtils.clone(html, HtmlWebContentDs.class);
      pageDs = htmlRelationshipSupport.onUpdateItem(pageDs, detachedHtml);
    }
    
    return CloneUtils.clone(pageDs, WebContentPageTx.class);
  }
  
  @Override
  public void printPagesToXml() {

  }
  
  public PortalPage resolveAllDependencies(PortalPage page) {
    resolvePageDependencies(page, true, true);
    return page;
  }

  private void resolvePageDependencies(PortalPage page, boolean resolveChildreen, boolean resolveHtmls) {
    PortalPage resolvedPage = findById(page.getId(), resolveChildreen, true, resolveHtmls);
    if (resolveHtmls && resolvedPage instanceof WebContentPage) {
      WebContentPage webContentPage = (WebContentPage)resolvedPage;
      ((WebContentPage)page).setHtmls(webContentPage.getHtmls());
    }
    if (resolveChildreen && resolvedPage instanceof PortalFolderPage) {
      PortalFolderPage folderPage = (PortalFolderPage)resolvedPage;
      ((PortalFolderPage)page).setChildreen(folderPage.getChildreen());
      for (PortalPage childPage : folderPage.getChildreen()) {
        resolvePageDependencies(childPage, resolveChildreen, resolveHtmls);
      }
    }
  }
  
  private List<AbstractPortalPageDs> sortByName(List<AbstractPortalPageDs> pages) {
    if (pages == null)
      return null;
    Collections.sort(pages, new Comparator<AbstractPortalPageDs>() {
      public int compare(AbstractPortalPageDs p1, AbstractPortalPageDs p2) {
        if (p1.getName() == null)
          return -1;
        if (p2.getName() == null)
          return 1;
        return p1.getName().compareTo(p2.getName());
      }
    });
    return pages;
  }
  
  private List<AbstractPortalPageDs> sortByOrderNm(List<AbstractPortalPageDs> pages) {
    if (pages == null)
      return null;
    Collections.sort(pages, new Comparator<AbstractPortalPageDs>() {
      public int compare(AbstractPortalPageDs p1, AbstractPortalPageDs p2) {
        if (p1.getOrderNm() == null)
          return -1;
        if (p2.getOrderNm() == null)
          return 1;
        return p1.getOrderNm().compareTo(p2.getOrderNm());
      }
    });
    return pages;
  }
  
  public void exportToXml() {

  }
  
  public void loadFromXml() {
    deleteAllPages();
    exportToXml();
  }
  
  private void deleteAllPages() {
    List<PortalPage> pages = findAll();
    for (PortalPage page : pages) {
      delete(page);
    }
  }
  
}

package it.mate.econyx.server.services.impl;

import it.mate.commons.server.utils.CacheUtils;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.KeyUtils;
import it.mate.econyx.server.model.impl.ProduttoreDs;
import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.services.BlogAdapter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProducerFolderPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.model.impl.ArticlePageTx;
import it.mate.econyx.shared.model.impl.BlogDiscussionPageTx;
import it.mate.econyx.shared.model.impl.ProducerFolderPageTx.ProducerProductPageTx;
import it.mate.econyx.shared.services.PortalPageService;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PortalPageServiceImpl extends RemoteServiceServlet implements PortalPageService {

  private static Logger logger = Logger.getLogger(PortalPageServiceImpl.class);
  
  private PortalPageAdapter adapter;
  
  private ArticleAdapter articleAdapter;
      
  private BlogAdapter blogAdapter;
  
  private ProductAdapter productAdapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getPortalPageAdapter();
    this.articleAdapter = AdaptersUtil.getArticleAdapter();
    this.blogAdapter = AdaptersUtil.getBlogAdapter();
    this.productAdapter = AdaptersUtil.getProductAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  public List<PortalPage> findAll() {
    List<PortalPage> results = adapter.findAll();
    if (results != null) {
      for (PortalPage page : results) {
        postProcessProducerFolderPage(page);
      }
    }
    return results;
  }

  @Override
  public PortalPage update(PortalPage entity) {
    return adapter.update(entity);
  }

  @Override
  public void delete(PortalPage entity) {
    adapter.delete(entity);
  }

  @Override
  public PortalPage create(PortalPage entity) {
    return adapter.create(entity);
  }

  @Override
  public PortalPage findById(String id) {
    PortalPage virtualPage = findVirtualPageById(id);
    if (virtualPage != null) {
      return virtualPage;
    }
    PortalPage result = adapter.findById(id);
    postProcessProducerFolderPage(result);
    return result;
  }
  
  @Override
  public PortalPage findById(String id, boolean resolveChildreen, boolean resolveProducts, boolean resolveHtmls) {
    PortalPage virtualPage = findVirtualPageById(id);
    if (virtualPage != null) {
      return virtualPage;
    }
    PortalPage result = adapter.findById(id, resolveChildreen, resolveProducts, resolveHtmls);
    if (resolveChildreen) {
      postProcessProducerFolderPage(result);
    }
    return result;
  }
  
  @Override
  public PortalFolderPage fetchChildreen(PortalFolderPage page) {
    PortalFolderPage result = adapter.fetchChildreen(page);
    postProcessProducerFolderPage(result);
    return result;
  }

  public WebContentPage fetchHtmls (WebContentPage contentPage) {
    return adapter.fetchHtmls(contentPage);
  }
  
  public WebContentPage updateHtmlContent (String pageId, HtmlContent content) {
    return adapter.updateHtmlContent(pageId, content);
  }
  
  @Override
  public List<PortalPage> findAllRoot() {
    return adapter.findAllRoot();
  }

  @Override
  public List<PortalPage> findAllRootMenu() {
    return adapter.findAllRootMenu();
  }

  @Override
  public List<PortalPage> findAllRootExplorer() {
    return adapter.findAllRootExplorer();
  }

  @Override
  public PortalPage newInstance(String classname) {
    try {
      return (PortalPage)Class.forName(classname).newInstance();
    } catch (Exception ex) {
      logger.error("error", ex);
      return null;
    }
  }

  @Override
  public PortalPage findByCode(String code) {
    PortalPage page = adapter.findByCode(code);
    if (page != null) {
      postProcessProducerFolderPage(page);
    }
    if (page == null) {
      page = getArticlePageByCode(code);
    }
    if (page == null) {
      page = getBlogDiscussionPageByCode(code);
    }
    if (page == null) {
      page = getProducerProductPageByCode(code);
    }
    if (page == null) {
//    logger.error("error", new ServiceException("Page not found " + code));
      logger.error("error - Page not found " + code);
    }
    return page;
  }
  
  @Override
  public void removePageFromCache(String pageId) {
    PortalPage virtualPage = findVirtualPageById(pageId);
    if (virtualPage == null) {
      CacheUtils.deleteByKey(KeyUtils.castToKey(pageId));
    }
  }
  
  private PortalPage findVirtualPageById (String id) {
    if (ArticlePageTx.isVirtualPageId(id)) {
      return getArticlePageByCode(ArticlePageTx.getEntityCodeFromPageId(id));
    }
    if (BlogDiscussionPageTx.isVirtualPageId(id)) {
      return getBlogDiscussionPageByCode(BlogDiscussionPageTx.getEntityCodeFromPageId(id));
    }
    if (ProducerProductPageTx.isVirtualPageId(id)) {
      return getProducerProductPageByCode(ProducerProductPageTx.getEntityCodeFromPageId(id));
    }
    return null;
  }

  private PortalPage getArticlePageByCode(String code) {
    Article article = articleAdapter.findByCode(code);
    if (article != null) {
      return new ArticlePageTx(article);
    }
    return null;
  }

  private PortalPage getBlogDiscussionPageByCode(String code) {
    BlogDiscussion discussion = blogAdapter.findDiscussionByCode(code);
    if (discussion != null) {
      return new BlogDiscussionPageTx(discussion);
    }
    return null;
  }

  private PortalPage getProducerProductPageByCode(String code) {
    Articolo product = productAdapter.findProductByCode(code);
    if (product != null) {
      return new ProducerProductPageTx(product);
    }
    return null;
  }
  
  private PortalPage postProcessProducerFolderPage (PortalPage page) {
    if (page != null && page instanceof ProducerFolderPage) {
      ProducerFolderPage producerFolderPage = (ProducerFolderPage)page;
      Produttore producer = producerFolderPage.getEntity();
      if (producer != null) {
        if (producer.getProducts() == null || producer.getProducts().size() == 0) {
          List<Articolo> products = productAdapter.findProductsByProducerId(producer.getId());
          if (products != null) {
            producer.setProducts(products);
            CacheUtils.put(CloneUtils.clone(producer, ProduttoreDs.class));
            producerFolderPage.setEntity(producer);
          }
        }
      }
    }
    return page;
  }

}

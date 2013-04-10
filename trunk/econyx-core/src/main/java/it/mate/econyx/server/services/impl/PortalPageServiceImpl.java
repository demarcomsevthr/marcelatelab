package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.services.BlogAdapter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.model.impl.ArticlePageTx;
import it.mate.econyx.shared.model.impl.BlogDiscussionPageTx;
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
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getPortalPageAdapter();
    this.articleAdapter = AdaptersUtil.getArticleAdapter();
    this.blogAdapter = AdaptersUtil.getBlogAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  public List<PortalPage> findAll() {
    return adapter.findAll();
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
    if (ArticlePageTx.isVirtualId(id)) {
      return getArticlePageByCode(ArticlePageTx.getEntityCodeFromId(id));
    }
    if (BlogDiscussionPageTx.isVirtualId(id)) {
      return getBlogDiscussionPageByCode(BlogDiscussionPageTx.getEntityCodeFromId(id));
    }
    return adapter.findById(id);
  }
  
  @Override
  public PortalPage findById(String id, boolean resolveChildreen, boolean resolveProducts, boolean resolveHtmls) {
    if (ArticlePageTx.isVirtualId(id)) {
      return getArticlePageByCode(ArticlePageTx.getEntityCodeFromId(id));
    }
    if (BlogDiscussionPageTx.isVirtualId(id)) {
      return getBlogDiscussionPageByCode(BlogDiscussionPageTx.getEntityCodeFromId(id));
    }
    return adapter.findById(id, resolveChildreen, resolveProducts, resolveHtmls);
  }

  @Override
  public PortalFolderPage fetchChildreen(PortalFolderPage page) {
    return adapter.fetchChildreen(page);
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
    if (page == null) {
      page = getArticlePageByCode(code);
    }
    if (page == null) {
      page = getBlogDiscussionPageByCode(code);
    }
    return page;
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

}

package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.server.util.ServletThreadUtils;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.services.ArticleService;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service (".articleService")
public class ArticleServiceImpl extends RemoteServiceServlet implements ArticleService {

  private static Logger logger = Logger.getLogger(ArticleServiceImpl.class);
  
  private ArticleAdapter adapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getArticleAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  protected void onBeforeRequestDeserialized(String serializedRequest) {
    ServletThreadUtils.set(getThreadLocalRequest(), getThreadLocalResponse());
    PortalSessionStateServerUtils.setInThread(AdaptersUtil.getGeneralAdapter().retrievePortalSessionState(getThreadLocalRequest()));
    super.onBeforeRequestDeserialized(serializedRequest);
  }
  
  @Override
  public List<ArticleFolder> findAll() throws ServiceException {
    return adapter.findAll();
  }
  
  @Override
  public ArticleFolder create(ArticleFolder entity) throws ServiceException {
    return adapter.create(entity);
  }

  @Override
  public ArticleFolder update(ArticleFolder entity) throws ServiceException {
    return adapter.update(entity);
  }

  @Override
  public void delete(ArticleFolder entity) throws ServiceException {
    adapter.delete(entity);
  }

  @Override
  public ArticleFolder findById(String id) throws ServiceException {
    return adapter.findById(id);
  }

  @Override
  public Article findArticleById(String id, boolean fetchComments) {
    return adapter.findArticleById(id, fetchComments);
  }

  @Override
  public Article addCommentToArticle(String id, ArticleComment comment) {
    return adapter.addCommentToArticle(id, comment);
  }
  
  @Override
  public Article updateArticle(Article article) {
    return adapter.update(article);
  }
  
}

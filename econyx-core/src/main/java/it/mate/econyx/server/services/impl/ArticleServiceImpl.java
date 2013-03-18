package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
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

}

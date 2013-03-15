package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.services.ArticleService;

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

}

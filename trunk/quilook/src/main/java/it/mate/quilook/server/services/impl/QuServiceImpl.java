package it.mate.quilook.server.services.impl;

import it.mate.econyx.server.services.impl.ArticleServiceImpl;
import it.mate.quilook.server.services.QuAdapter;
import it.mate.quilook.shared.services.QuService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class QuServiceImpl extends RemoteServiceServlet implements QuService {

  private static Logger logger = Logger.getLogger(ArticleServiceImpl.class);
  
  QuAdapter adapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    this.adapter = context.getBean(QuAdapter.class);
    logger.debug("initialized " + this);
  }

  @Override
  public String getQuMessage() {
    return adapter.getQuMessage();
  }

  
}

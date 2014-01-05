package it.mate.stickmail.server.servlet;

import it.mate.stickmail.server.services.AdapterUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdapterUtil.initContext(WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()));
  }

}

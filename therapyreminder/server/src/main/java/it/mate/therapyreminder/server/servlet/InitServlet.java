package it.mate.therapyreminder.server.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    /*
    AdapterUtil.initContext(WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()));
    */
  }

}

package it.mate.econyx.server.servlets;

import it.mate.econyx.server.services.PortalUserAdapter;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class UserActivationServlet extends HttpServlet {
  
  private static Logger logger = Logger.getLogger(UserActivationServlet.class);
  
  private PortalUserAdapter portalUserAdapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    portalUserAdapter =  context.getBean(PortalUserAdapter.class);
  }
  
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String tid = request.getParameter("tid");
      portalUserAdapter.activateUserByToken(tid);
      request.getRequestDispatcher("/user-activation.jsp").forward(request, response);
    } catch (Exception ex) {
      logger.error("error", ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
  
}

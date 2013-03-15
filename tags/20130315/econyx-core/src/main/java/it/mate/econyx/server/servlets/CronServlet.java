package it.mate.econyx.server.servlets;

import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.PortalPageAdapter;

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
public class CronServlet extends HttpServlet {

  private static Logger logger = Logger.getLogger(CronServlet.class);
  
  private PortalPageAdapter portalPageAdapter;
  
  private PortalDataExporter portalDataMarshaller;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    portalPageAdapter =  context.getBean(PortalPageAdapter.class);
    this.portalDataMarshaller = context.getBean(PortalDataExporter.class);
    logger.debug("initialized " + this);
  }
  
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String op = request.getParameter("op");
      logger.debug("executing cron servlet with op " + op);
      
      String cronHeader = request.getHeader("X-AppEngine-Cron");
      if (!"true".equals(cronHeader)) {
        logger.error("missing cron header - cannot executing cron servlet");
        return;
      }
      
      if ("exportPagesData".equalsIgnoreCase(op)) {
        logger.debug("exporting pages data");
        portalPageAdapter.exportToXml();
      } else if ("importPortalData".equalsIgnoreCase(op)) {
        portalDataMarshaller.load();
      } else {
        logger.error("unknown op receivede - cannot executing cron servlet");
      }
      
      response.setStatus(HttpServletResponse.SC_OK);
      
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
  }
  
}

package it.mate.econyx.server.servlets;

import it.mate.econyx.server.model.PortalDataExportModel;
import it.mate.econyx.server.services.PortalDataExporter;

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
public class ExportServlet extends HttpServlet {
  
  private static Logger logger = Logger.getLogger(ExportServlet.class);
  
  private PortalDataExporter portalDataMarshaller;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    portalDataMarshaller = context.getBean(PortalDataExporter.class);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int exportMode = getIntParameter(request, "exportMode", PortalDataExportModel.LOAD_METHOD_ALL);
      String xml = portalDataMarshaller.unload(exportMode);
      response.setContentType("text/xml");
      response.getWriter().print(xml);
    } catch (Exception ex) {
      logger.error("error", ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
  
  private int getIntParameter(HttpServletRequest request, String name, int defValue) {
    String value = request.getParameter(name);
    if (value != null) {
      return Integer.parseInt(value);
    }
    return defValue;
  }
  
}

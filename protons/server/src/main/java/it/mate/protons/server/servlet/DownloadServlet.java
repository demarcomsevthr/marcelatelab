package it.mate.protons.server.servlet;

import it.mate.commons.server.utils.LoggingUtils;
import it.mate.protons.server.model.PrincipioAttivoDs;
import it.mate.protons.server.services.RemoteAdapter;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet {
  
  RemoteAdapter adapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    adapter = context.getBean(RemoteAdapter.class);
    LoggingUtils.debug(getClass(), "initialized " + this);
    LoggingUtils.debug(getClass(), "adapter = " + adapter);
  }

  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      String name = request.getParameter("name");
      
      LoggingUtils.debug(getClass(), String.format("downloading name=%s", name));
      PrincipioAttivoDs principio = adapter.findPrincipioAttivoByName(name);
      if (principio == null) {
        LoggingUtils.error(getClass(), "NOT FOUND");
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
      } else {
        
        LoggingUtils.debug(getClass(), String.format("downloading %s", principio));
        
//      response.setContentType("image/png");
        response.setContentType("application/octet-stream");
        response.getOutputStream().write(principio.getContent().getBytes());
        
      }
      
    } catch (Exception ex) {
      LoggingUtils.error(getClass(), ex.getMessage());
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
    
    
  }
  
  
}

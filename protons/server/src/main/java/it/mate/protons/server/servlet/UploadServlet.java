package it.mate.protons.server.servlet;

import it.mate.commons.server.utils.BlobUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.protons.server.model.PrincipioAttivoDs;
import it.mate.protons.server.services.RemoteAdapter;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Blob;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
  
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String op = null;
      String fileName = null;
      Blob fileBlob = null;
      
      ServletFileUpload fileUpload = new ServletFileUpload();
      FileItemIterator iter = fileUpload.getItemIterator(request);
      while (iter.hasNext()) {
        FileItemStream item = iter.next();
        if (item.isFormField()) {
          if ("op".equals(item.getFieldName())) {
            op = Streams.asString(item.openStream());
          } else if ("fileName".equals(item.getFieldName())) {
            fileName = Streams.asString(item.openStream());
          }
        } else {
          fileBlob = BlobUtils.inputStreamToBlob(item.openStream());
        }
      }
      
      if ("FILE_UPLOAD".equals(op)) {
        LoggingUtils.debug(getClass(), String.format("received name=%s lenght=%s", fileName, fileBlob.getBytes().length));
        PrincipioAttivoDs principio = adapter.findPrincipioAttivoByName(fileName);
        if (principio == null) {
          principio = new PrincipioAttivoDs();
          principio.setName(fileName);
        }
        principio.setContent(fileBlob);
        adapter.savePrincipioAttivoDs(principio);
        LoggingUtils.debug(getClass(), "UPLOAD DONE");
        response.getOutputStream().print("UPLOAD DONE");
      }
      
    } catch (Exception ex) {
      LoggingUtils.error(getClass(), ex.getMessage());
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
  
  
}

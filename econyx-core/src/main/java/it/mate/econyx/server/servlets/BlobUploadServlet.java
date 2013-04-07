package it.mate.econyx.server.servlets;

import it.mate.econyx.server.model.impl.DocumentDs;
import it.mate.econyx.server.services.DocumentAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class BlobUploadServlet extends HttpServlet {
  
  private static Logger logger = Logger.getLogger(BlobUploadServlet.class);
  
  private DocumentAdapter documentAdapter;
  
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    documentAdapter = context.getBean(DocumentAdapter.class);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String op = null;
      String objId = null;

      op = request.getParameter("op");
      objId = request.getParameter("objId");
      
      
      if ("DOCUMENT_UPLOAD".equals(op)) {
        
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        
        BlobKey blobKey = blobs.get("file").get(0);
        
        DocumentDs document = documentAdapter.findDocumentDsById(objId);
        
        document.setBlobKey(blobKey.getKeyString());

        /*
        DocumentContentDs content = (DocumentContentDs)document.getContent();
        if (content == null) {
          content = new DocumentContentDs();
          document.setContent(content);
        }
        content.setContentBlob(fileBlob);
        */
        documentAdapter.updateDocument(document);
      }
      
    } catch (Exception ex) {
      logger.error("error", ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
  
  
}

package it.mate.econyx.server.servlets;

import it.mate.commons.server.utils.BlobUtils;
import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.DocumentAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.tasks.PortalDataUploadDeferredTask;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

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
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
  
  private static Logger logger = Logger.getLogger(UploadServlet.class);
  
  private ProductAdapter productAdapter;
  
  private ImageAdapter imageAdapter;
  
  private DocumentAdapter documentAdapter;
  
  private CustomAdapter customAdapter;
  
  private PortalDataExporter portalDataMarshaller;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    productAdapter =  context.getBean(ProductAdapter.class);
    imageAdapter =  context.getBean(ImageAdapter.class);
    customAdapter =  context.getBean(CustomAdapter.class);
    portalDataMarshaller = context.getBean(PortalDataExporter.class);
    documentAdapter = context.getBean(DocumentAdapter.class);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String op = null;
      String imageType = null;
      String imageSize = null;
      String objId = null;
      boolean useCustomAdaper = false;
      Blob fileBlob = null;
      
      ServletFileUpload fileUpload = new ServletFileUpload();
      FileItemIterator iter = fileUpload.getItemIterator(request);
      while (iter.hasNext()) {
        FileItemStream item = iter.next();
        if (item.isFormField()) {
          if ("op".equals(item.getFieldName())) {
            op = Streams.asString(item.openStream());
          } else if ("imageType".equals(item.getFieldName())) {
            imageType = Streams.asString(item.openStream());
          } else if ("imageSize".equals(item.getFieldName())) {
            imageSize = Streams.asString(item.openStream());
          } else if ("objId".equals(item.getFieldName())) {
            objId = Streams.asString(item.openStream());
          } else if ("useCustomAdapter".equals(item.getFieldName())) {
            useCustomAdaper = Boolean.parseBoolean(Streams.asString(item.openStream()));
          }
        } else {
          fileBlob = BlobUtils.inputStreamToBlob(item.openStream());
        }
      }
      
      if ("IMAGE_UPLOAD".equals(op)) {
        if ("product".equals(imageType)) {
          AbstractArticoloDs product = productAdapter.fetchImages(objId);
          logger.debug(String.format("updating blob on %s id %s size %s lenght %s", imageType, objId, imageSize, fileBlob.getBytes().length));
          if (ImageContent.Type.SMALL.name().equalsIgnoreCase(imageSize)) {
            product.setImageSmall(fileBlob);
            productAdapter.updateDs(product, true, false);
          } else if (ImageContent.Type.MEDIUM.name().equalsIgnoreCase(imageSize)) {
            product.setImageMedium(fileBlob);
            productAdapter.updateDs(product, true, false);
          }
        } else if ("image".equals(imageType)) {
          ImageDs imageDs = imageAdapter.findByCode(objId);
          imageDs.setContent(fileBlob);
          imageAdapter.updateDs(imageDs);
        } else if (useCustomAdaper) {
          String result = customAdapter.uploadImage(imageType, imageSize, objId, fileBlob, request.getSession());
          response.getOutputStream().print(result);
        } else {
          throw new ServiceException(String.format("Image type %s non previsto", imageType));
        }
      } else if ("DOCUMENT_UPLOAD".equals(op)) {
        /*
        DocumentDs document = documentAdapter.findDocumentDsById(objId);
        DocumentContentDs content = (DocumentContentDs)document.getContent();
        if (content == null) {
          content = new DocumentContentDs();
          document.setContent(content);
        }
        content.setContentBlob(fileBlob);
        documentAdapter.updateDocument(document);
        */
      } else if ("PORTAL_DATA_UPLOAD".equals(op)) {
        if (PropertiesHolder.getBoolean("uploadServlet.deferredPortalDataUpload")) {
          Queue queue = QueueFactory.getDefaultQueue();
          queue.add(TaskOptions.Builder.withPayload(new PortalDataUploadDeferredTask(new String(fileBlob.getBytes()))));
        } else {
          logger.debug("uploading portal data file");
          portalDataMarshaller.load(new String(fileBlob.getBytes()));
        }
      }
      
    } catch (Exception ex) {
      logger.error("error", ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
  
  
}

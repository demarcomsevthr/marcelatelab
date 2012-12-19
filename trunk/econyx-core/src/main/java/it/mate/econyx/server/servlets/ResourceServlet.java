package it.mate.econyx.server.servlets;

import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.services.ResourcesService;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.gwtcommons.server.utils.SpringUtils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {

  private static Logger logger = Logger.getLogger(ResourceServlet.class);
  
  private ProductAdapter productAdapter;
  
  private ImageAdapter imageAdapter;
  
  private ResourcesService resourceService;

  private byte[] blankImage = null;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    productAdapter =  context.getBean(ProductAdapter.class);
    imageAdapter =  context.getBean(ImageAdapter.class);
    resourceService = context.getBean(ResourcesService.class);
    logger.debug("initialized " + this);

    try {
      blankImage = IOUtils.toByteArray(SpringUtils.getClassPathResource("stock-images/blank.png").getInputStream());
    } catch (Exception ex) { }
    
  }
  
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String op = request.getParameter("op");
      if ("PRI".equals(op)) {
        String productId = request.getParameter("productId");
        String imageSize = request.getParameter("imageSize");
        if (productId != null && !productId.equals("null")) {
          response.setContentType("image/jpg");
//        AbstractArticoloDs product = productAdapter.fetchImages(productId, true);
          AbstractArticoloDs product = productAdapter.fetchImages(productId, false);
          if (ImageContent.Type.SMALL.name().equalsIgnoreCase(imageSize)) {
            if (product.getImageSmall() != null)
              response.getOutputStream().write(product.getImageSmall().getBytes());
            else
              response.getOutputStream().write(blankImage);
          } else if (ImageContent.Type.MEDIUM.name().equalsIgnoreCase(imageSize)) {
            if (product.getImageMedium() != null)
              response.getOutputStream().write(product.getImageMedium().getBytes());
          }
          response.getOutputStream().flush();
        }
      } else if ("IMG".equals(op)) {
        String imageCode = request.getParameter("code");
        ImageDs imageDs = imageAdapter.findByCode(imageCode);
        if (imageDs.getContent() != null && imageDs.getContent().getBytes() != null) {
          response.getOutputStream().write(imageDs.getContent().getBytes());
        }
      } else if ("THI".equals(op)) {
        String themeId = request.getParameter("id");
        String imgPath = request.getParameter("path");
        response.setContentType("image/jpg");
        response.getOutputStream().write(resourceService.getZippedThemeResourceAsStream(themeId, imgPath));
      }
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
  }
  
}

package it.mate.econyx.server.servlets;

import it.mate.econyx.server.model.impl.DocumentDs;
import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.DocumentAdapter;
import it.mate.econyx.server.services.ExcelAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.services.ReportAdapter;
import it.mate.econyx.server.services.ResourcesService;
import it.mate.econyx.server.util.CacheConstants;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.server.utils.CacheUtils;
import it.mate.gwtcommons.server.utils.LoggingUtils;
import it.mate.gwtcommons.server.utils.PdfSession;
import it.mate.gwtcommons.server.utils.StringUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.server.services.PortalServiceAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@Controller
public class RestController {
  
  private final static Logger logger = Logger.getLogger(RestController.class);

  @Autowired private GeneralAdapter generalAdapter;
  
  @Autowired private PortalPageAdapter portalPageAdapter;
  
  @Autowired private ResourcesService resourceService;
  
  @Autowired private ImageAdapter imageAdapter;
  
  @Autowired private OrderAdapter orderAdapter;
  
  @Autowired private ProductAdapter productAdapter;
  
  @Autowired private ReportAdapter reportAdapter;
  
  @Autowired private ExcelAdapter excelAdapter;
  
  @Autowired private PortalServiceAdapter portalServiceAdapter;
  
  @Autowired private DocumentAdapter documentAdapter;
  
  
  private static boolean probeActive = false;
  
  private static boolean probeStopRequested = false;
  
  
  @RequestMapping ("/pg/{pageCode}")
  public void forwardToPage(@PathVariable ("pageCode") String pageCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.debug("pageCode = " + pageCode);
    PortalPage page = portalPageAdapter.findByCode(pageCode);
    PortalSessionState state = new PortalSessionState(PortalSessionState.MODULE_SITE, page);
    state.setTemplateName(page.getTemplateName());
    generalAdapter.storePortalSessionState(request, state);
    request.setAttribute("pageName", page.getName());
    request.getRequestDispatcher("/site-nobanner.jsp").forward(request, response);
//  return new ModelAndView("forward:/");
  }
  
  @RequestMapping ("/th/{themeId}/**")
  public void getThemeImage(@PathVariable ("themeId") String themeId, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.debug("themeId = " + themeId);
    String resPath = StringUtils.substringSlice(request.getRequestURI(), String.format("/%s/", themeId));
    logger.debug("resPath = " + resPath);
    if (resPath == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing path");
      return;
    }
    response.setContentType("image/jpg");
    response.getOutputStream().write(resourceService.getZippedThemeResourceAsStream(themeId, resPath));
  }
  
  @RequestMapping ("/im/{imgCode}")
  public void getImage(@PathVariable ("imgCode") String imgCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ImageDs imageDs = imageAdapter.findByCode(imgCode);
    if (imageDs.getContent() != null && imageDs.getContent().getBytes() != null) {
      response.getOutputStream().write(imageDs.getContent().getBytes());
    }
  }
  
  @RequestMapping ("/doc/{docCode}")
  public void getDocument(@PathVariable ("docCode") String docCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DocumentDs document = documentAdapter.findDocumentDsByCode(docCode);
    BlobKey blobKey = new BlobKey(document.getBlobKey());
    response.setContentType("application/pdf");
    BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
  }
  
  @RequestMapping ("/probe/{command}/{delaySeconds}")
  public void startProbe(@PathVariable ("command") String command, 
      @PathVariable ("delaySeconds") final String delaySeconds, 
      HttpServletResponse response) throws Exception {
    response.getOutputStream().print("PROBE RESPONSE");
    if ("start".equals(command)) {
      final int delay = Integer.parseInt(delaySeconds);
      probeStopRequested = false;
      if (probeActive) {
        logger.debug("probe already started");
      } else {
        probeActive = true;
        try {
          logger.debug("received start probe command with delay = " + delay);
          Thread.sleep(delay * 1000);
          if (!probeStopRequested) {
            logger.debug("enqueing new start probe command with delay = " + delay);
            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(TaskOptions.Builder.withUrl("/re/probe/start/" + delaySeconds));
          }
        } catch (Exception e) {
          //
        } finally {
          probeActive = false;
        }
      }
    } else if ("stop".equals(command)) {
      probeStopRequested = true;
    }
  }
  
  @RequestMapping ("/ping")
  public void aliveCheck(HttpServletResponse response) throws Exception {
    response.getOutputStream().print("PONG");
  }

  @RequestMapping ("/refreshCache")
  public void refreshCache(HttpServletResponse response) throws Exception {
    if (PropertiesHolder.getBoolean("server.refreshCache.enabled")) {
      logger.debug("refresh cache check");
      Long refreshTime = (Long)CacheUtils.instGet(CacheConstants.REFRESH_CACHE_CHECK);
      if (refreshTime == null) {
        refreshTime = System.currentTimeMillis() + 1000 * PropertiesHolder.getInt("server.refreshCache.initialDelay", 120);
        CacheUtils.instPut(CacheConstants.REFRESH_CACHE_CHECK, refreshTime);
      } else {
        Long currentTime = System.currentTimeMillis();
        if (currentTime > refreshTime) {
          LoggingUtils.getJavaLogging(getClass()).info(String.format("REFRESH CACHE STARTED"));
          refreshTime = System.currentTimeMillis() + 1000 * PropertiesHolder.getInt("server.refreshCache.nextDelay", 1800);
          CacheUtils.instPut(CacheConstants.REFRESH_CACHE_CHECK, refreshTime);
          logger.debug("REFRESH CACHE CHECK >>>> RELOADING ALL DATA IN CACHE.......");
          portalPageAdapter.findAllRoot();
          imageAdapter.findAll();
          portalServiceAdapter.getPage("root");
          portalServiceAdapter.getPage("home");
          CacheUtils.purgeMasterIndex();
          LoggingUtils.getJavaLogging(getClass()).info(String.format("REFRESH CACHE COMPLETED"));
        }
      }
    }
    response.getOutputStream().print("PONG");
  }

  @RequestMapping ("/refreshCacheDeeper/{pageCode}")
  public void refreshCacheDeeper(@PathVariable ("pageCode") String pageCode, HttpServletResponse response) throws Exception {
    if (PropertiesHolder.getBoolean("server.refreshCache.deeper.enabled")) {
      logger.debug("refresh cache check deeper");
      Long refreshTime = (Long)CacheUtils.instGet(CacheConstants.REFRESH_CACHE_DEEPER_CHECK);
      if (refreshTime == null) {
        refreshTime = System.currentTimeMillis() + 1000 * PropertiesHolder.getInt("server.refreshCache.deeper.initialDelay", 120);
        CacheUtils.instPut(CacheConstants.REFRESH_CACHE_DEEPER_CHECK, refreshTime);
      } else {
        Long currentTime = System.currentTimeMillis();
        if (currentTime > refreshTime) {
          refreshTime = System.currentTimeMillis() + 1000 * PropertiesHolder.getInt("server.refreshCache.deeper.nextDelay", 1800);
          CacheUtils.instPut(CacheConstants.REFRESH_CACHE_DEEPER_CHECK, refreshTime);
          logger.debug("REFRESH CACHE CHECK >>>> RELOADING ALL DATA IN CACHE.......");
          portalPageAdapter.findAllRoot();
          imageAdapter.findAll();
          portalServiceAdapter.getPage("root");
          portalServiceAdapter.getPage("home");
          if (pageCode != null) {
            PortalPage refreshPage = portalPageAdapter.findByCode(pageCode);
            pageTraverse(refreshPage);
          }
        }
      }
    }
    response.getOutputStream().print("PONG");
  }
  
  private void pageTraverse(PortalPage portalPage) {
    portalPage = portalPageAdapter.findById(portalPage.getId(), true, true, true);
    if (portalPage instanceof PortalFolderPage) {
      PortalFolderPage portalFolderPage = (PortalFolderPage)portalPage;
      for (PortalPage childPage : portalFolderPage.getChildreen()) {
        pageTraverse(childPage);
      }
    }
    if (portalPage instanceof ProductPage) {
      ProductPage productPage = (ProductPage)portalPage;
      productAdapter.findById(productPage.getEntity().getId());
    }
  }

  @RequestMapping ("/pdf/img/{imgCode}.pdf")
  public void testPdf(@PathVariable ("imgCode") String imgCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ImageDs imageDs = imageAdapter.findByCode(imgCode);
    if (imageDs.getContent() != null && imageDs.getContent().getBytes() != null) {
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfSession pdfSession = new PdfSession(baos);
      pdfSession.addParaghraph("Pdf di test con l'immagine " + imgCode);
      pdfSession.addImage(imageDs.getContent().getBytes());
      pdfSession.closeDocument();
      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "attachment;filename=\"" + imgCode+".pdf\"");
      response.getOutputStream().write(baos.toByteArray());
      
    }
  }
  
  @RequestMapping ("/pdf/order/{orderId}")
  public void testOrder(@PathVariable ("orderId") String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Order order = orderAdapter.findById(orderId);
    byte[] bytes = reportAdapter.printOrder(order);
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment;filename=\"ORD"+orderId+".pdf\"");
    response.getOutputStream().write(bytes);
  }
  
  @RequestMapping ("/pdf/ordersToProducer/{producerId}/{ordersId}")
  public void printOrdersToProducer(@PathVariable ("producerId") String producerId,
      @PathVariable ("ordersId") String ordersId,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Order> orders = retrieveOrders(ordersId);
    Produttore producer = productAdapter.findProducerById(producerId);
    byte[] bytes = reportAdapter.printOrdersToProducer(producer, orders);
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment;filename=\"DISTINTA"+producer.getCodice()+".pdf\"");
    response.getOutputStream().write(bytes);
  }
  
  @RequestMapping ("/excel/ordersToProducer/{producerId}/{ordersId}")
  public void exportExcelOrdersToProducer(@PathVariable ("producerId") String producerId,
      @PathVariable ("ordersId") String ordersId,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Order> orders = retrieveOrders(ordersId);
    Produttore producer = productAdapter.findProducerById(producerId);
    byte[] bytes = excelAdapter.printOrdersToProducer(producer, orders);
    response.setContentType("application/x-ms-excel");
    response.setHeader("Content-Disposition", "attachment;filename=\"DISTINTA"+producer.getCodice()+".xls\"");
    response.getOutputStream().write(bytes);
  }
  
  private List<Order> retrieveOrders (String ordersId) {
    List<Order> orders = new ArrayList<Order>();
    StringTokenizer st = new StringTokenizer(ordersId, "|");
    while (st.hasMoreTokens()) {
      String orderId = st.nextToken();
      Order order = orderAdapter.findById(orderId);
      orders.add(order);
    }
    return orders;
  }
  
}

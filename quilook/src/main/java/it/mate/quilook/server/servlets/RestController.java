package it.mate.quilook.server.servlets;

import it.mate.commons.server.utils.CacheUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.util.CacheConstants;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.server.services.PortalServiceAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller ("quilookRestController")
public class RestController {
  
  private final static Logger logger = Logger.getLogger(RestController.class);

//@Autowired private GeneralAdapter generalAdapter;
  
  @Autowired private PortalPageAdapter portalPageAdapter;
  
  @Autowired private ImageAdapter imageAdapter;
  
  @Autowired private PortalServiceAdapter portalServiceAdapter;
  
  
  
  
  @RequestMapping ("/pg/{pageCode}")
  public void forwardToPage(@PathVariable ("pageCode") String pageCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.debug("pageCode = " + pageCode);
    PortalPage page = portalPageAdapter.findByCode(pageCode);
    PortalSessionState state = new PortalSessionState(PortalSessionState.MODULE_SITE, page);
    state.setTemplateName(page.getTemplateName());
//  generalAdapter.storePortalSessionState(request, state);
    request.setAttribute("pageName", page.getName());
    request.getRequestDispatcher("/site-nobanner.jsp").forward(request, response);
  }
  
  @RequestMapping ("/im/{imgCode}")
  public void getImage(@PathVariable ("imgCode") String imgCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ImageDs imageDs = imageAdapter.findByCode(imgCode);
    if (imageDs.getContent() != null && imageDs.getContent().getBytes() != null) {
      response.getOutputStream().write(imageDs.getContent().getBytes());
    }
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
  }
  
}

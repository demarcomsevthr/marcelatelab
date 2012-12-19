package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.services.PortalPageService;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PortalPageServiceImpl extends RemoteServiceServlet implements PortalPageService {

  private static Logger logger = Logger.getLogger(PortalPageServiceImpl.class);
  
  private PortalPageAdapter adapter;
      
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getPortalPageAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  public List<PortalPage> findAll() {
    return adapter.findAll();
  }

  @Override
  public PortalPage update(PortalPage entity) {
    return adapter.update(entity);
  }

  @Override
  public void delete(PortalPage entity) {
    adapter.delete(entity);
  }

  @Override
  public PortalPage create(PortalPage entity) {
    return adapter.create(entity);
  }

  @Override
  public PortalPage findById(String id) {
    return adapter.findById(id);
  }
  
  @Override
  public PortalPage findById(String pageId, boolean resolveChildreen, boolean resolveProducts, boolean resolveHtmls) {
    return adapter.findById(pageId, resolveChildreen, resolveProducts, resolveHtmls);
  }

  @Override
  public PortalFolderPage fetchChildreen(PortalFolderPage page) {
    return adapter.fetchChildreen(page);
  }

  public WebContentPage fetchHtmls (WebContentPage contentPage) {
    return adapter.fetchHtmls(contentPage);
  }
  
  public WebContentPage updateHtmlContent (String pageId, HtmlContent content) {
    return adapter.updateHtmlContent(pageId, content);
  }
  
  @Override
  public List<PortalPage> findAllRoot() {
    return adapter.findAllRoot();
  }

  @Override
  public List<PortalPage> findAllRootMenu() {
    return adapter.findAllRootMenu();
  }

  @Override
  public List<PortalPage> findAllRootExplorer() {
    return adapter.findAllRootExplorer();
  }

  @Override
  public PortalPage newInstance(String classname) {
    try {
      return (PortalPage)Class.forName(classname).newInstance();
    } catch (Exception ex) {
      logger.error("error", ex);
      return null;
    }
  }

  @Override
  public PortalPage findByCode(String code) {
    return adapter.findByCode(code);
  }

}

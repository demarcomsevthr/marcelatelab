package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.WebContentPage;

import java.util.List;

public interface PortalPageAdapter {
  
  public List<PortalPage> findAll();

  public PortalPage update(PortalPage entity);

  public void delete(PortalPage entity);

  public PortalPage create(PortalPage entity);

  public PortalPage findById(String id);
  
  public PortalPage findById (String pageId, final boolean resolveChildreen, final boolean resolveProducts, final boolean resolveHtmls);

  public List<PortalPage> findAllRoot();
  
  public List<PortalPage> findAllRootMenu();

  public List<PortalPage> findAllRootExplorer();
  
  public void printPagesToXml();

  public WebContentPage updateHtmlContent(String pageId, HtmlContent content);
  
  public PortalFolderPage fetchChildreen(PortalFolderPage folder);

  public ProductFolderPage fetchProducts (ProductFolderPage parent);
  
  public WebContentPage fetchHtmls (WebContentPage contentPage);

  public PortalPage resolveAllDependencies(PortalPage page);
  
  public void exportToXml();
  
  public void loadFromXml();
  
  public PortalPage findByCode(String code);

}

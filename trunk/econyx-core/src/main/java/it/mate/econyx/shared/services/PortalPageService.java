package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".portalPageService")
public interface PortalPageService extends RemoteService {
  
  public List<PortalPage> findAll() throws ServiceException;

  public PortalPage update(PortalPage entity) throws ServiceException;

  public void delete(PortalPage entity) throws ServiceException;

  public PortalPage create(PortalPage entity) throws ServiceException;

  public PortalPage findById(String id) throws ServiceException;

  public PortalPage findById (String id, boolean resolveChildreen, boolean resolveProducts, boolean resolveHtmls) throws ServiceException;
  
  public PortalFolderPage fetchChildreen(PortalFolderPage folder) throws ServiceException;

  public WebContentPage fetchHtmls (WebContentPage contentPage) throws ServiceException;
  
  public WebContentPage updateHtmlContent (String pageId, HtmlContent content) throws ServiceException;
  
  public List<PortalPage> findAllRoot() throws ServiceException;
  
  public List<PortalPage> findAllRootMenu() throws ServiceException;

  public List<PortalPage> findAllRootExplorer() throws ServiceException;
  
  public PortalPage newInstance(String classname) throws ServiceException;
  
  public PortalPage findByCode(String code) throws ServiceException;

  public void removePageFromCache(String pageId);
  
}

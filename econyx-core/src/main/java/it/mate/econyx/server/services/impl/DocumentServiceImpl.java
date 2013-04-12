package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.DocumentAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.server.util.ServletThreadUtils;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.services.DocumentService;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service (".documentService")
public class DocumentServiceImpl extends RemoteServiceServlet implements DocumentService {

  private static Logger logger = Logger.getLogger(DocumentServiceImpl.class);
  
  private DocumentAdapter adapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getDocumentAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  protected void onBeforeRequestDeserialized(String serializedRequest) {
    ServletThreadUtils.set(getThreadLocalRequest(), getThreadLocalResponse());
    PortalSessionStateServerUtils.setInThread(AdaptersUtil.getGeneralAdapter().retrievePortalSessionState(getThreadLocalRequest()));
    super.onBeforeRequestDeserialized(serializedRequest);
  }
  
  @Override
  public List<DocumentFolder> findAllFolders() throws ServiceException {
    return adapter.findAllFolders();
  }
  
  @Override
  public DocumentFolder createFolder(DocumentFolder entity) throws ServiceException {
    return adapter.createFolder(entity);
  }

  @Override
  public DocumentFolder updateFolder(DocumentFolder entity) throws ServiceException {
    return adapter.updateFolder(entity);
  }

  @Override
  public void deleteFolder(DocumentFolder entity) throws ServiceException {
    adapter.deleteFolder(entity);
  }

  @Override
  public DocumentFolder findFolderById(String id) throws ServiceException {
    return adapter.findFolderById(id);
  }

  @Override
  public Document findDocumentById(String id, boolean fetchContent) {
    return adapter.findDocumentById(id, fetchContent);
  }

  @Override
  public Document updateDocument(Document document) {
    return adapter.updateDocument(document);
  }
  
}

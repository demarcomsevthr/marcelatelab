package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".documentService")
public interface DocumentService extends RemoteService {
  
  public List<DocumentFolder> findAllFolders() throws ServiceException;
  
  public DocumentFolder createFolder(DocumentFolder entity) throws ServiceException;

  public DocumentFolder updateFolder(DocumentFolder entity) throws ServiceException;

  public void deleteFolder(DocumentFolder entity) throws ServiceException;

  public DocumentFolder findFolderById(String id) throws ServiceException;
  
  public Document findDocumentById(String id, boolean fetchContent);
  
  public Document updateDocument(Document document);
  
}

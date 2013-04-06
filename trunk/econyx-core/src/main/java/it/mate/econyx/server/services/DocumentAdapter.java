package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;

import java.util.List;

public interface DocumentAdapter {

  public List<DocumentFolder> findAllFolders();
  
  public DocumentFolder updateFolder(DocumentFolder entity);

  public void deleteFolder(DocumentFolder entity);

  public DocumentFolder createFolder(DocumentFolder entity);

  public DocumentFolder findFolderById(String id);
  
  public Document findDocumentByCode(String code);
  
  public Document findDocumentById(String id, boolean fetchContent);
  
  public Document updateDocument(Document Document);
  
}

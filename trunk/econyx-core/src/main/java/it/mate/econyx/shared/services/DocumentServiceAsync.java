package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DocumentServiceAsync {

  void createFolder(DocumentFolder entity, AsyncCallback<DocumentFolder> callback);

  void deleteFolder(DocumentFolder entity, AsyncCallback<Void> callback);

  void findAllFolders(AsyncCallback<List<DocumentFolder>> callback);

  void findDocumentById(String id, boolean fetchContent, AsyncCallback<Document> callback);

  void findFolderById(String id, AsyncCallback<DocumentFolder> callback);

  void updateDocument(Document document, AsyncCallback<Document> callback);

  void updateFolder(DocumentFolder entity, AsyncCallback<DocumentFolder> callback);

}

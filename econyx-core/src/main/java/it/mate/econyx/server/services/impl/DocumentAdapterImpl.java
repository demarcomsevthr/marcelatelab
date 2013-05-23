package it.mate.econyx.server.services.impl;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.FindContext;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.econyx.server.model.impl.DocumentDs;
import it.mate.econyx.server.model.impl.DocumentFolderDs;
import it.mate.econyx.server.services.DocumentAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.impl.DocumentFolderTx;
import it.mate.econyx.shared.model.impl.DocumentTx;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentAdapterImpl implements DocumentAdapter {

  private static Logger logger = Logger.getLogger(DocumentAdapterImpl.class);

  @Autowired private GeneralAdapter generalAdapter;
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  @Override
  public List<DocumentFolder> findAllFolders() {
    List<DocumentFolderDs> folders = dao.findAll(DocumentFolderDs.class);
    if (folders != null) {
      Collections.sort(folders, new Comparator<DocumentFolder>() {
        public int compare(DocumentFolder f1, DocumentFolder f2) {
          return (f1.getOrderNm() != null && f2.getOrderNm() != null) ? f1.getOrderNm().compareTo(f2.getOrderNm()) : 0;
        }
      });
    }
    return CloneUtils.clone(folders, DocumentFolderTx.class, DocumentFolder.class);
  }

  @Override
  public DocumentFolder updateFolder(DocumentFolder entity) {
    DocumentFolderDs folder = CloneUtils.clone(entity, DocumentFolderDs.class);
    folder.setDocuments(updateDocuments(folder.getDocuments()));
    folder = dao.update(folder);
    return CloneUtils.clone (folder, DocumentFolderTx.class);
  }

  @Override
  public void deleteFolder(DocumentFolder entity) {
    DocumentFolderDs folder = dao.findById(DocumentFolderDs.class, entity.getId());
    deleteDocuments(folder.getDocuments());
    dao.delete(folder);
  }

  @Override
  public DocumentFolder createFolder(DocumentFolder entity) {
    DocumentFolderDs folder = CloneUtils.clone(entity, DocumentFolderDs.class);
    folder.setDocuments(createDocuments(folder.getDocuments()));
    if (folder.getCode() == null) {
      folder.setCode(getNextCodeCounter());
    }
    folder = dao.create(folder);
    return CloneUtils.clone (folder, DocumentFolderTx.class);
  }

  @Override
  public DocumentFolder findFolderById(String id) {
    DocumentFolder folder = dao.findById(DocumentFolderDs.class, id);
    return CloneUtils.clone(folder, DocumentFolderTx.class);
  }
  
  @Override
  public Document updateDocument(Document document) {
    document = createOrUpdateDocumentDs(CloneUtils.clone(document, DocumentDs.class));
    return CloneUtils.clone(document, DocumentTx.class);
  }

  private List<Document> createDocuments(List<Document> documents) {
    if (documents != null) {
      for (int it = 0; it < documents.size(); it++) {
        DocumentDs document = CloneUtils.clone(documents.get(it), DocumentDs.class);
        document = createOrUpdateDocumentDs(document);
        documents.set(it, document);
      }
    }
    return documents;
  }
  
  private void deleteDocuments(List<Document> documents) {
    if (documents != null) {
      for (Document Document : documents) {
        DocumentDs document = CloneUtils.clone(Document, DocumentDs.class);
        deleteDocumentDs(document);
      }
    }
  }

  private List<Document> updateDocuments(List<Document> documents) {
    if (documents != null) {
      for (int it = 0; it < documents.size(); it++) {
        DocumentDs document = CloneUtils.clone(documents.get(it), DocumentDs.class);
        document = createOrUpdateDocumentDs(document);
        documents.set(it, document);
      }
    }
    return documents;
  }
  
  private void deleteDocumentDs (DocumentDs documentDs) {
    /*
    DocumentContentDs content = (DocumentContentDs)documentDs.getContent();
    if (content != null && content.getKey() != null) {
      dao.delete(content);
    } else {
      documentDs = internalFindDocumentById(documentDs.getId(), true);
      if (content != null && content.getKey() != null) {
        dao.delete(content);
      }
    }
    */
    dao.delete(documentDs);
  }
  
  private DocumentDs createOrUpdateDocumentDs (DocumentDs document) {
    /*
    DocumentContentDs content = (DocumentContentDs)document.getContent();
    if (content != null) {
      if (content.getKey() != null) {
        content = dao.update(content);
      } else {
        content = dao.create(content);
      }
      document.setContent(content);
    }
    */
    if (document.getKey() == null) {
      if (document.getCode() == null) {
        document.setCode(getNextCodeCounter());
      }
      document = dao.create(document);
    } else {
      document = dao.update(document);
    }
    return document;
  }
  
  public Document findDocumentByCode(String code) {
    DocumentDs document = dao.findSingle(DocumentDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code);
    return CloneUtils.clone(document, DocumentTx.class);
  }
  
  public DocumentDs findDocumentDsByCode(String code) {
    Document document = findDocumentByCode(code);
    document = internalFindDocumentById(document.getId(), true);
    return (DocumentDs)document;
  }
  
  public Document findDocumentById(String id, boolean fetchContent) {
    Document document = internalFindDocumentById(id, fetchContent);
    return CloneUtils.clone(document, DocumentTx.class);
  }
  
  public DocumentDs findDocumentDsById(String id) {
    return internalFindDocumentById(id, true);
  }
  
  private DocumentDs internalFindDocumentById(String id, boolean fetchContent) {
    /*
    if (fetchContent) {
      CacheUtils.deleteByKeyWithCondition(id, Document.class, new CacheUtils.Condition<Document>() {
        public boolean evaluate(Document cachedEntity) {
          return (cachedEntity.getContent() == null);
        }
      });
    }
    */
    FindContext<DocumentDs> context = new FindContext<DocumentDs>(DocumentDs.class).setId(id);
    /*
    if (fetchContent) {
      context.includedField("contentKey");
    }
    */
    DocumentDs ds = dao.findWithContext(context);
    return ds;
  }
  
  private String getNextCodeCounter() {
    return ""+generalAdapter.findNextCounterValue();
  }
  
}

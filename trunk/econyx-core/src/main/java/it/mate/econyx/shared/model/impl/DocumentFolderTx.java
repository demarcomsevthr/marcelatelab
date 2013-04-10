package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DocumentFolderTx implements DocumentFolder {

  String id;
  
  String code;
  
  String name;
  
  Integer orderNm;
  
  List<DocumentTx> documents = new ArrayList<DocumentTx>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public List<Document> getDocuments() {
    return new CollectionPropertyClientUtil<Document, DocumentTx>(documents, DocumentTx.class);
  }

  @CloneableProperty (targetClass=DocumentTx.class)
  public void setDocuments(List<Document> documents) {
    if (documents != null) {
      this.documents = new ArrayList<DocumentTx>();
      for (Document document : documents) {
        if (document instanceof DocumentTx) {
          this.documents.add((DocumentTx)document);
        } else {
          throw new CloneablePropertyMissingException(document);
        }
      }
    } else {
      this.documents = null;
    }
  }
  
}

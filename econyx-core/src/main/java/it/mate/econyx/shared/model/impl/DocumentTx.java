package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentContent;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.Date;

@SuppressWarnings("serial")
public class DocumentTx implements Document {

  String id;
  
  String code;
  
  Integer orderNm;
  
  String title;

  DocumentContentTx content;

  PortalUserTx author;
  
  Date created;
  
  // serve solo per eseguire correttamente la DocumentActivity.update(Document) 
  transient DocumentFolder documentFolder;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DocumentContent getContent() {
    return content;
  }

  @CloneableProperty (targetClass=DocumentContentTx.class)
  public void setContent(DocumentContent content) {
    if (content == null) {
      this.content = null;
    } else if (content instanceof DocumentContentTx) {
      this.content = (DocumentContentTx)content;
    } else {
      throw new CloneablePropertyMissingException(content);
    }
  }

  public String getName() {
    return getTitle();
  }

  public void setName(String name) {
    setTitle(name);
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PortalUser getAuthor() {
    return author;
  }

  @CloneableProperty (targetClass=PortalUserTx.class)
  public void setAuthor(PortalUser author) {
    if (author == null) {
      this.author = null;
    } else if (author instanceof PortalUserTx) {
      this.author = (PortalUserTx)author;
    } else {
      throw new CloneablePropertyMissingException(author);
    }
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public DocumentFolder getDocumentFolder() {
    return documentFolder;
  }

  public void setDocumentFolder(DocumentFolder documentFolder) {
    this.documentFolder = documentFolder;
  }

}

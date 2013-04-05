package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.DocumentContent;

@SuppressWarnings("serial")
public class DocumentContentTx implements DocumentContent {

  String id;
  
  String content;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  
}

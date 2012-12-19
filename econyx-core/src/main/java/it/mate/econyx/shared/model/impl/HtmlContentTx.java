package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.HtmlContent;

@SuppressWarnings("serial")
public class HtmlContentTx extends PortalContentFragmentTx implements HtmlContent {

  protected Type type;
  
  protected String content;
  
  @Override
  public String toString() {
    return "ImageContentTx [id=" + id + ", entity=" + entity + "]";
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  
}

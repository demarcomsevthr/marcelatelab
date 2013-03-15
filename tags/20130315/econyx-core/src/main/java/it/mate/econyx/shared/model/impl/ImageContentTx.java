package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ImageContent;

@SuppressWarnings("serial")
public class ImageContentTx extends PortalContentFragmentTx implements ImageContent {

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

  public String getContentString() {
    return content;
  }

  public void setContentString(String content) {
    this.content = content;
  }
  
  
  
}

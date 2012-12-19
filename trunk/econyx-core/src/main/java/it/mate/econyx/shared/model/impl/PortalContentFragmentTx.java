package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.PortalContentFragment;
import it.mate.econyx.shared.model.WebContent;

@SuppressWarnings("serial")
public class PortalContentFragmentTx implements PortalContentFragment {

  protected String id;

  protected WebContent entity;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WebContent getEntity() {
    return entity;
  }

  public void setEntity(WebContent entity) {
    this.entity = entity;
  }

}

package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.impl.HtmlContentTx;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=HtmlContentTx.class, instanceCache=true)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class HtmlWebContentDs extends HtmlContentDs implements HtmlContent, HasKey {

  public HtmlWebContentDs() {
    super();
  }

  public HtmlWebContentDs(String type) {
    super(type);
  }
  
}

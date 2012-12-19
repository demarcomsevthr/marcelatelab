package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.impl.HtmlContentTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=HtmlContentTx.class)
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

package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.impl.HtmlContentTx;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=HtmlContentTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class HtmlProductContentDs extends HtmlContentDs implements HtmlContent, HasKey {

  public HtmlProductContentDs() {
    super();
  }

  public HtmlProductContentDs(String type) {
    super(type);
  }
  
}

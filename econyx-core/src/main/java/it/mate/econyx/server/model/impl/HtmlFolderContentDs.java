package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.PersistenceCapable;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class HtmlFolderContentDs extends HtmlContentDs implements HtmlContent, HasKey {

  public HtmlFolderContentDs() {
    super();
  }

  public HtmlFolderContentDs(String type) {
    super(type);
  }

}

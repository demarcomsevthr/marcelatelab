package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.DocumentFolderPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.DocumentFolderPageDs")
public class DocumentFolderPageTx extends PortalFolderPageTx implements DocumentFolderPage {

  private DocumentFolderTx entity;
  
  public DocumentFolder getEntity() {
    return entity;
  }

  @CloneableProperty (targetClass=DocumentFolderTx.class)
  public void setEntity(DocumentFolder entity) {
    if (entity == null) {
      this.entity = null;
    } else if (entity instanceof DocumentFolderTx) {
      this.entity = (DocumentFolderTx)entity;
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }

}

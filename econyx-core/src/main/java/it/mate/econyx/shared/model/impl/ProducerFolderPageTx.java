package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ProducerFolderPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.BlogPageDs")
public class ProducerFolderPageTx extends PortalFolderPageTx implements ProducerFolderPage {

  private ProduttoreTx entity;
  
  @Override
  public Produttore getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=ProduttoreTx.class)
  public void setEntity(Produttore entity) {
    if (entity == null) {
      this.entity = null;
    } else if (entity instanceof ProduttoreTx) {
      this.entity = (ProduttoreTx)entity;
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }
  
}

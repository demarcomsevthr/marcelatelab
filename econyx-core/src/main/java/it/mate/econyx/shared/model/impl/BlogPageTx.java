package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.BlogPageDs")
public class BlogPageTx extends PortalFolderPageTx implements BlogPage {

  private BlogTx entity;
  
  @Override
  public Blog getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=BlogTx.class)
  public void setEntity(Blog entity) {
    if (entity == null) {
      this.entity = null;
    } else if (entity instanceof BlogTx) {
      this.entity = (BlogTx)entity;
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }
  
}

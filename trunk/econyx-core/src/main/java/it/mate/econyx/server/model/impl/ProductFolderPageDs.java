package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.impl.ProductFolderPageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=ProductFolderPageTx.class, instanceCache=true)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.ProductFolderPageTx")
public class ProductFolderPageDs extends AbstractPortalFolderPageDs implements ProductFolderPage {
  
}

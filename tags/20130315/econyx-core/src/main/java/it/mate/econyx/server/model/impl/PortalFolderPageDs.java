package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.impl.PortalFolderPageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=PortalFolderPageTx.class, instanceCache=true)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.PortalFolderPageTx")
public class PortalFolderPageDs extends AbstractPortalFolderPageDs {

  
}

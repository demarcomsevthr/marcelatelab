package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.impl.WebContentPageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=WebContentPageTx.class, instanceCache=true)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.WebContentPageTx")
public class WebContentPageDs extends AbstractWebContentPageDs {

  
}

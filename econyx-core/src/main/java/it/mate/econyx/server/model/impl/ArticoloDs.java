package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=ArticoloTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.ArticoloTx")
public class ArticoloDs extends AbstractArticoloDs {


}

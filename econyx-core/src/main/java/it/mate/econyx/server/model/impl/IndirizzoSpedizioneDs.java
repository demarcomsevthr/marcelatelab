package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.impl.IndirizzoSpedizioneTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=IndirizzoSpedizioneTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.IndirizzoSpedizioneTx")
public class IndirizzoSpedizioneDs extends AbstractIndirizzoDs {

}

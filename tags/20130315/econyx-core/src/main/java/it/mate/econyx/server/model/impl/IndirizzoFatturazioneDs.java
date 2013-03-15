package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.impl.IndirizzoFatturazioneTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=IndirizzoFatturazioneTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.IndirizzoFatturazioneTx")
public class IndirizzoFatturazioneDs extends AbstractIndirizzoDs {

}

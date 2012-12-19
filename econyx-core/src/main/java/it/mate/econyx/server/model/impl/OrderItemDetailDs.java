package it.mate.econyx.server.model.impl;

import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.OrderItemDetailTx")
public class OrderItemDetailDs extends AbstractOrderItemDetailDs {

}

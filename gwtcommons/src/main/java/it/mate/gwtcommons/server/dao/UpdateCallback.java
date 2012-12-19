package it.mate.gwtcommons.server.dao;

import java.io.Serializable;

import javax.jdo.PersistenceManager;

public abstract class UpdateCallback <E extends Serializable> {

  public abstract E updateEntityValues(PersistenceManager pm, E attachedEntity);
  
}

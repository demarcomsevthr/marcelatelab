package it.mate.gwtcommons.server.dao;

import java.io.Serializable;

public abstract class FindCallback <E extends Serializable> {

  public abstract void processResultsInTransaction(E entity);
  
}

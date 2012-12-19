package it.mate.econyx.server.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

public interface Counter extends Serializable {

  public Long getValue();

  public void setValue(Long value);

  public Key getKey();

}

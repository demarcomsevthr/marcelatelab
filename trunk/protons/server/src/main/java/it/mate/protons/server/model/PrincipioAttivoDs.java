package it.mate.protons.server.model;

import it.mate.commons.server.model.HasKey;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class PrincipioAttivoDs implements HasKey, Serializable {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  private String name;
  
  @Persistent
  Blob content;
  
  @Override
  public String toString() {
    return "PrincipioAttivoDs [id=" + id + ", name=" + name + ", content.lenght=" + (content != null ? content.getBytes().length : "null"  )+ "]";
  }

  public Key getKey() {
    return id;
  }

  public void setId(Key id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Blob getContent() {
    return content;
  }

  public void setContent(Blob content) {
    this.content = content;
  }

}

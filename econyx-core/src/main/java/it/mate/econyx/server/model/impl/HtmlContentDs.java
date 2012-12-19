package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.WebContent;
import it.mate.econyx.shared.model.impl.HtmlContentTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@CacheableEntity (txClass=HtmlContentTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@Inheritance (strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class HtmlContentDs implements HtmlContent, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent (dependentKey="false")
  Key entityId;
  
  @UnownedRelationship (key="entityId")
  transient WebContent entity;
  
  @Persistent
  String type;
  
  @Persistent
  Text content;
  
  public HtmlContentDs() {
    super();
  }

  public HtmlContentDs(String type) {
    super();
    this.type = type;
  }

  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public WebContent getEntity() {
    return entity;
  }

  public void setEntity(WebContent entity) {
    if (entity instanceof HasKey) {
      HasKey hasKey = (HasKey)entity;
      this.entityId = hasKey.getKey();
//    this.entity = entity;
    }
  }

  public Type getType() {
    return Type.valueOfCode(type);
  }

  public void setType(Type type) {
    this.type = type.getCode();
  }

  public String getContent() {
    return content.getValue();
  }

  public void setContent(String content) {
    this.content = new Text(content);
  }
  
}

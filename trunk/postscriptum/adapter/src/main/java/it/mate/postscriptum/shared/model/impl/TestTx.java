package it.mate.postscriptum.shared.model.impl;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TestTx implements Serializable {

  private String id;
  
  private String version;
  
  private String field1;

  @Override
  public String toString() {
    return "TestTx [id=" + id + ", version=" + version + ", field1=" + field1 + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getField1() {
    return field1;
  }

  public void setField1(String field1) {
    this.field1 = field1;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
  
}

package it.mate.postscriptum.shared.model.impl;

import java.io.Serializable;

public class TestTx implements Serializable {

  private static final long serialVersionUID = -9023633831136189873L;

  private String id;
  
  private String version;
  
  private String field1;

  private String field2;
  
  @Override
  public String toString() {
    return "TestTx [id=" + id + ", version=" + version + ", field1=" + field1 + ", field2=" + field2 + "]";
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

  public String getField2() {
    return field2;
  }

  public void setField2(String field2) {
    this.field2 = field2;
  }

}

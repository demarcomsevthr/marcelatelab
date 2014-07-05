package it.mate.postscriptum.shared.model.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ExtensibleTx implements Serializable {

  private Map<String, Object> values = new HashMap<String, Object>();
  
  @Override
  public String toString() {
    return "ExtensibleTx [getField1()=" + getField1() + ", getField2()=" + getField2() + "]";
  }

  public String getField1() {
    return (String)values.get("field1");
  }
  
  public void setField1(String value) {
    values.put("field1", value);
  }
  
  public String getField2() {
    return (String)values.get("field2");
  }
  
  public void setField2(String value) {
    values.put("field2", value);
  }
  
  public String getField3() {
    return (String)values.get("field3");
  }
  
  public void setField3(String value) {
    values.put("field3", value);
  }
  
}

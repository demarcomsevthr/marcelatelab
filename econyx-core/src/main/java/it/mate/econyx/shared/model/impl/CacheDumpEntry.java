package it.mate.econyx.shared.model.impl;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CacheDumpEntry implements Serializable {
  
  private String key;
  
  private String value;
  
  public CacheDumpEntry() {

  }

  public CacheDumpEntry(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public String setValue(String value) {
    String oldValue = this.value;
    this.value = value;
    return oldValue;
  }
  
}

package it.mate.gwtcommons.shared.rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class RpcMap extends HashMap<String, Object> {

  // Escamotage per obbligare RPC a generare tutti i datatypes utilizzati nei tx
  public String dummyString;
  public Date dummyDate; 
  public Integer dummyInteger;
  public Double dummyDouble;
  public Long dummyLong; 
  public List<Void> dummyList;
  public ArrayList<Void> dummyArrayList;
  public Boolean dummyBoolean;
  public Float dummyFloat;
  public Short dummyShort;
  
}

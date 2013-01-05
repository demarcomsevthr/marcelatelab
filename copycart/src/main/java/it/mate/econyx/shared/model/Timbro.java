package it.mate.econyx.shared.model;

public interface Timbro extends Articolo {

  public Integer getLarghezza();

  public void setLarghezza(Integer larghezza);

  public Integer getAltezza();

  public void setAltezza(Integer altezza);
  
  public Integer getNumRighe();
  
  public void setNumRighe(Integer numRighe);
  
  public Boolean getOval();

  public void setOval(Boolean oval);
  
  public boolean isOval();
  
  public Boolean getDatario();
  
  public void setDatario(Boolean value);
  
  public boolean isDatario();
  
}

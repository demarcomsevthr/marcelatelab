package it.mate.econyx.shared.model;

import java.util.List;

public interface Articolo extends PortalEntity, WebContent {
  
  public String getNome();

  public void setNome(String code);

  public String getCodice();

  public void setCodice(String code);
  
  public Double getPrezzo();

  public void setPrezzo(Double price);

  public TipoArticolo getTipoArticolo();

  public void setTipoArticolo(TipoArticolo type);
  
  public UnitaDiMisura getUnitaDiMisura();

  public void setUnitaDiMisura(UnitaDiMisura unitOfMeasure);
  
  public Integer getConfezione();
  
  public void setConfezione(Integer size);
  
  
  public List<ImageContent> getImages();
  
  public void setImages(List<ImageContent> images);
  
  
  public void setProducer(Produttore producer);
  
  public Produttore getProducer();
  

}

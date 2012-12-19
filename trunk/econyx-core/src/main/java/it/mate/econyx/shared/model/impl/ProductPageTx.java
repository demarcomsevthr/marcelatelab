package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.ProductPageDs")
public class ProductPageTx extends WebContentPageTx implements ProductPage {

  private Articolo entity;
  
  @Override
  public Articolo getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=ArticoloTx.class)
  public void setEntity(Articolo entity) {
    this.entity = entity;
  }

  
  
  /*
  
  String code;
  
  Float price;
  
  ProductType type;
  
  UnitOfMeasure unitOfMeasure;
  
  Integer packetSize;
  
  List<? extends ImageContent> images = new ArrayList<ImageContentTx>();
  
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }
  
  public ProductType getType() {
    return type;
  }
  
  @CloneableProperty (targetClass=ProductTypeTx.class)
  public void setType(ProductType type) {
    if (type == null) {
      this.type = null;
    } else if (type instanceof ProductTypeTx) {
      this.type = (ProductTypeTx)type;
    } else {
      throw new IllegalArgumentException("cannot set type of type " + type.getClass());
    }
  }
  
  public UnitOfMeasure getUnitOfMeasure() {
    return unitOfMeasure;
  }

  @CloneableProperty (targetClass=UnitOfMeasureTx.class)
  public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
    if (unitOfMeasure == null) {
      this.unitOfMeasure = null;
    } else if (unitOfMeasure instanceof UnitOfMeasureTx) {
      this.unitOfMeasure = unitOfMeasure;
    } else {
      throw new IllegalArgumentException("cannot set unitOfMeasure of type " + unitOfMeasure.getClass());
    }
  }
  
  public Integer getPacketSize() {
    return packetSize;
  }

  public void setPacketSize(Integer packetSize) {
    this.packetSize = packetSize;
  }
  
  public List<ImageContent> getImages() {
    return (List<ImageContent>)images;
  }
  
  @CloneableProperty (targetClass=ImageContentTx.class)
  public void setImages(List<ImageContent> images) {
    this.images = images;
  }
  
  */
  
}

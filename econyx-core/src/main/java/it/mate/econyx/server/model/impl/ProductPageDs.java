package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.impl.ProductPageTx;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@CacheableEntity (txClass=ProductPageTx.class, instanceCache=true)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.ProductPageTx")
public class ProductPageDs extends AbstractWebContentPageDs implements ProductPage {

  @Persistent (dependentKey="false")
  Key entityId;
  
  @UnownedRelationship (key="entityId")
  transient AbstractArticoloDs entity;

  @Override
  public Articolo getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=AbstractArticoloDs.class)
  public void setEntity(Articolo entity) {
    if (entity == null) {
      this.entity = null;
      this.entityId = null;
    } else {
      this.entity = (AbstractArticoloDs)entity;
      this.entityId = this.entity.getKey();
    }
  }
 
  
  /*
  
  @Persistent
  String code;
  
  @Persistent
  Float price;

  @Persistent (dependentKey="false")
  Key typeId;
  
  @UnownedRelationship (key="typeId")
  transient ProductTypeDs type;
  
  @Persistent (dependentKey="false")
  Key umId;
  
  @UnownedRelationship (key="umId")
  transient UnitOfMeasureDs unitOfMeasure;
  
  @Persistent Integer packetSize;

  
  @Persistent
  List<ImageContentDs> images;
  
  
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

  @CloneableProperty (targetClass=ProductTypeDs.class)
  public void setType(ProductType type) {
    this.type = (ProductTypeDs)type;
    this.typeId = type != null ? ((ProductTypeDs)type).getKey() : null;
  }
  
  public UnitOfMeasure getUnitOfMeasure() {
    return unitOfMeasure;
  }

  @CloneableProperty (targetClass=UnitOfMeasureDs.class)
  public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
    this.unitOfMeasure = (UnitOfMeasureDs)unitOfMeasure;
    this.umId = unitOfMeasure != null ? ((UnitOfMeasureDs)unitOfMeasure).getKey() : null;
  }
  
  public Integer getPacketSize() {
    return packetSize;
  }

  public void setPacketSize(Integer packetSize) {
    this.packetSize = packetSize;
  }
  
  
  private ImageContentDs getImageContentDs(ImageContent.Type type) {
    if (areImagesInitialized()) {
      for (ImageContent image : images) {
        if (image.getType() == type) {
          return (ImageContentDs)image;
        }
      }
    }
    return null;
  }
  
  public Blob getImageSmall() {
    ImageContentDs image = getImageContentDs(ImageContent.Type.SMALL);
    if (image != null) {
      return image.getContent();
    }
    return null;
  }
  
  private boolean isImageSmallInitialized() {
    return getImageSmall() != null;
  }
  
  public void setImageSmall(Blob content) {
    ImageContentDs image = getImageContentDs(ImageContent.Type.SMALL);
    if (image == null) {
      image = new ImageContentDs(ImageContent.Type.SMALL);
      addImage(image);
    }
    image.setContent(content);
  }
  
  public Blob getImageMedium() {
    ImageContentDs image = getImageContentDs(ImageContent.Type.MEDIUM);
    if (image != null) {
      return image.getContent();
    }
    return null;
  }
  
  private boolean isImageMediumInitialized() {
    return getImageMedium() != null;
  }
  
  public void setImageMedium(Blob content) {
    ImageContentDs image = getImageContentDs(ImageContent.Type.MEDIUM);
    if (image == null) {
      image = new ImageContentDs(ImageContent.Type.MEDIUM);
      addImage(image);
    }
    image.setContent(content);
  }
  
  public void initializeImages() {
    CollectionUtils.traverseCollection(this.images);
  }

  public boolean areImagesInitialized() {
    return images != null;
  }
  
  public List<ImageContent> getImages() {
    return images != null ? new ArrayList<ImageContent>(images) : null;
  }

  @CloneableProperty (targetClass=ImageContentDs.class)
  public void setImages(List<ImageContent> images) {
    if (images != null) {
      for (ImageContent image : images) {
        addImage(image);
      }
    }
  }
  
  private void addImage (ImageContent image) {
    if (this.images == null) {
      this.images = new ArrayList<ImageContentDs>();
    }
    ((List<ImageContentDs>)images).add((ImageContentDs)image);
    images.get(images.size() - 1).setEntity(this);
  }
  
  */

}

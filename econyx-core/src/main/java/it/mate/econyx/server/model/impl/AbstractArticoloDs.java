package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.commons.server.services.NotImplementedException;
import it.mate.commons.server.utils.CollectionUtils;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.HtmlContent.Type;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true", identityType=IdentityType.APPLICATION)
@Inheritance (strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class AbstractArticoloDs implements Articolo, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent
  String codice;
  
  @Persistent
  String nome;
  
  @Persistent
  Double prezzo;

  @Persistent (dependentKey="false")
  Key tipoArticoloId;
  
  @UnownedRelationship (key="tipoArticoloId")
  transient TipoArticoloDs tipoArticolo;
  
  @Persistent (dependentKey="false")
  Key umId;
  
  @UnownedRelationship (key="umId")
  transient UnitaDiMisuraDs unitaDiMisura;
  
  @Persistent Integer confezione;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> imageKeys;
  
  @UnownedRelationship (key="imageKeys", itemClass=ImageWebContentDs.class)
  transient List<ImageWebContentDs> images = new ArrayList<ImageWebContentDs>();
  
  
  /*
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<HtmlProductContentDs> htmls;
  */
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> htmlKeys;
  
  @UnownedRelationship (key="htmlKeys", itemClass=HtmlWebContentDs.class)
  transient List<HtmlWebContentDs> htmls;
  
  

  @Persistent
  Integer orderNm;
  
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  Key produttoreId;

  @UnownedRelationship (key="produttoreId")
  transient ProduttoreDs produttore;
  
  
  @Override
  public String toString() {
    return "ProductDs [code=" + codice + ", id=" + id + ", price=" + prezzo + 
    ", screenName=" + nome + ", typeId=" + tipoArticoloId + 
    ", imageSmallDetached=" + isImageSmallInitialized() +
    ", imageSmall.len=" + (getImageSmall() != null ? getImageSmall().getBytes().length : "null") +
    ", imageMediumDetached=" + isImageMediumInitialized() +
    ", imageMedium.len=" + (getImageMedium() != null ? getImageMedium().getBytes().length : "null") +
    "]";
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

  public String getCodice() {
    return codice;
  }

  public void setCodice(String code) {
    this.codice = code;
  }

  public String getName() {
    return nome;
  }

  public void setName(String screenName) {
    this.nome = screenName;
  }
  
  public String getNome() {
    return getName();
  }

  public void setNome(String code) {
    setName(code);
  }

  public Double getPrezzo() {
    return prezzo;
  }

  public void setPrezzo(Double price) {
    this.prezzo = price;
  }

  public TipoArticolo getTipoArticolo() {
    return tipoArticolo;
  }

  @CloneableProperty (targetClass=TipoArticoloDs.class)
  public void setTipoArticolo(TipoArticolo type) {
    this.tipoArticolo = (TipoArticoloDs)type;
    this.tipoArticoloId = type != null ? ((TipoArticoloDs)type).getKey() : null;
  }
  
  public UnitaDiMisura getUnitaDiMisura() {
    return unitaDiMisura;
  }

  @CloneableProperty (targetClass=UnitaDiMisuraDs.class)
  public void setUnitaDiMisura(UnitaDiMisura unitOfMeasure) {
    this.unitaDiMisura = (UnitaDiMisuraDs)unitOfMeasure;
    this.umId = unitOfMeasure != null ? ((UnitaDiMisuraDs)unitOfMeasure).getKey() : null;
  }
  
  public Integer getConfezione() {
    return confezione;
  }

  public void setConfezione(Integer packetSize) {
    this.confezione = packetSize;
  }

  public Produttore getProducer() {
    return produttore;
  }

  @CloneableProperty (targetClass=ProduttoreDs.class)
  public void setProducer(Produttore producer) {
    this.produttore = (ProduttoreDs)producer;
    this.produttoreId = producer != null ? ((ProduttoreDs)producer).getKey() : null;
  }
  
  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  
  
  /************************************************************
   *  GESTIONE IMAGES
   */

  
  
  public List<ImageContent> getImages() {
    return images != null ? new ArrayList<ImageContent>(images) : null;
  }

  @CloneableProperty (targetClass=ImageWebContentDs.class)
  public void setImages(List<ImageContent> images) {
    this.imageKeys = new ArrayList<Key>();
    this.images = new ArrayList<ImageWebContentDs>();
    if (images != null) {
      for (ImageContent image : images) {
        if (image instanceof ImageWebContentDs) {
          ImageWebContentDs imageDs = (ImageWebContentDs)image;
          attachImage(imageDs);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + image.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
    }
  }
  
  public void resolveImages() {
    CollectionUtils.traverseCollection(this.imageKeys);
  }

  public boolean areImagesInitialized() {
    return images != null;
  }
  
  private Blob getImageX(ImageContent.Type type) {
    ImageWebContentDs image = getImageContentDs(type);
    if (image != null) {
      return image.getContent();
    }
    return null;
  }
  
  public void setImageX(ImageContent.Type type, Blob content) {
    ImageWebContentDs image = getImageContentDs(type);
    if (image == null) {
      image = new ImageWebContentDs(type);
      attachImage(image);
    }
    image.setContent(content);
  }

  public Blob getImageSmall() {
    return getImageX(ImageContent.Type.SMALL);
  }
  
  private boolean isImageSmallInitialized() {
    return getImageX(ImageContent.Type.SMALL) != null;
  }
  
  public void setImageSmall(Blob content) {
    setImageX(ImageContent.Type.SMALL, content);
  }

  public Blob getImageMedium() {
    return getImageX(ImageContent.Type.MEDIUM);
  }
  
  private boolean isImageMediumInitialized() {
    return getImageX(ImageContent.Type.MEDIUM) != null;
  }
  
  public void setImageMedium(Blob content) {
    setImageX(ImageContent.Type.MEDIUM, content);
  }

  protected ImageWebContentDs attachImage(ImageWebContentDs image) {
    if (images == null)
      images = new ArrayList<ImageWebContentDs>();
    images.add(image);
    image.setEntity(this);
    if (image.getKey() != null) {
      if (imageKeys == null)
        imageKeys = new ArrayList<Key>();
      imageKeys.add(image.getKey());
    }
    return image;
  }
  
  private ImageWebContentDs getImageContentDs(ImageContent.Type type) {
    if (areImagesInitialized()) {
      for (ImageContent image : images) {
        if (image.getType() == type) {
          return (ImageWebContentDs)image;
        }
      }
    }
    return null;
  }

  
  public List<Key> getImageKeys() {
    return imageKeys;
  }

  public ImageContent getAttachedImage(ImageContent image) {
    return getImageContentDs(image.getType());
  }
  
  
  public ImageContent setAttachedImage(ImageContent image) {
    ImageContent attachedImage = getAttachedImage(image);
    if (attachedImage == null) {
      attachedImage = attachImage((ImageWebContentDs)image);
    } else {
      attachedImage.setContentString(image.getContentString());
    }
    return attachedImage;
  }
  
  
  /************************************************************
   *  GESTIONE HTMLS
   */



  
  @Override
  public List<HtmlContent> getHtmls() {
    return htmls != null ? new ArrayList<HtmlContent>(htmls) : null;
  }

  @Override
  @CloneableProperty (targetClass=HtmlWebContentDs.class)
  public void setHtmls(List<HtmlContent> htmls) {
    if (htmls == null) {
      this.htmlKeys = null;
      this.htmls = null;
    } else {
      this.htmlKeys = new ArrayList<Key>();
      this.htmls = new ArrayList<HtmlWebContentDs>();
      for (HtmlContent html : htmls) {
        if (html instanceof HtmlWebContentDs) {
          HtmlWebContentDs htmlDs = (HtmlWebContentDs)html;
          attachHtml(htmlDs);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + html.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
    }
  }

  public void initializeHtmls() {
    CollectionUtils.traverseCollection(this.htmlKeys);
  }
  
  public HtmlContent getAttachedHtml(HtmlContent html) {
    if (htmls != null) {
      for (HtmlContent attachedHtml : htmls) {
        if (attachedHtml.getType() == html.getType()) {
          return attachedHtml;
        }
      }
    }
    return null;
  }

  public HtmlContent setAttachedHtml(HtmlContent html) {
    HtmlContent attachedHtml = getAttachedHtml(html);
    if (attachedHtml == null) {
      attachedHtml = attachHtml(html);
    } else {
      attachedHtml.setContent(html.getContent());
    }
    return attachedHtml;
  }
  
  public HtmlContent attachHtml(HtmlContent html) {
    if (html instanceof HtmlWebContentDs) {
      HtmlWebContentDs htmlDs = (HtmlWebContentDs)html;
      if (htmls == null)
        htmls = new ArrayList<HtmlWebContentDs>();
      htmls.add(htmlDs);
      if (htmlDs.getKey() != null) {
        if (htmlKeys == null)
          htmlKeys = new ArrayList<Key>();
        htmlKeys.add(htmlDs.getKey());
      }
      htmlDs.setEntity(this);
    }
    return html;
  }
  

  @Override
  public boolean areHtmlsInitialized() {
    return htmls != null;
  }

  @Override
  public void ensureHtmls() {
    
  }

  @Override
  public HtmlContent getHtmlContent(Type type) {
    if (htmls != null) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return html;
        }
      }
    }
    return null;
  }

  public void setHtmlContent(HtmlContent content) {
    throw new NotImplementedException();
  }

  
  
  

  /************************************************************
  
  public void initializeHtmls() {
    CollectionUtils.traverseCollection(this.htmls);
  }

  public boolean areHtmlsInitialized() {
    return htmls != null;
  }
  
  public List<HtmlContent> getHtmls() {
    return htmls != null ? new ArrayList<HtmlContent>(htmls) : null;
  }

  @CloneableProperty (targetClass=HtmlProductContentDs.class)
  public void setHtmls(List<HtmlContent> htmls) {
    if (htmls != null) {
      for (HtmlContent html : htmls) {
        internalSetHtmlContent(html);
      }
    }
  }
  
  public void setHtmlContent(HtmlContent content) {
    HtmlProductContentDs html = getHtmlProductContentDs(content.getType());
    if (html == null) {
      html = new HtmlProductContentDs(content.getType().getCode());
      internalSetHtmlContent(html);
    }
    html.setContent(content.getContent());
  }
  
  @Override
  public HtmlContent getHtmlContent(HtmlContent.Type type) {
    if (htmls != null) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return html;
        }
      }
    }
    return null;
  }
  
  public boolean containsHtmlContent(HtmlContent.Type type) {
    return getHtmlContent(type) != null;
  }

  private HtmlProductContentDs getHtmlProductContentDs(HtmlContent.Type type) {
    if (areHtmlsInitialized()) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return (HtmlProductContentDs)html;
        }
      }
    }
    return null;
  }
  
  public void ensureHtmls() {
    
  }
  
  private void internalSetHtmlContent (HtmlContent html) {
    if (this.htmls == null) {
      this.htmls = new ArrayList<HtmlProductContentDs>();
    }
    ((List<HtmlProductContentDs>)htmls).add((HtmlProductContentDs)html);
    htmls.get(htmls.size() - 1).setEntity(this);
  }
  
  ***********************************************************/

}

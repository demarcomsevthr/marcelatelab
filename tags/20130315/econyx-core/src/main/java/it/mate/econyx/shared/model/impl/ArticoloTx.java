package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.HtmlContent.Type;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"serial", "unchecked"})
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.ArticoloDs")
public class ArticoloTx implements Articolo {

  String id;
  
  String codice;
  
  String nome;
  
  Double prezzo;
  
  TipoArticolo tipoArticolo;
  
  UnitaDiMisura unitaDiMisura;
  
  Integer confezione;
  
  Integer orderNm;
  
  Produttore produttore;
  
  
  List<? extends ImageContent> images = new ArrayList<ImageContentTx>();
  
  List<? extends HtmlContent> htmls = new ArrayList<HtmlContentTx>();
  
  boolean htmlsInitialized = false;
  
  
  @Override
  public String toString() {
    return "ProductTx [code=" + codice + ", id=" + id + ", price=" + prezzo + ", screenName=" + nome + ", type="
        + tipoArticolo + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ArticoloTx other = (ArticoloTx) obj;
    if (codice == null) {
      if (other.codice != null)
        return false;
    } else if (!codice.equals(other.codice))
      return false;
    return true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
  
  @CloneableProperty (targetClass=TipoArticoloTx.class)
  public void setTipoArticolo(TipoArticolo type) {
    if (type == null) {
      this.tipoArticolo = null;
    } else if (type instanceof TipoArticoloTx) {
      this.tipoArticolo = (TipoArticoloTx)type;
    } else {
      throw new IllegalArgumentException("cannot set type of type " + type.getClass());
    }
  }
  
  public UnitaDiMisura getUnitaDiMisura() {
    return unitaDiMisura;
  }

  @CloneableProperty (targetClass=UnitaDiMisuraTx.class)
  public void setUnitaDiMisura(UnitaDiMisura unitOfMeasure) {
    if (unitOfMeasure == null) {
      this.unitaDiMisura = null;
    } else if (unitOfMeasure instanceof UnitaDiMisuraTx) {
      this.unitaDiMisura = unitOfMeasure;
    } else {
      throw new IllegalArgumentException("cannot set unitOfMeasure of type " + unitOfMeasure.getClass());
    }
  }
  
  public Integer getConfezione() {
    return confezione;
  }

  public void setConfezione(Integer packetSize) {
    this.confezione = packetSize;
  }

  public List<ImageContent> getImages() {
    return (List<ImageContent>)images;
  }
  
  @CloneableProperty (targetClass=ImageContentTx.class)
  public void setImages(List<ImageContent> images) {
    this.images = images;
  }
  
  
  public boolean hasImageOfType (ImageContent.Type type) {
    if (images != null) {
      for (ImageContent image : images) {
        if (image.getType() == type) {
          return true;
        }
      }
    }
    return false;
  }
  
  
  public List<HtmlContent> getHtmls() {
    return (List<HtmlContent>)htmls;
  }
  
  @CloneableProperty (targetClass=HtmlContentTx.class)
  public void setHtmls(List<HtmlContent> htmls) {
    this.htmls = htmls;
  }

  public boolean areHtmlsInitialized() {
    return htmlsInitialized;
  }
  
  public void setHtmlsInitialized(boolean htmlsInitialized) {
    this.htmlsInitialized = htmlsInitialized;
  }

  public void ensureHtmls() {
    if (htmls == null) {
      htmls = new ArrayList<HtmlContentTx>();
    }
  }
  
  public HtmlContent getHtmlContent(Type type) {
    HtmlContent html = getHtmlOfType(type);
    if (html != null) {
      return html;
    }
    html = HtmlContent.Factory.createOnClient(this, type);
    addHtmlTx(html);
    return html;
  }
  
  public boolean hasHtmlOfType(HtmlContent.Type type) {
    return getHtmlOfType(type) != null;
  }
  
  public HtmlContent getHtmlOfType(HtmlContent.Type type) {
    ensureHtmls();
    for (HtmlContent html : htmls) {
      if (html.getType() == type) {
        return html;
      }
    }
    return null;
  }
  
  private void addHtmlTx(HtmlContent html) {
    ((List<HtmlContentTx>)htmls).add((HtmlContentTx)html);
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public Produttore getProducer() {
    return produttore;
  }

  @CloneableProperty (targetClass=ProduttoreTx.class)
  public void setProducer(Produttore producer) {
    this.produttore = producer;
  }
  
}

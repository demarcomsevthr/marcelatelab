package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.server.model.impl.HtmlWebContentDs;
import it.mate.econyx.server.model.impl.ImageWebContentDs;
import it.mate.econyx.server.model.impl.ProduttoreDs;
import it.mate.econyx.server.model.impl.TipoArticoloDs;
import it.mate.econyx.server.model.impl.UnitaDiMisuraDs;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.util.SortUtils;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.econyx.shared.model.impl.ProduttoreTx;
import it.mate.econyx.shared.model.impl.TipoArticoloTx;
import it.mate.econyx.shared.model.impl.UnitaDiMisuraTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.FindCallback;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.model.utils.OneToManyAdapterSupport;
import it.mate.gwtcommons.server.utils.CloneUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductAdapterImpl implements ProductAdapter {

  private static Logger logger = Logger.getLogger(ProductAdapterImpl.class);

  @Autowired private Dao dao;
  
  private List<TipoArticolo> productTypes = null;
  
  private List<UnitaDiMisura> unitOfMeasures = null;
  
  private List<Produttore> producers = null;
  
  OneToManyAdapterSupport<AbstractArticoloDs, HtmlContent> htmlRelationshipSupport;
  
  OneToManyAdapterSupport<AbstractArticoloDs, ImageContent> imageRelationshipSupport;
  
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    htmlRelationshipSupport = new OneToManyAdapterSupport<AbstractArticoloDs, HtmlContent>(
        dao, "getHtmls", "setHtmls", "initializeHtmls", "getAttachedHtml", "setAttachedHtml");
    // FINIRE DI UTILIZZARE imageRelationshipSupport NELLE OPERAIZONI DI UPDATE, DELETE E UPDATEIMAGE
    imageRelationshipSupport = new OneToManyAdapterSupport<AbstractArticoloDs, ImageContent>(
        dao, "getImages", "setImages", "resolveImages", "getAttachedImage", "setAttachedImage");
  }

  public List<Articolo> findAll() {
    List<AbstractArticoloDs> products = dao.findAll(AbstractArticoloDs.class);
    Collections.sort(products, new Comparator<AbstractArticoloDs>() {
      public int compare(AbstractArticoloDs a1, AbstractArticoloDs a2) {
        return a1.getCodice().compareTo(a2.getCodice());
      }
    });
    return CloneUtils.clone(SortUtils.sortProductsByOrderNm(products), ArticoloTx.class, Articolo.class);
  }
  
  public Articolo findById (String id) {
    Articolo ds = dao.findById(AbstractArticoloDs.class, id);
    return CloneUtils.clone(ds, ArticoloTx.class);
  }

  public Produttore findProducerById (String id) {
    Produttore ds = dao.findById(ProduttoreDs.class, id);
    return CloneUtils.clone(ds, ProduttoreTx.class);
  }

  public Articolo update (Articolo entity) {
    AbstractArticoloDs articoloDs = CloneUtils.clone(entity, AbstractArticoloDs.class);
    if (htmlRelationshipSupport != null) {
      htmlRelationshipSupport.onBeforeUpdate(articoloDs);
    }
    articoloDs = dao.update(articoloDs);
    return CloneUtils.clone (articoloDs, ArticoloTx.class);
  }
  
  public AbstractArticoloDs updateDs (AbstractArticoloDs articoloDs, boolean updateImages, boolean updateHtmls) {
    if (htmlRelationshipSupport != null) {
      htmlRelationshipSupport.onBeforeUpdate(articoloDs);
    }
    articoloDs = dao.update(articoloDs);
    if (updateImages) {
      articoloDs = updateImages(articoloDs);
    }
    return articoloDs;
  }
  
  public void delete (Articolo entity) {
    AbstractArticoloDs articoloDs = CloneUtils.clone(entity, AbstractArticoloDs.class);
    if (htmlRelationshipSupport != null) {
      htmlRelationshipSupport.onBeforeDelete(articoloDs);
    }
    deleteImages(articoloDs);
    dao.delete(articoloDs);
  }
  
  public Articolo create (Articolo product) {
    setDefaults(product);
    AbstractArticoloDs articoloDs = CloneUtils.clone(product, AbstractArticoloDs.class);
    if (htmlRelationshipSupport != null) {
      htmlRelationshipSupport.onBeforeCreate(articoloDs);
    }
    if (imageRelationshipSupport != null) {
      imageRelationshipSupport.onBeforeCreate(articoloDs);
    }
    articoloDs = dao.create(articoloDs);
    return CloneUtils.clone(articoloDs, ArticoloTx.class);
  }
  
  public Produttore create (Produttore producer) {
    return CloneUtils.clone(dao.create( CloneUtils.clone(producer, ProduttoreDs.class)  ), ProduttoreTx.class);
  }
  
  public List<TipoArticolo> findAllProductTypes() {
    return findAllProductTypes(false);
  }
  
  public List<UnitaDiMisura> findAllUnitOfMeasures() {
    return findAllUnitOfMeasures(false);
  }
  
  public List<Produttore> findAllProducers() {
    return findAllProducers(false);
  }
  
  public List<Produttore> findAllProducers(boolean useCache) {
    if (!useCache || this.producers == null) {
      this.producers = CloneUtils.clone(dao.findAll(ProduttoreDs.class), ProduttoreTx.class, Produttore.class);
    }
    return this.producers;
  }
  
  public void delete (TipoArticolo entity) {
    dao.delete(CloneUtils.clone(entity, TipoArticoloDs.class));
  }

  public void delete (UnitaDiMisura entity) {
    dao.delete(CloneUtils.clone(entity, UnitaDiMisuraDs.class));
  }

  public void delete (Produttore entity) {
    dao.delete(CloneUtils.clone(entity, ProduttoreDs.class));
  }

  public TipoArticolo create (TipoArticolo entity) {
    return CloneUtils.clone(dao.create(CloneUtils.clone(entity, TipoArticoloDs.class)), TipoArticoloTx.class);
  }
  
  public UnitaDiMisura create (UnitaDiMisura entity) {
    return CloneUtils.clone(dao.create(CloneUtils.clone(entity, UnitaDiMisuraDs.class)), UnitaDiMisuraTx.class);
  }
  
  public AbstractArticoloDs fetchImages (Serializable id) {
    return fetchImages(id, false);
  }
  
  public AbstractArticoloDs fetchImages (Serializable id, boolean cacheDisabled) {
    FindContext<AbstractArticoloDs> context = new FindContext<AbstractArticoloDs>(AbstractArticoloDs.class);
    context.setId(id);
    // 17/11/2012
    context.setCacheDisabled(cacheDisabled);
    context.setCallback(new FindCallback<AbstractArticoloDs>() {
      public void processResultsInTransaction(AbstractArticoloDs productDs) {
        productDs.resolveImages();
      }
    });
    AbstractArticoloDs articoloDs = dao.findById(context);
    return articoloDs;
  }
  
  public AbstractArticoloDs fetchImages (AbstractArticoloDs product) {
    if (product.areImagesInitialized()) {
      return product;
    } else {
      return fetchImages(product.getId());
    }
  }
  
  @Override
  public Articolo fetchHtmls (Articolo product) {
    if (product.getId() == null || product.areHtmlsInitialized()) {
      return product;
    } else {
      AbstractArticoloDs productDs = fetchHtmls(product.getId());
      ArticoloTx result = CloneUtils.clone(productDs, ArticoloTx.class);
      result.setHtmlsInitialized(true);
      return result;
    }
  }
  
  @Override
  public Articolo updateHtmlContent (String productId, HtmlContent html) {
    if (productId == null)
      return null;
    AbstractArticoloDs productDs = fetchHtmls(productId);
    if (htmlRelationshipSupport != null) {
      HtmlContent detachedHtml = CloneUtils.clone(html, HtmlWebContentDs.class);
      productDs = htmlRelationshipSupport.onUpdateItem(productDs, detachedHtml);
    } else {
      productDs.setHtmlContent(html);
      productDs = updateDs(productDs, false, false);
    }
    ArticoloTx productTx = CloneUtils.clone(productDs, ArticoloTx.class);
    return productTx;
  }
  
  public Articolo resolveAllDependencies(Articolo articolo) {
    articolo = fetchHtmls(articolo);
    return articolo;
  }
  
  private void setDefaults(Articolo product) {
    if (product.getTipoArticolo() == null) {
      List<TipoArticolo> productTypes = findAllProductTypes(true);
      if (productTypes != null && productTypes.size() > 0) {
        product.setTipoArticolo(productTypes.get(0));
      }
    }
  }
  
  private List<TipoArticolo> findAllProductTypes(boolean useCache) {
    if (!useCache || this.productTypes == null) {
      this.productTypes = CloneUtils.clone(dao.findAll(TipoArticoloDs.class), TipoArticoloTx.class, TipoArticolo.class);
    }
    return this.productTypes;
  }
  
  private List<UnitaDiMisura> findAllUnitOfMeasures(boolean useCache) {
    if (!useCache || this.unitOfMeasures == null) {
      this.unitOfMeasures = CloneUtils.clone(dao.findAll(UnitaDiMisuraDs.class), UnitaDiMisuraTx.class, UnitaDiMisura.class);
    }
    return this.unitOfMeasures;
  }
  
  private AbstractArticoloDs updateImages (AbstractArticoloDs articoloDs) {
    boolean needUpdate = false;
    List<ImageContent> updateImages = new ArrayList<ImageContent>();
    List<ImageContent> actualImages = articoloDs.getImages();
    if (actualImages != null) {
      for (ImageContent image : actualImages) {
        if (image instanceof ImageWebContentDs) {
          ImageWebContentDs imageDs = (ImageWebContentDs)image;
          if (imageDs.getKey() == null) {
            imageDs = dao.create(imageDs);
          } else {
            imageDs = dao.update(imageDs);
          }
          updateImages.add(imageDs);
          needUpdate = true;
        }
      }
    }
    if (needUpdate) {
      articoloDs.setImages(updateImages);
      articoloDs = dao.update(articoloDs);
    }
    return articoloDs;
  }
  
  private AbstractArticoloDs fetchHtmls (Serializable id) {
    return dao.findById(AbstractArticoloDs.class, id, 
        new FindCallback<AbstractArticoloDs>() {
          public void processResultsInTransaction(AbstractArticoloDs product) {
            product.initializeHtmls();
          }
        }
    );
  }
  
  private void deleteImages (AbstractArticoloDs entity) {
    AbstractArticoloDs articolo = fetchImages(entity);
    if (articolo.getImages() != null) {
      List<ImageContent> images = articolo.getImages();
      for (ImageContent image : images) {
        dao.delete(CloneUtils.clone(image, ImageWebContentDs.class));
      }
    }
  }

}

package it.mate.econyx.server.services;

import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;

import java.io.Serializable;
import java.util.List;

public interface ProductAdapter {

  public List<Articolo> findAll();

  public Articolo update(Articolo entity);

  public AbstractArticoloDs updateDs (AbstractArticoloDs entity, boolean updateImages, boolean updateHtmls);
  
  public void delete(Articolo entity);

  public Articolo create(Articolo entity);

  public Articolo findById(String id);

  public Articolo fetchHtmls(Articolo product);

  public Articolo updateHtmlContent(String productId, HtmlContent content);
  
  public AbstractArticoloDs fetchImages (Serializable id);
  
  public AbstractArticoloDs fetchImages (Serializable id, boolean cacheDisabled);
  
  public TipoArticolo create (TipoArticolo entity);  
  
  public List<TipoArticolo> findAllProductTypes();
  
  public void delete (TipoArticolo entity);
  
  public void delete (UnitaDiMisura entity);
  
  public void delete (Produttore entity);
  
  public UnitaDiMisura create (UnitaDiMisura entity);
  
  public List<UnitaDiMisura> findAllUnitOfMeasures();
  
  public Produttore create (Produttore entity);
  
  public List<Produttore> findAllProducers();
  
  public Articolo resolveAllDependencies(Articolo articolo);
  
  public Produttore findProducerById (String id);
  
  public List<Articolo> findProductsByProducerId(Serializable producerId);

  public Produttore findProducerByCode(String code);
  
  public Articolo findProductByCode(String code);
  
}

package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".productService")
public interface ProductService extends RemoteService {
  
  public List<Articolo> findAll() throws ServiceException;

  public Articolo update(Articolo entity) throws ServiceException;

  public void delete(Articolo entity) throws ServiceException;

  public Articolo create(Articolo entity) throws ServiceException;

  public Articolo findById(String id) throws ServiceException;

  public Articolo fetchHtmls(Articolo product) throws ServiceException;

  public Articolo updateHtmlContent(String productId, HtmlContent content) throws ServiceException;
  
  public List<Produttore> findAllProducers();
  
}

package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Produttore;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {

  void findAll(AsyncCallback<List<Articolo>> callback);

  void update(Articolo entity, AsyncCallback<Articolo> callback);

  void delete(Articolo entity, AsyncCallback<Void> callback);

  void create(Articolo entity, AsyncCallback<Articolo> callback);

  void findById(String id, AsyncCallback<Articolo> callback);

  void fetchHtmls(Articolo product, AsyncCallback<Articolo> callback);

  void updateHtmlContent(String productId, HtmlContent content, AsyncCallback<Articolo> callback);

  void findAllProducers(AsyncCallback<List<Produttore>> callback);

}

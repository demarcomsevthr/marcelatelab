package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Image;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImageServiceAsync {

  void create(Image entity, AsyncCallback<Image> callback);

  void delete(Image entity, AsyncCallback<Void> callback);

  void findAll(AsyncCallback<List<Image>> callback);

  void update(Image entity, AsyncCallback<Image> callback);

  void findById(String id, AsyncCallback<Image> callback);

}

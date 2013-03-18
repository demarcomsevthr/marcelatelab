package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.ArticleFolder;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ArticleServiceAsync {

  void findAll(AsyncCallback<List<ArticleFolder>> callback);

  void update(ArticleFolder entity, AsyncCallback<ArticleFolder> callback);

  void delete(ArticleFolder entity, AsyncCallback<Void> callback);

  void findById(String id, AsyncCallback<ArticleFolder> callback);

  void create(ArticleFolder entity, AsyncCallback<ArticleFolder> callback);

}

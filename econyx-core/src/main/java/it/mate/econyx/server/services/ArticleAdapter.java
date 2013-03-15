package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;

import java.util.List;

public interface ArticleAdapter {

  public List<ArticleFolder> findAll();
  
  public ArticleFolder update(ArticleFolder entity);

  public void delete(ArticleFolder entity);

  public ArticleFolder create(ArticleFolder entity);

  public ArticleFolder findById(String id);
  
  public Article findByCode(String code);
  
}

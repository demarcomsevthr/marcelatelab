package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".articleService")
public interface ArticleService extends RemoteService {
  
  public List<ArticleFolder> findAll() throws ServiceException;
  
  public ArticleFolder create(ArticleFolder entity) throws ServiceException;

  public ArticleFolder update(ArticleFolder entity) throws ServiceException;

  public void delete(ArticleFolder entity) throws ServiceException;

  public ArticleFolder findById(String id) throws ServiceException;

}

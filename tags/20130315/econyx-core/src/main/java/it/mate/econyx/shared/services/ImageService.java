package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Image;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".imageService")
public interface ImageService extends RemoteService {

  public List<Image> findAll() throws ServiceException;

  public Image findById(String id) throws ServiceException;
  
  public Image update(Image entity) throws ServiceException;

  public void delete(Image entity) throws ServiceException;

  public Image create(Image entity) throws ServiceException;

}

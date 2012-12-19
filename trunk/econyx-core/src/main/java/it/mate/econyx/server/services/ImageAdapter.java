package it.mate.econyx.server.services;

import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.shared.model.Image;

import java.util.List;

public interface ImageAdapter {

  public List<Image> findAll();

  public Image update(Image entity);

  public ImageDs updateDs (ImageDs entity);
  
  public void delete(Image entity);

  public Image create(Image entity);

  public Image findById(String id);
  
  public ImageDs findByCode (String code);

}

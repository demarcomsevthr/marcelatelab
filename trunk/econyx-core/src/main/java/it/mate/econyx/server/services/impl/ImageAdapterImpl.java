package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.impl.ImageTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImageAdapterImpl implements ImageAdapter {

  private static Logger logger = Logger.getLogger(ImageAdapterImpl.class);

  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  public List<Image> findAll() {
    List<ImageDs> images = dao.findAll(ImageDs.class);
    return CloneUtils.clone(images, ImageTx.class, Image.class);
  }
  
  public Image findById (String id) {
    Image image = dao.findById(ImageDs.class, id);
    return CloneUtils.clone(image, ImageTx.class);
  }

  public ImageDs findByCode (String code) {
    ImageDs image = dao.findSingle(ImageDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code );
    return image;
  }

  public Image update (Image image) {
    validateImage(image);
    return CloneUtils.clone (updateDs(CloneUtils.clone(image, ImageDs.class)), ImageTx.class);
  }
  
  public void delete (Image image) {
    dao.delete(CloneUtils.clone(image, ImageDs.class));
  }

  public Image create (Image image) {
    validateImage(image);
    return CloneUtils.clone(dao.create( CloneUtils.clone(image, ImageDs.class)  ), ImageTx.class);
  }
  
  private void validateImage(Image image) {
    if (image.getCode() == null || image.getCode().trim().length() == 0) {
      throw new ServiceException("Inserire il codice dell'immagine");
    }
    if (image.getCode().contains(" ")) {
      throw new ServiceException("Il codice non può contenere spazi");
    }
    Image found = findByCode(image.getCode());
    if (found != null) {
      throw new ServiceException("Esiste già un'altra immagine con lo stesso codice");
    }
  }

  public ImageDs updateDs(ImageDs image) {
    return dao.update(image);
  }
  
}

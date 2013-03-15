package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.impl.ImageTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.utils.CloneUtils;

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
    Image ds = dao.findById(ImageDs.class, id);
    return CloneUtils.clone(ds, ImageTx.class);
  }

  public ImageDs findByCode (String code) {
//  Image ds = dao.findById(ImageDs.class, id);
    ImageDs ds = dao.findSingle(ImageDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code );
    return ds;
  }

  public Image update (Image entity) {
    return CloneUtils.clone (updateDs(CloneUtils.clone(entity, ImageDs.class)), ImageTx.class);
  }
  
  public void delete (Image entity) {
    dao.delete(CloneUtils.clone(entity, ImageDs.class));
  }

  public Image create (Image entity) {
    return CloneUtils.clone(dao.create( CloneUtils.clone(entity, ImageDs.class)  ), ImageTx.class);
  }

  public ImageDs updateDs(ImageDs entity) {
    return dao.update(entity);
  }
  
}

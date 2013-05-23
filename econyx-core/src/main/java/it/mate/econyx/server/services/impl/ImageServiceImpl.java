package it.mate.econyx.server.services.impl;

import it.mate.commons.server.dao.Dao;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.services.ImageService;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ImageServiceImpl extends RemoteServiceServlet implements ImageService {

  private static Logger logger = Logger.getLogger(ImageServiceImpl.class);

  @Autowired private Dao dao;
  
  private ImageAdapter adapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getImageAdapter();
    logger.debug("initialized " + this);
  }
  
  public List<Image> findAll() {
    return adapter.findAll();
  }

  public Image create(Image entity) {
    return adapter.create(entity);
  }

  public void delete(Image entity) {
    adapter.delete(entity);
  }

  public Image update(Image entity) {
    return adapter.update(entity);
  }

  public Image findById(String id) {
    return adapter.findById(id);
  }
  
}

package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.CalEventAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.services.CalEventService;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service (".calEventService")
public class CalEventServiceImpl extends RemoteServiceServlet implements CalEventService {

  private static Logger logger = Logger.getLogger(CalEventServiceImpl.class);
  
  private CalEventAdapter adapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getCalEventAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  public List<CalEvent> findAll() throws ServiceException {
    return adapter.findAll();
  }

  @Override
  public CalEvent create(CalEvent entity) throws ServiceException {
    return adapter.create(entity);
  }

  @Override
  public CalEvent update(CalEvent entity) throws ServiceException {
    return adapter.update(entity);
  }

  @Override
  public void delete(CalEvent entity) throws ServiceException {
    adapter.delete(entity);
  }

  @Override
  public CalEvent findById(String id) throws ServiceException {
    return adapter.findById(id);
  }

  @Override
  public List<CalEvent> findByDate(Date date) throws ServiceException {
    return adapter.findByDate(date);
  }

}

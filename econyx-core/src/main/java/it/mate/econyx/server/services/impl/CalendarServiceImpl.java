package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.CalendarAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.server.util.ServletThreadUtils;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;
import it.mate.econyx.shared.services.CalendarService;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service (".calendarService")
public class CalendarServiceImpl extends RemoteServiceServlet implements CalendarService {

  private static Logger logger = Logger.getLogger(CalendarServiceImpl.class);
  
  private CalendarAdapter adapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getCalendarAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  protected void onBeforeRequestDeserialized(String serializedRequest) {
    ServletThreadUtils.set(getThreadLocalRequest(), getThreadLocalResponse());
    PortalSessionStateServerUtils.setInThread(AdaptersUtil.getGeneralAdapter().retrievePortalSessionState(getThreadLocalRequest()));
    super.onBeforeRequestDeserialized(serializedRequest);
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
  
  @Override
  public List<CalEvent> findByPeriod(Period period) {
    return adapter.findByPeriod(period);
  }

}

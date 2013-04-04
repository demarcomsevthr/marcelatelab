package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".calendarService")
public interface CalendarService extends RemoteService {
  
  public List<CalEvent> findAll() throws ServiceException;
  
  public CalEvent create(CalEvent entity) throws ServiceException;

  public CalEvent update(CalEvent entity) throws ServiceException;

  public void delete(CalEvent entity) throws ServiceException;

  public CalEvent findById(String id) throws ServiceException;
  
  public List<CalEvent> findByDate(Date date) throws ServiceException;
  
  public List<CalEvent> findByPeriod(Period period);
  
}

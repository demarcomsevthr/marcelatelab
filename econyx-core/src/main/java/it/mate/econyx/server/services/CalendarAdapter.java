package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;

import java.util.Date;
import java.util.List;

public interface CalendarAdapter {

  public List<CalEvent> findAll();
  
  public CalEvent update(CalEvent entity);

  public void delete(CalEvent entity);

  public CalEvent create(CalEvent entity);

  public CalEvent findById(String id);
  
  public List<CalEvent> findByDate(Date date);
  
  public List<CalEvent> findByPeriod(Period period);
  
}

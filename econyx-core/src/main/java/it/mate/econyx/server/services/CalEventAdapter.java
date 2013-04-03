package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.CalEvent;

import java.util.Date;
import java.util.List;

public interface CalEventAdapter {

  public List<CalEvent> findAll();
  
  public CalEvent update(CalEvent entity);

  public void delete(CalEvent entity);

  public CalEvent create(CalEvent entity);

  public CalEvent findById(String id);
  
  public List<CalEvent> findByDate(Date date);
  
}

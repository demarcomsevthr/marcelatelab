package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.CalEventDs;
import it.mate.econyx.server.services.CalEventAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.impl.CalEventTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.utils.CloneUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalEventAdapterImpl implements CalEventAdapter {

  private static Logger logger = Logger.getLogger(CalEventAdapterImpl.class);

  @Autowired private Dao dao;
  
  @Autowired private GeneralAdapter generalAdapter;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  @Override
  public List<CalEvent> findAll() {
    List<CalEventDs> events = dao.findAll(CalEventDs.class);
    if (events != null) {
      Collections.sort(events, new Comparator<CalEvent>() {
        public int compare(CalEvent f1, CalEvent f2) {
          return (f1.getOrderNm() != null && f2.getOrderNm() != null) ? f1.getOrderNm().compareTo(f2.getOrderNm()) : 0;
        }
      });
    }
    return CloneUtils.clone(events, CalEventTx.class, CalEvent.class);
  }

  @Override
  public CalEvent update(CalEvent entity) {
    CalEventDs event = CloneUtils.clone(entity, CalEventDs.class);
    event = dao.update(event);
    return CloneUtils.clone (event, CalEventTx.class);
  }

  @Override
  public void delete(CalEvent entity) {
    CalEventDs event = dao.findById(CalEventDs.class, entity.getId());
    dao.delete(event);
  }

  @Override
  public CalEvent create(CalEvent entity) {
    CalEventDs event = CloneUtils.clone(entity, CalEventDs.class);
    if (event.getCode() == null) {
      event.setCode(getNextCodeCounter());
    }
    event = dao.create(event);
    return CloneUtils.clone (event, CalEventTx.class);
  }

  @Override
  public CalEvent findById(String id) {
    CalEvent event = dao.findById(CalEventDs.class, id);
    return CloneUtils.clone(event, CalEventTx.class);
  }

  @Override
  public List<CalEvent> findByDate(Date date) {
    List<CalEventDs> events = dao.findList(CalEventDs.class, "date == dateParam", Date.class.getName() + " dateParam", null, date);
    return CloneUtils.clone(events, CalEventTx.class, CalEvent.class);
  }
  
  private String getNextCodeCounter() {
    return ""+generalAdapter.findNextCounterValue();
  }
  
}

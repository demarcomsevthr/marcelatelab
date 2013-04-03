package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.CalEvent;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CalEventServiceAsync {

  void create(CalEvent entity, AsyncCallback<CalEvent> callback);

  void delete(CalEvent entity, AsyncCallback<Void> callback);

  void findAll(AsyncCallback<List<CalEvent>> callback);

  void findByDate(Date date, AsyncCallback<List<CalEvent>> callback);

  void findById(String id, AsyncCallback<CalEvent> callback);

  void update(CalEvent entity, AsyncCallback<CalEvent> callback);

}

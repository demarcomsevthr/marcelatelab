package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CalEventPlace;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.services.CalEventServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CalEventActivity extends BaseActivity implements 
    CalEventListView.Presenter,
    CalEventEditView.Presenter {

  private CalEventPlace place;
  
  private CalEventServiceAsync calEventService = AppClientFactory.IMPL.getGinjector().getCalEventService();
  
  public CalEventActivity(CalEventPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    /*
    if (place.getToken().equals(CalEventPlace.EVENT_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalEventView(), panel);
      retrieveModel();
    }
    */
    if (place.getToken().equals(CalEventPlace.EVENT_LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalEventListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(CalEventPlace.EVENT_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalEventEditView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    if (place.getToken().equals(CalEventPlace.EVENT_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(CalEventPlace.EVENT_LIST)) {
      calEventService.findAll(new AsyncCallback<List<CalEvent>>() {
        public void onSuccess(List<CalEvent> results) {
          getView().setModel(results);
        }
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
      });
    }
    if (place.getToken().equals(CalEventPlace.EVENT_EDIT)) {
      getView().setModel(place.getModel());
    }
  }
  
  private void registerHandlers(EventBus eventBus) {

  }

  @Override
  public void onDispose() {
    GwtUtils.log("disposing " + this);
    super.onDispose();
  }
  
  @Override
  public void edit(CalEvent event) {
    goTo(new CalEventPlace(CalEventPlace.EVENT_EDIT, event));
  }

  @Override
  public void update(CalEvent event) {
    if (event.getId() == null) {
      calEventService.create(event, new AsyncCallback<CalEvent>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(CalEvent result) {
          edit(result);
        }
      });
    } else {
      calEventService.update(event, new AsyncCallback<CalEvent>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(CalEvent result) {
          edit(result);
        }
      });
    }
  }

  @Override
  public void delete(CalEvent event) {
    calEventService.delete(event, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        goTo(new CalEventPlace(CalEventPlace.EVENT_LIST));
      }
    });
  }

}

package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CalendarPlace;
import it.mate.econyx.client.util.NavigationUtils;
import it.mate.econyx.client.util.PagesUtils;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.client.view.CalendarView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;
import it.mate.econyx.shared.services.CalendarServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CalendarActivity extends BaseActivity implements 
    CalEventListView.Presenter,
    CalEventEditView.Presenter,
    CalendarView.Presenter {
  
  private CalendarPlace place;
  
  private CalendarServiceAsync calendarService = AppClientFactory.IMPL.getGinjector().getCalendarService();
  
  public CalendarActivity(CalendarPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
    GwtUtils.log("INITIALIZING " + this + " place = " + place);
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    GwtUtils.log("STARTING " + this + " place = " + place);
    registerHandlers(eventBus);
    if (place.getToken().equals(CalendarPlace.CAL_DATE_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalendarDateView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(CalendarPlace.CAL_MONTH_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalendarMonthView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(CalendarPlace.EVENT_LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalEventListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(CalendarPlace.EVENT_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getCalEventEditView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    if (place.getToken().equals(CalendarPlace.CAL_DATE_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(CalendarPlace.CAL_MONTH_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(CalendarPlace.EVENT_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(CalendarPlace.EVENT_LIST)) {
      calendarService.findAll(new AsyncCallback<List<CalEvent>>() {
        public void onSuccess(List<CalEvent> results) {
          getView().setModel(results);
        }
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
      });
    }
    if (place.getToken().equals(CalendarPlace.EVENT_EDIT)) {
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
    goTo(new CalendarPlace(CalendarPlace.EVENT_EDIT, event));
  }

  @Override
  public void update(CalEvent event) {
    if (event.getId() == null) {
      calendarService.create(event, new AsyncCallback<CalEvent>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(CalEvent result) {
          edit(result);
        }
      });
    } else {
      calendarService.update(event, new AsyncCallback<CalEvent>() {
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
    calendarService.delete(event, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        goTo(new CalendarPlace(CalendarPlace.EVENT_LIST));
      }
    });
  }

  @Override
  public void goToDate(final Date date, final List<CalEvent> calEvents) {
    PagesUtils.goToPageByCode(PropertiesHolder.getString("client.CalendarActivity.calendarDatePageCode", "calendarDatePage"));
    GwtUtils.onAvailable("CalendarDateViewImplHiddenDiv", new Delegate<Element>() {
      public void execute(Element element) {
        NavigationUtils.setSelectedCalendarDate(date, calEvents);
      }
    });
  }

  @Override
  public void findByPeriod(Period period) {
    calendarService.findByPeriod(period, new AsyncCallback<List<CalEvent>>() {
      public void onSuccess(List<CalEvent> results) {
        getView().setModel(results);
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }

}

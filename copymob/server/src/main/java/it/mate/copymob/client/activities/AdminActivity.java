package it.mate.copymob.client.activities;


import it.mate.copymob.client.factories.AdminClientFactory;
import it.mate.copymob.client.places.AdminPlace;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.OrdiniView;
import it.mate.copymob.shared.service.RemoteFacadeAsync;
import it.mate.gwtcommons.client.mvp.SingletonBaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class AdminActivity extends SingletonBaseActivity implements HomeView.Presenter, OrdiniView.Presenter
  {
  
  protected AdminPlace place;

  protected RemoteFacadeAsync remoteFacade = AdminClientFactory.IMPL.getGinjector().getRemoteFacade();
  
  private static Map<String, Delegate<AcceptsOneWidget>> startHandlers;
  
  private static Map<String, Delegate<AdminPlace>> retrieveHandlers;
  
  public AdminActivity(AdminClientFactory clientFactory) {
    super(clientFactory);
    GwtUtils.log(getClass(), "init", "instantiating " + this.hashCode());
  }
  
  public void setPlace(AdminPlace place) {
    this.place = place;
  }
  
  private Map<String, Delegate<AcceptsOneWidget>> ensureStartHandlers() {
    if (startHandlers == null) {
      startHandlers = new HashMap<String, Delegate<AcceptsOneWidget>>();
      startHandlers.put(AdminPlace.HOME, new Delegate<AcceptsOneWidget>() {
        public void execute(AcceptsOneWidget panel) {
          initView(AdminClientFactory.IMPL.getGinjector().getHomeView(), panel);
          retrieveModel();
        }
      });
      startHandlers.put(AdminPlace.ORDINI, new Delegate<AcceptsOneWidget>() {
        public void execute(AcceptsOneWidget panel) {
          initView(AdminClientFactory.IMPL.getGinjector().getOrdiniView(), panel);
          retrieveModel();
        }
      });
    }
    return startHandlers;
  }

  private Map<String, Delegate<AdminPlace>> ensureRetrieveHandlers() {
    if (retrieveHandlers == null) {
      retrieveHandlers = new HashMap<String, Delegate<AdminPlace>>();
      retrieveHandlers.put(AdminPlace.HOME, new Delegate<AdminPlace>() {
        public void execute(AdminPlace place) { 
          getView().setModel(place.getModel());
          testServerConnection();
        }
      });
      retrieveHandlers.put(AdminPlace.ORDINI, new Delegate<AdminPlace>() {
        public void execute(AdminPlace place) { 
          getView().setModel(place.getModel());
        }
      });
    }
    return retrieveHandlers;
  }

  @Override
  public void start(final AcceptsOneWidget panel, EventBus eventBus) {
    GwtUtils.log(getClass(), "start", "starting " + this.hashCode());
    Delegate<AcceptsOneWidget> startHandler = ensureStartHandlers().get(place.getToken());
    if (startHandler != null) {
      startHandler.execute(panel);
    } else {
      throw new IllegalStateException("RICEVUTO PLACE CON TOKEN " + place.getToken() + " NON IMPLEMENTATO");
    }
  }
  
  private void retrieveModel() {
    ensureRetrieveHandlers().get(place.getToken()).execute(place);
  }
  
  private void testServerConnection() {
    remoteFacade.checkConnection(new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
        GwtUtils.log("TEST SERVER CONNECTION SUCCESS");
      }
      public void onFailure(Throwable caught) {
        GwtUtils.log("TEST SERVER CONNECTION FAILURE");
      }
    });
  }
  
}

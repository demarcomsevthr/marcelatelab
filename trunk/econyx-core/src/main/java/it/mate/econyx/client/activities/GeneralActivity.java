package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.GeneralPlace;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GeneralActivity extends BaseActivity {

  private GeneralPlace place;
  
  private GeneralServiceAsync generalService = AppClientFactory.IMPL.getGinjector().getGeneralService();
  
  public GeneralActivity(GeneralPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
    registerHandlers(clientFactory.getEventBus());
    
//  RPCUtils.setSynchronizedInterceptor((ServiceDefTarget)generalService);
    
  }

  private void registerHandlers(EventBus eventBus) {
    
  }
  
  @Override
  public void onDispose() {
    super.onDispose();
  }
  
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(GeneralPlace.EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getGeneralConfigView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(GeneralPlace.EDIT)) {
      getView().setModel(place.getModel(), null);
    }
  }
  
  public void loadFoldersData() {
    generalService.loadFoldersData(new AsyncCallback<Void>() {
      public void onSuccess(Void result) {
        Window.alert("Inizializzazione completata");
      }
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
    });
  }
  
  public void exportFoldersData() {
    generalService.exportFoldersData(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Export effettuato");
      }
    });
  }
  
  public void initPagesData() {
    generalService.initPagesData(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Inizializzazione dati test effettuata");
      }
    });
  }
  
  public void loadPagesData() {
    generalService.loadPagesData(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Import effettuato");
      }
    });
  }
  
  public void exportPagesData() {
    generalService.exportPagesData(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Export effettuato");
      }
    });
  }
  
  public void importPortalData() {
    generalService.importPortalData(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Import dei dati accodato");
      }
    });
  }
  
  public void exportPortalData() {
    generalService.exportPortalData(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Export effettuato");
      }
    });
  }
  
  public void clearCache() {
    generalService.clearCache(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Reset cache effettuato");
      }
    });
  }
  
  public void deleteAll() {
    GwtUtils.messageBox("Sei sicuro di voler cancellare tutti i dati?", 
      MessageBox.BUTTONS_YESNO)
      .setCallbacks(new MessageBox.Callbacks() {
        public void onYes() {
          generalService.deleteAll(new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
              Window.alert("Errore: "+caught.getMessage());
            }
            public void onSuccess(Void result) {
              Window.alert("Cancellazione db effettuata");
            }
          });
        }
      });
  }
  
  public void getServerContextUrl() {
    generalService.getServerContextUrl(new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        Window.alert("Errore: "+caught.getMessage());
      }
      public void onSuccess(String result) {
        Window.alert("Url = "+result);
      }
    });
  }
  
  public void reloadProperties() {
    generalService.reloadProperties(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Properties ricaricate");
      }
    });
  }
  
  public void generateRandomCustomers(int number) {
    generalService.generateRandomCustomers(number, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Operazione eseguita");
      }
    });
  }
  
  public void generateRandomOrders(int number) {
    generalService.generateRandomOrders(number, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Operazione eseguita");
      }
    });
  }
  
  public void gdataSpreadsheetTest(final Delegate<String> urlDelegate) {
    generalService.gdataSpreadsheetTest(new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(String result) {
        urlDelegate.execute(result);
      }
    });
  }
  
  public void instanceCacheDump (final Delegate<List<CacheDumpEntry>> delegate) {
    generalService.instanceCacheDump(new AsyncCallback<List<CacheDumpEntry>>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(List<CacheDumpEntry> results) {
        delegate.execute(results);
      }
    });
  }
  
  public void cobraTest() {
    generalService.cobraTest(new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        Window.alert("Test completato");
      }
    });
  }
  
  
}

package it.mate.therapyreminder.client.logic;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.places.MainPlace;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.List;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Timer;

public class CheckSomministrazioniScaduteTask {

  private PlaceController placeController;

  private boolean enabled = true;
  
  private static CheckSomministrazioniScaduteTask inst;
  
  public static CheckSomministrazioniScaduteTask getInstance() {
    if (inst == null) {
      inst = new CheckSomministrazioniScaduteTask(AppClientFactory.IMPL.getPlaceController());
    }
    return inst;
  }
  
  protected CheckSomministrazioniScaduteTask(PlaceController placeController) {
    this.placeController = placeController;
    init();
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  private void init() {
    PhgUtils.log("starting CheckSomministrazioniScaduteTask");
    final ObjectWrapper<Timer> timer = new ObjectWrapper<Timer>();
    timer.set(GwtUtils.createTimer(1000, new Delegate<Void>() {
      public void execute(Void element) {
        if (MainController.getInstance().isReady()) {
          run();
          timer.get().cancel();
          GwtUtils.createTimer(5000, new Delegate<Void>() {
            public void execute(Void element) {
              run();
            }
          });
        }
      }
    }));
  }
  
  public void run() {
    if (!enabled)
      return;
    
    PhgUtils.log("running CheckSomministrazioniScaduteTask");
    
    MainController.getInstance().findSomministrazioniScadute(new Delegate<List<Somministrazione>>() {
      public void execute(final List<Somministrazione> somministrazioni) {
        
        if (somministrazioni != null && somministrazioni.size() > 0) {
          
          if (somministrazioni.size() > 0) {
            
            placeController.goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazioni.get(0)));

          /* 12/07/2014 - tolgo annullamento in blocco
          } else {
            GwtUtils.deferredExecution(500, new Delegate<Void>() {
              public void execute(Void element) {
                PhgDialogUtils.showMessageDialog("Il sistema ha trovato diverse somministrazioni scadute. Vuoi annullarle tutte in blocco?", "Alert", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
                  public void execute(Integer btn) {
                    if (btn == 1) {
                      MainController.getInstance().annullaSomministrazioni(somministrazioni, new Delegate<Void>() {
                        public void execute(Void element) {
                          PhgDialogUtils.showMessageDialog("Somministrazioni annullate");
                          placeController.goTo(new MainPlace());
                        }
                      });
                    } else {
                      placeController.goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazioni.get(0)));
                    }
                  }
                });
              }
            });
            */
            
            
          }
          
        }
        
      }
    });
  }
  
  
}

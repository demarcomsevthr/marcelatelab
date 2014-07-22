package it.mate.therapyreminder.client.logic;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.therapyreminder.client.constants.AppMessages;
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
  
  private static final boolean CHECK_DATA_CONNECTION_FIRST = true;
  
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
    if (!enabled) {
      return;
    }
    if (PhgDialogUtils.isMessageDialogVisible()) {
      return;
    }
    PhgUtils.log("running CheckSomministrazioniScaduteTask");
    MainController.getInstance().findSomministrazioniScadute(new Delegate<List<Somministrazione>>() {
      public void execute(final List<Somministrazione> somministrazioni) {
        if (somministrazioni != null && somministrazioni.size() > 0) {
          if (somministrazioni.size() > 0) {
            final Somministrazione somministrazione = somministrazioni.get(0);
            
            if ( CHECK_DATA_CONNECTION_FIRST && somministrazione.getPrescrizione().isRemote()) {
              MainController.getInstance().checkDataConnectionAvailable(false, new Delegate<Boolean>() {
                public void execute(Boolean available) {
                  if (available) {
                    placeController.goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazione));
                  } else {
                    PhgDialogUtils.showMessageDialog(AppMessages.IMPL.CheckSomministrazioniScaduteTask_run_msg1());
                  }
                }
              });
            } else {
              placeController.goTo(new MainPlace(MainPlace.REMINDER_EDIT, somministrazione));
            }
            
          }
        }
      }
    });
  }
  
}

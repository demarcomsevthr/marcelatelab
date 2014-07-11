package it.mate.therapyreminder.client.logic;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.user.client.Timer;

public class SynchSomministrazioniTask {

  private boolean enabled = true;
  
  private static SynchSomministrazioniTask inst;
  
  public static SynchSomministrazioniTask getInstance() {
    if (inst == null) {
      inst = new SynchSomministrazioniTask();
    }
    return inst;
  }
  
  protected SynchSomministrazioniTask() {
    init();
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  private void init() {
    PhgUtils.log("starting SynchSomministrazioniTask");
    final ObjectWrapper<Timer> timer = new ObjectWrapper<Timer>();
    timer.set(GwtUtils.createTimer(5000, new Delegate<Void>() {
      public void execute(Void element) {
        if (MainController.getInstance().isReady()) {
          run();
          timer.get().cancel();
          GwtUtils.createTimer(20000, true, new Delegate<Void>() {
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
    PhgUtils.log("running SynchSomministrazioniTask");
    MainController.getInstance().synchSomministrazioniInBackground();
  }
  
}

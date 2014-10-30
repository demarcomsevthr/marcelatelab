package it.mate.protoph.client.logic;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.plugins.LocalNotificationsPlugin;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.protoph.client.factories.AppClientFactory;

import java.util.List;

import com.google.gwt.user.client.Timer;

public class MainController {

  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private static MainController instance;
  
  public static MainController getInstance() {
    if (instance == null)
      instance = new MainController();
    return instance;
  }
  
  protected MainController() {
    // setting dao error delegate
    dao.setErrorDelegate(new Delegate<String>() {
      public void execute(String errorMessage) {
        PhgUtils.log(errorMessage);
        PhgDialogUtils.showMessageDialog(errorMessage);
      }
    });

    if (!OsDetectionUtils.isDesktop()) {
      checkOrphanNotifications();
      LocalNotificationsPlugin.setOnCancel(new LocalNotificationsPlugin.JSOEventCallback() {
        public void handleEvent(String id, String state, String json) {
          PhgUtils.log("RECEIVED CANCEL NOTIFICATION EVENT: id="+id);
        }
      });
    }
  }
  
  private void checkOrphanNotifications() {
    final ObjectWrapper<Timer> timer = new ObjectWrapper<Timer>();
    timer.set(GwtUtils.createTimer(1000, new Delegate<Void>() {
      public void execute(Void element) {
        if (MainController.getInstance().isReady()) {
          timer.get().cancel();
          
          // purge orphan notifications
          LocalNotificationsPlugin.getScheduledIds(new Delegate<List<String>>() {
            public void execute(List<String> ids) {
//            MainController.getInstance().purgeNotificationIds(ids);
            }
          });
          
        }
      }
    }));
  }
  
  public boolean isReady() {
    return dao != null && dao.isReady();
  }
  
}
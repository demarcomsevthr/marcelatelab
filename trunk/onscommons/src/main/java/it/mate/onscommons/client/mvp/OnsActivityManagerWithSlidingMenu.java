package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.onscommons.client.onsen.OnsenReadyHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.CdvUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class OnsActivityManagerWithSlidingMenu extends ActivityManager {

  private EventBus eventBus;

  private static Panel activePanel;
  
  private static String activePanelId = "";
  
  private List<OnsTemplate> templates;
  
  private Map<String, Place> placesMap = new HashMap<String, Place>();

  public OnsActivityManagerWithSlidingMenu(ActivityMapper mapper, EventBus eventBus, BaseView<? extends BasePresenter> menuView) {
    super(mapper, eventBus);
    this.eventBus = eventBus;
    if (!OnsenUi.isInitialized()) {
      OnsenUi.initializeOnsen(new OnsenReadyHandler() {
        public void onReady() {
          CdvUtils.log("ONSEN READY");
        }
      });
    }
    setDisplay(new SimplePanel());
  }
  
  public static AcceptsOneWidget getActivePanel() {
    return (AcceptsOneWidget)activePanel;
  }
  
}

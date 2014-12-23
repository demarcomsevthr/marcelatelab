package it.mate.onscommons.client.mvp;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class OnsAbstractActivity extends AbstractActivity {

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
//  start(panel, (com.google.web.bindery.event.shared.EventBus) eventBus);
    start(OnsActivityManager.getActiveTemplate(), (com.google.web.bindery.event.shared.EventBus) eventBus);
  }

  public void start(AcceptsOneWidget panel, com.google.web.bindery.event.shared.EventBus eventBus) {

  }
  
}

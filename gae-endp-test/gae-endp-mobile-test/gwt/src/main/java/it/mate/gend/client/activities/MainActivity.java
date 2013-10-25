package it.mate.gend.client.activities;

import it.mate.gend.client.factories.AppClientFactory;
import it.mate.gend.client.places.MainPlace;
import it.mate.gend.client.view.HomeView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.AndroidBackButtonHandler;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter {

  
  private MainPlace place;
  
  private final static String CKD_ATTR = "ckd";
  
  private final static String STEP_ATTR = "step";
  
  private PopinDialog ratingDialog;
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(MainPlace.HOME)) {
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      AndroidBackButtonHandler.setDelegate(new Delegate<String>() {
        public void execute(String element) {
          AppClientFactory.IMPL.getPhoneGap().exitApp();
        }
      });
    }
  }

  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
  }
  
  public void openHelpPage() {
    String appLanguage = GwtUtils.getJSVar("appLanguage", null);
    if ("it".equals(appLanguage)) {
      PhonegapUtils.openInAppBrowser("help-it.html");
    } else {
      PhonegapUtils.openInAppBrowser("help.html");
    }
  }

  @Override
  public void goToCkdInput() {
    
  }

  @Override
  public void checkRatingDialog(Delegate<PopinDialog> delegate) {
    
  }
  
  
  
}

package it.mate.gend.client.view;

import it.mate.gend.client.api.GreetingsProxy;
import it.mate.gend.client.constants.AppConstants;
import it.mate.gend.client.factories.AppClientFactory;
import it.mate.gend.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
    void checkRatingDialog(final Delegate<PopinDialog> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Panel outputPanel;
  @UiField Anchor signAnchor;
  @UiField MTextBox messageBox;
  
  private GreetingsProxy proxy;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {

    if (OsDetectionUtils.isTablet()) {
      setTitleHtml(AppConstants.IMPL.tabletAppName());
    } else {
      setTitleHtml(AppConstants.IMPL.phoneAppName());
    }
    
    initHeaderBackButton("Home", new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
      }
    });
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    proxy = AppClientFactory.IMPL.getProxy();
    
    proxy.setSignedInDelegate(new Delegate<Void>() {
      public void execute(Void element) {
        signAnchor.setText("Sign Out");
      }
    });
    
    proxy.setSignedOutDelegate(new Delegate<Void>() {
      public void execute(Void element) {
        signAnchor.setText("Sign In");
      }
    });
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

  @UiHandler ("listBtn")
  public void onListBtn(TouchEndEvent event) {
    if (proxy.isInitialized()) {
      GwtUtils.log("touch");
      outputPanel.clear();
      proxy.list(new Delegate<List<GreetingsProxy.Greetings>>() {
        public void execute(List<GreetingsProxy.Greetings> results) {
          GwtUtils.log("the results >>>>>");
          for (GreetingsProxy.Greetings greetings : results) {
            GwtUtils.log(greetings.getMessage());
            outputPanel.add(new Label(greetings.getMessage()));
          }
        }
      });
    }
  }
  
  @UiHandler ("addBtn")
  public void onAddBtn (TouchEndEvent event) {
    if (proxy.isInitialized()) {
      proxy.addGreeting(messageBox.getText(), new Delegate<Void>() {
        public void execute(Void results) {
          PhonegapUtils.log("message added");
        }
      });
    }
  }
  
  @UiHandler ("signAnchor")
  public void onSignBtn (ClickEvent event) {
    proxy.auth();
  }
  
}

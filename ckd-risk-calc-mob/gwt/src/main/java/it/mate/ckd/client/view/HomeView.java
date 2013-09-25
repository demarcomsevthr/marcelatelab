package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.constants.AppProperties;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.MyAnchor;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;
import it.mate.phgcommons.client.utils.MgwtDialogs;
import it.mate.phgcommons.client.utils.OsDetectionPatch;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CustomTheme.CustomBundle bundle;
  @UiField (provided=true) CustomMainCss style;
  
  @UiField Panel wrapperPanel;
  @UiField HasText paramBtn;
  @UiField Label devInfo;
  @UiField Label versionCreditsLbl;
  
  public HomeView() {
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {

    if (OsDetectionPatch.isTablet()) {
      setTitleHtml(AppConstants.IMPL.tabletAppName());
    } else {
      setTitleHtml(AppConstants.IMPL.phoneAppName());
    }
    
    getHeaderPanel().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    paramBtn.setText(AppConstants.IMPL.HomeView_paramBtn_text());

    if (AppProperties.IMPL.HomeView_devInfo_visible()) {
      devInfo.setVisible(true);
      String info = "Width " + Window.getClientWidth();
      info += " Height " + Window.getClientHeight();
      if (OsDetectionPatch.isTabletLandscape()) {
        info += " isTabletLandscape";
      } else if (OsDetectionPatch.isTabletPortrait()) {
        info += " isTabletPortrait";
      } else {
        info += " isPhone";
      }
      devInfo.setText(info);
      
    } else if (AppProperties.IMPL.HomeView_langInfo_visible()) {
      devInfo.setVisible(true);
      String info = "Language = " + PhonegapUtils.getNavigator().getLanguage();
      devInfo.setText(info);
    }
    
    AppClientFactory.IMPL.adaptWrapperPanelOnTablet(wrapperPanel, "homeWrapperPanel", true, new Delegate<Element>() {
      public void execute(Element wrapperPanelElem) {
        List<Element> spacers = JQueryUtils.selectList(".ckdHomeSpacer");
        for (Element spacer : spacers) {
          int spacerHeight = spacer.getClientHeight() / 2;
          spacer.getStyle().setHeight(spacerHeight, Unit.PX);
        }
      }
    });

    versionCreditsLbl.setText(AppConstants.IMPL.versionCredits());
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  private PopinDialog ratingDialog;
  
//private PopupPanel ratingDialog;
  
  @UiHandler("paramBtn")
  public void onParamBtn(TouchStartEvent event) {
    
    
    
    String rating = PhonegapUtils.getLocalStorageItem("ckd-free-rating");
    if (rating == null) {
      rating = "4";
    }
    GwtUtils.log("rating count = " + rating);
    
    int ratingCount = Integer.parseInt(rating);
    if (ratingCount < 0) {
      getPresenter().goToCkdInput();
    } else if (ratingCount > 0) {
      ratingCount --;
      rating = "" + ratingCount;
      PhonegapUtils.setLocalStorageItem("ckd-free-rating", rating);
      getPresenter().goToCkdInput();
    } else {
      final SimpleContainer dialogPanel = new SimpleContainer();
      dialogPanel.add(new SimplePanel(new MyAnchor("Yes Now", new ClickHandler() {
        public void onClick(ClickEvent event) {
          PhonegapUtils.setLocalStorageItem("ckd-free-rating", "-1");
          GwtUtils.log("clicked yes");
          ratingDialog.hide();
          PhonegapUtils.openInAppBrowser("itms-apps://itunes.apple.com/app/id669006296");
        }
      })));
      dialogPanel.add(new SimplePanel(new MyAnchor("Remind Me Later", new ClickHandler() {
        public void onClick(ClickEvent event) {
          PhonegapUtils.setLocalStorageItem("ckd-free-rating", "4");
          GwtUtils.log("clicked maybe");
          ratingDialog.hide();
          getPresenter().goToCkdInput();
        }
      })));
      dialogPanel.add(new SimplePanel(new MyAnchor("No, Thanks", new ClickHandler() {
        public void onClick(ClickEvent event) {
          PhonegapUtils.setLocalStorageItem("ckd-free-rating", "-1");
          GwtUtils.log("clicked no");
          ratingDialog.hide();
          getPresenter().goToCkdInput();
        }
      })));
      
      // sono costretto a mettere un delay di 1s (!) perche' ho visto che nel simulatore
      // prende il touch sul bottone e lo propaga agli anchor all'interno del dialog !!!!
      GwtUtils.deferredExecution(1000, new Delegate<Void>() {
        public void execute(Void element) {
          
          // ratingDialog = ratingDialog("If you like this App, take a moment to rate it", dialogPanel);
          ratingDialog = MgwtDialogs.popin("If you like this App, take a moment to rate it", dialogPanel);
          
        }
      });
      
    }
    
  }
  
  @SuppressWarnings("unused")
  private PopupPanel ratingDialog (String title, Widget body) {
    PopupPanel dialog = new PopupPanel();

    SimpleContainer dialogWrapper = new SimpleContainer();
    dialogWrapper.addStyleName("phg-PopinDialog-Wrapper");
    Label titleWrapper = new Label(title);
    titleWrapper.addStyleName("phg-PopinDialog-TitleWrapper");
    SimplePanel bodyWrapper = new SimplePanel();
    bodyWrapper.addStyleName("phg-PopinDialog-BodyWrapper");
    bodyWrapper.add(body);
    dialogWrapper.add(titleWrapper);
    dialogWrapper.add(bodyWrapper);
    
    dialog.add(dialogWrapper);
    dialog.center();
    return dialog;
  }
  

}

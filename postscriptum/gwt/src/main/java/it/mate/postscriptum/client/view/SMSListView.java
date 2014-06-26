package it.mate.postscriptum.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.postscriptum.client.ui.SignPanel;
import it.mate.postscriptum.client.view.SMSListView.Presenter;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickSms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class SMSListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_SMSS = "smss";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void findScheduledSMSsByUser(RemoteUser remoteUser);
    public void deleteSMSs(final RemoteUser remoteUser, List<StickSms> smss);
  }

  public interface ViewUiBinder extends UiBinder<Widget, SMSListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField SignPanel signPanel;
  @UiField ScrollPanel resultsPanel;
  @UiField TouchButton deleteBtn;
  
  private boolean scrollInProgress = false;
  
  List<StickSms> checkedSMSs = new ArrayList<StickSms>();
  
  private RemoteUser remoteUser;
  
  public SMSListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initHeaderBackButton(SafeHtmlUtils.fromTrustedString("<img src='main/images/home-back.png'/>"), new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToHome();
      }
    });
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    getScrollPanel().setScrollingEnabledX(false);
    getScrollPanel().setScrollingEnabledY(false);
    resultsPanel.setScrollingEnabledY(true);
    
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        int resultsHeight = Window.getClientHeight() - resultsPanel.getAbsoluteTop();
        resultsPanel.getElement().getStyle().setHeight(resultsHeight, Unit.PX);
      }
    });
    
    resultsPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {
      public void onScrollMove(ScrollMoveEvent event) {
        scrollInProgress = true;
      }
    });
    resultsPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {
      public void onScrollEnd(ScrollEndEvent event) {
        scrollInProgress = false;
      }
    });
    
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    signPanel.setRemoteUserDelegate(presenter, new Delegate<RemoteUser>() {
      public void execute(RemoteUser remoteUser) {
        SMSListView.this.remoteUser = remoteUser;
        if (remoteUser == null) {
          showSMSList(null);
        } else {
          getPresenter().findScheduledSMSsByUser(remoteUser);
        }
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_SMSS.equals(tag)) {
      if (model != null && model instanceof List) {
        @SuppressWarnings("unchecked")
        List<StickSms> smss = (List<StickSms>)model;
        showSMSList(smss);
      }
    }
  }
  
  private void showSMSList(List<StickSms> smss) {
    resultsPanel.clear();
    if (smss == null || smss.size() == 0) {
      Label noSMSsLbl = new Label("You have no scheduled sms.");
      noSMSsLbl.addStyleName("ui-nomails");
      resultsPanel.add(noSMSsLbl);
      return;
    }
    Collections.sort(smss, new Comparator<StickSms>() {
      public int compare(StickSms s1, StickSms s2) {
        return s1.getScheduled().compareTo(s2.getScheduled());
      }
    });
    deleteBtn.setVisible(true);
    SimpleContainer list = new SimpleContainer();
    for (final StickSms sms : smss) {
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      PhCheckBox check = new PhCheckBox();
      check.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (!scrollInProgress) {
            if (event.getValue()) {
              checkedSMSs.add(sms);
            } else {
              checkedSMSs.remove(sms);
            }
          }
        }
      });
      row.add(check);
      String shortBody = sms.getBody() != null ? sms.getBody() : "";
      shortBody = shortBody.length() > 20 ? (shortBody.substring(0, 20) + "...") : shortBody;
      TouchHTML smsHtml = new TouchHTML("<p class='ui-row-subject'><b>" + sms.getReceiverNumber() + "</b> " + shortBody + "</p><p class='ui-row-scheduled'>" + GwtUtils.dateToString(sms.getScheduled(), "dd/MM/yyyy HH:mm") + "</p>");
      row.add(smsHtml);
      smsHtml.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (!scrollInProgress) {

          }
        }
      });
      
    }
    resultsPanel.add(list);
    TouchUtils.applyFocusPatch();
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        resultsPanel.setHeight("" + (Window.getClientHeight() - resultsPanel.getAbsoluteTop()) + "px");
      }
    });
  }
  
  @UiHandler ("deleteBtn")
  public void onDeleteBtn(TouchEndEvent event) {
    if (checkedSMSs == null || checkedSMSs.size() == 0)
      return;
    for (StickSms sms : checkedSMSs) {
      PhgUtils.log("deleting " + sms);
    }
    PhgDialogUtils.showMessageDialog("Are you sure you want to delete the selected sms?", "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
      public void execute(Integer selectedButton) {
        if (selectedButton == 1) {
          getPresenter().deleteSMSs(remoteUser, checkedSMSs);
        }
      }
    });
  }
  
}

package it.mate.stickmail.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.stickmail.client.ui.SignPanel;
import it.mate.stickmail.client.view.MailListView.Presenter;
import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class MailListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_MAILS = "mails";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void findMailsByUser(RemoteUser remoteUser);
  }

  public interface ViewUiBinder extends UiBinder<Widget, MailListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField SignPanel signPanel;
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  public MailListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initHeaderBackButton("Back", new Delegate<TapEvent>() {
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
        if (remoteUser == null) {
          showMailList(null);
        } else {
          getPresenter().findMailsByUser(remoteUser);
        }
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_MAILS.equals(tag)) {
      if (model != null && model instanceof List) {
        List<StickMail> mails = (List<StickMail>)model;
        showMailList(mails);
      }
    }
  }
  
  private void showMailList(List<StickMail> mails) {
    resultsPanel.clear();
    if (mails == null || mails.size() == 0)
      return;
    SimpleContainer list = new SimpleContainer();
    boolean odd = false;
    
    for (final StickMail mail : mails) {
      
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      
      PhCheckBox check = new PhCheckBox();
      row.add(check);
      
      TouchHTML mailHtml = new TouchHTML("<p class='ui-row-subject'>" + mail.getSubject() + "</p><p class='ui-row-scheduled'>" + GwtUtils.dateToString(mail.getScheduled(), "dd/MM/yyyy HH:mm") + "</p>");
      row.add(mailHtml);
      
      mailHtml.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (!scrollInProgress) {
            PhonegapUtils.log("selected " + mail);
          }
        }
      });
      
    }
    
    resultsPanel.add(list);
//  TouchUtils.applyFocusPatch();
    TouchUtils.applyFocusPatchDeferred();
    
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        resultsPanel.setHeight("" + (Window.getClientHeight() - resultsPanel.getAbsoluteTop()) + "px");
      }
    });
    
  }

  
}

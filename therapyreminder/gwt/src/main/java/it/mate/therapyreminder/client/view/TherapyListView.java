package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.view.TherapyListView.Presenter;
import it.mate.therapyreminder.shared.model.Prescrizione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapEvent;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class TherapyListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_PRESCRIZIONI = "prescrizioni";

  public interface Presenter extends BasePresenter {
    public void goToTherapyEditView(Prescrizione prescrizione);
    public void deletePrescrizioni(List<Prescrizione> prescrizioni);
  }

  public interface ViewUiBinder extends UiBinder<Widget, TherapyListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  List<Prescrizione> selectedPrescrizioni = new ArrayList<Prescrizione>();
  
  public TherapyListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
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
  }

  @Override
  public void setModel(Object model, String tag) {
    if (TAG_PRESCRIZIONI.equals(tag)) {
      @SuppressWarnings("unchecked")
      List<Prescrizione> prescrizioni = (List<Prescrizione>)model;
      showListaPrescrizioni(prescrizioni);
    }
    
  }
  
  @UiHandler ("newBtn")
  public void onNewBtn (TouchEndEvent event) {
    getPresenter().goToTherapyEditView(null);
  }
  
  private void showListaPrescrizioni(List<Prescrizione> prescrizioni) {
    resultsPanel.clear();
    if (prescrizioni == null || prescrizioni.size() == 0) {
      //TODO
      Label noResultsLbl = new Label(AppMessages.IMPL.TherapyListView_showListaPrescrizioni_noRows_text());
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(prescrizioni, new Comparator<Prescrizione>() {
      public int compare(Prescrizione p1, Prescrizione p2) {
        return p1.getDataInizio().compareTo(p2.getDataInizio());
      }
    });
    SimpleContainer list = new SimpleContainer();
    for (final Prescrizione prescrizione : prescrizioni) {
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      PhCheckBox check = new PhCheckBox();
      check.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (!scrollInProgress) {
            if (event.getValue()) {
              selectedPrescrizioni.add(prescrizione);
            } else {
              selectedPrescrizioni.remove(prescrizione);
            }
          }
        }
      });
      row.add(check);
//    TouchHTML rowHtml = new TouchHTML("<p class='ui-row-subject'>" + prescrizione.getNome() + "</p><p class='ui-row-scheduled'>" + GwtUtils.dateToString(prescrizione.getDataInizio(), "dd/MM/yyyy") + "</p>");
      TouchHTML rowHtml = new TouchHTML("<p class='ui-row-subject'>" + prescrizione.getNome() + "</p><p class='ui-row-scheduled'>" + PhgUtils.dateToString(prescrizione.getDataInizio()) + "</p>");
      row.add(rowHtml);
      rowHtml.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (!scrollInProgress) {
            getPresenter().goToTherapyEditView(prescrizione);
          }
        }
      });
      
      rowHtml.addLongTapHandler(new LongTapHandler() {
        public void onLongTap(LongTapEvent event) {
          PhgUtils.log("long tapped " + prescrizione);
//        getPresenter().goToTherapyEditView(prescrizione);
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
    if (selectedPrescrizioni == null || selectedPrescrizioni.size() == 0)
      return;
    PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyListView_onDeleteBtn_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
      public void execute(Integer selectedButton) {
        if (selectedButton == 1) {
          getPresenter().deletePrescrizioni(selectedPrescrizioni);
        }
      }
    });
  }
  
}

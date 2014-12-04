package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.view.PatientListView.Presenter;
import it.mate.therapyreminder.shared.model.Paziente;
import it.mate.therapyreminder.shared.model.impl.PazienteTx;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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

public class PatientListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_PAZIENTI = "pazienti";

  public interface Presenter extends BasePresenter {
    public void editPatient(Paziente paziente);
    public void selectPatient(Paziente paziente);
  }

  public interface ViewUiBinder extends UiBinder<Widget, PatientListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  public PatientListView() {
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
  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    if (TAG_PAZIENTI.equals(tag)) {
      List<Paziente> pazienti = (List<Paziente>)model;
      showListaPazienti(pazienti);
    }
  }
  
  @UiHandler ("newBtn")
  public void onNewBtn (TouchEndEvent event) {
    getPresenter().editPatient(new PazienteTx());
  }
  
  private void showListaPazienti(List<Paziente> pazienti) {
    resultsPanel.clear();
    if (pazienti == null || pazienti.size() == 0) {
      Label noResultsLbl = new Label(AppMessages.IMPL.PatientListView_showListaPazienti_msg1());
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(pazienti, new Comparator<Paziente>() {
      public int compare(Paziente c1, Paziente c2) {
        return c1.getNome().compareTo(c2.getNome());
      }
    });
    SimpleContainer list = new SimpleContainer();
    for (final Paziente paziente : pazienti) {
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      TouchHTML rowHtml = new TouchHTML("<p class='ui-row-subject'>" + paziente.getNome() + "</p>");
      row.add(rowHtml);
      rowHtml.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (!scrollInProgress) {
            getPresenter().selectPatient(paziente);
          }
        }
      });
      rowHtml.addLongTapHandler(new LongTapHandler() {
        public void onLongTap(LongTapEvent event) {
          PhgUtils.log("long tapped " + paziente);
          getPresenter().editPatient(paziente);
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

}

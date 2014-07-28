package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.view.ReminderListView.Presenter;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.UdM;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class ReminderListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_SOMMINISTRAZIONI_SCHEDULATE = "somministrazioniSchedulate";
  public static final String TAG_SOMMINISTRAZIONI_ANNULLATE = "somministrazioniAnnullate";

  public interface Presenter extends BasePresenter {
    public void goToReminderEditView(Somministrazione somministrazione);
    public void getUdmDescription(Double qta, final String udmCode, final Delegate<UdM> delegate);
    public void findSomministrazioniAnnullate(Delegate<List<Somministrazione>> resultsDelegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ReminderListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  DateTimeFormat dayNameFmt = DateTimeFormat.getFormat("EEEE");
  
  DateTimeFormat dateFmt = "it".equals(PhgUtils.getAppLocalLanguage()) ? DateTimeFormat.getFormat("dd MMM") : DateTimeFormat.getFormat("MMM dd");
  
  @UiField Panel wrapperPanel;
  @UiField ScrollPanel resultsPanel;
  @UiField TouchButton showCanceledBtn;
  
  private boolean scrollInProgress = false;
  
  private List<Somministrazione> somministrazioniAnnullate;
  private List<Somministrazione> somministrazioniSchedulate;
  
  public ReminderListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    disableMainScrolling();
    
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

    
//  GwtUtils.setDebugExceptionHandler();
    
    resultsContainer = new SimpleContainer();
    resultsPanel.add(resultsContainer);
    
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }

  @Override
  public void setModel(Object model, String tag) {
    if (TAG_SOMMINISTRAZIONI_SCHEDULATE.equals(tag)) {
      List<Somministrazione> somministrazioni = (List<Somministrazione>)model;
      this.somministrazioniSchedulate = somministrazioni;
      showListaSomministrazioni(somministrazioni);
    }
    if (TAG_SOMMINISTRAZIONI_ANNULLATE.equals(tag)) {
      List<Somministrazione> somministrazioni = (List<Somministrazione>)model;
      this.somministrazioniAnnullate = somministrazioni;
      if (somministrazioniAnnullate != null && somministrazioniAnnullate.size() > 0) {
        showCanceledBtn.setVisible(true);
        showSomministrazioniAnnullate();
      }
    }
  }
  
  private boolean isShowingSomministrazioniAnnullate() {
    Boolean flag = (Boolean)GwtUtils.getClientAttribute("ReminderListView.showingSomministrazioniAnnullate");
    if (flag == null) {
      flag = new Boolean(false);
      setShowingSomministrazioniAnnullate(flag);
    }
    return flag;
  }
  
  private void setShowingSomministrazioniAnnullate(boolean flag) {
    GwtUtils.setClientAttribute("ReminderListView.showingSomministrazioniAnnullate", new Boolean(flag));
  }
  
  private SimpleContainer resultsContainer = new SimpleContainer();
  
  private void showListaSomministrazioni(List<Somministrazione> somministrazioni) {
//  resultsPanel.clear();
    if (somministrazioni == null || somministrazioni.size() == 0) {
      Label noResultsLbl = new Label(AppMessages.IMPL.ReminderListView_showListaSomministrazioni_msg1());
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(somministrazioni, new Comparator<Somministrazione>() {
      public int compare(Somministrazione p1, Somministrazione p2) {
        return p1.getData().compareTo(p2.getData());
      }
    });
//  final SimpleContainer list = new SimpleContainer();
    final ObjectWrapper<String> prevDate = new ObjectWrapper<String>();
    final ObjectWrapper<Integer> actualHeight = new ObjectWrapper<Integer>(0);
    final int lastRowId = somministrazioni.get(somministrazioni.size() - 1).getId();
    for (final Somministrazione somministrazione : somministrazioni) {
      getPresenter().getUdmDescription(somministrazione.getQuantita(), somministrazione.getPrescrizione().getCodUdM(), new Delegate<UdM>() {
        public void execute(UdM udm) {
          actualHeight.set(actualHeight.get() + showRow(somministrazione, resultsContainer, prevDate, udm));
          if (somministrazione.getId() == lastRowId) {
            adjustInnerScrollPanelHeight(resultsPanel);
          }
        }
      });
    }
//  resultsPanel.add(list);
    TouchUtils.applyFocusPatch();
  }
  
  private int showRow(final Somministrazione somministrazione, SimpleContainer list, ObjectWrapper<String> lastTimestamp, UdM udm) {
    String rowTimestamp = GwtUtils.dateToString(somministrazione.getData(), "yyyyMMdd");
    HorizontalPanel row = new HorizontalPanel();
    row.addStyleName("ui-row-reminder");
    list.add(row);
    String html = "";
    if (lastTimestamp.get() == null || !lastTimestamp.get().equals(rowTimestamp)) {
      html += "<p class='ui-row-date'>";
      html += "<span class='ui-row-date-name'>";
      Date refDate = new Date();
      if (rowTimestamp.equals(GwtUtils.dateToString(refDate, "yyyyMMdd"))) {
        html +=  AppMessages.IMPL.ReminderListView_today_text() + ", ";
      } else {
        CalendarUtil.addDaysToDate(refDate, +1);
        if (rowTimestamp.equals(GwtUtils.dateToString(refDate, "yyyyMMdd"))) {
          html +=  AppMessages.IMPL.ReminderListView_tomorrow_text() + ", ";
        }
      }
      html += dayNameFmt.format(somministrazione.getData());
      html += "</span>";
      html += "<span class='ui-row-date-date'>";
      html += dateFmt.format(somministrazione.getData());
      html += "</span>";
      html += "</p>";
    }
    html += "<p class='ui-row-reminder-subject'>" + somministrazione.getPrescrizione().getNome() + "</p>";
    
    String cssAnnullata = "";
    if (somministrazione.isAnnullata()) {
      cssAnnullata = "ui-row-reminder-note-canceled";
    }
    
    html += "<p class='ui-row-reminder-note "+cssAnnullata+"'>" + somministrazione.getQuantita().intValue() + " " + udm.getDescrizione().toLowerCase();
    html += " "+ AppMessages.IMPL.ReminderListView_showRow_at_text() +" " + somministrazione.getOrario() + "</p>";
    TouchHTML rowHtml = new TouchHTML(html);
    row.add(rowHtml);
    rowHtml.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        if (!scrollInProgress) {
          getPresenter().goToReminderEditView(somministrazione);
        }
      }
    });
    lastTimestamp.set(rowTimestamp);
    return row.getElement().getClientHeight();
  }

  @UiHandler ("showCanceledBtn")
  public void onShowCanceledBtn(TouchEndEvent event) {
    if (somministrazioniAnnullate != null && somministrazioniAnnullate.size() > 0) {
      resultsContainer.clear();
      setShowingSomministrazioniAnnullate(!isShowingSomministrazioniAnnullate());
      showSomministrazioniAnnullate();
      showListaSomministrazioni(somministrazioniSchedulate);
    }
  }
  
  private void showSomministrazioniAnnullate() {
    if (isShowingSomministrazioniAnnullate()) {
      showCanceledBtn.setText(AppMessages.IMPL.ReminderListView_showCanceledBtn_text_hide());
      showListaSomministrazioni(somministrazioniAnnullate);
    } else {
      showCanceledBtn.setText(AppMessages.IMPL.ReminderListView_showCanceledBtn_text_show());
    }
  }
  
}

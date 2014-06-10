package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.view.ReminderListView.Presenter;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.UdM;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class ReminderListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_SOMMINISTRAZIONI = "somministrazioni";

  public interface Presenter extends BasePresenter {
    public void goToReminderEditView(Somministrazione somministrazione);
    public void getUdmDescription(Double qta, final String udmCode, final Delegate<UdM> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ReminderListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  DateTimeFormat dayNameFmt = DateTimeFormat.getFormat("EEEE");
  
  DateTimeFormat dateFmt = DateTimeFormat.getFormat("MMM dd");
  
  @UiField Panel wrapperPanel;
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  public ReminderListView() {
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
    if (TAG_SOMMINISTRAZIONI.equals(tag)) {
      @SuppressWarnings("unchecked")
      List<Somministrazione> somministrazioni = (List<Somministrazione>)model;
      showListaSomministrazioni(somministrazioni);
    }
    
  }
  
  private void showListaSomministrazioni(List<Somministrazione> somministrazioni) {
    resultsPanel.clear();
    if (somministrazioni == null || somministrazioni.size() == 0) {
      Label noResultsLbl = new Label("You have no therapies.");
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(somministrazioni, new Comparator<Somministrazione>() {
      public int compare(Somministrazione p1, Somministrazione p2) {
        return p1.getData().compareTo(p2.getData());
      }
    });
    final SimpleContainer list = new SimpleContainer();
    final ObjectWrapper<String> prevDate = new ObjectWrapper<String>();
    for (final Somministrazione somministrazione : somministrazioni) {
      getPresenter().getUdmDescription(somministrazione.getQuantita(), somministrazione.getPrescrizione().getCodUdM(), new Delegate<UdM>() {
        public void execute(UdM udm) {
          showRow(somministrazione, list, prevDate, udm);
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
  
  private void showRow(final Somministrazione somministrazione, SimpleContainer list, ObjectWrapper<String> prevDate, UdM udm) {
    String curDate = GwtUtils.dateToString(somministrazione.getData(), "yyyyMMdd");
    HorizontalPanel row = new HorizontalPanel();
    row.addStyleName("ui-row-reminder");
    list.add(row);
    String html = "";
    if (prevDate.get() == null || !prevDate.equals(curDate)) {
      html += "<p class='ui-row-date'>";
      html += "<span class='ui-row-date-name'>";
      html += dayNameFmt.format(somministrazione.getData());
      html += "</span>";
      html += "<span class='ui-row-date-date'>";
      html += dateFmt.format(somministrazione.getData());
      html += "</span>";
      html += "</p>";
    }
    html += "<p class='ui-row-reminder-subject'>" + somministrazione.getPrescrizione().getNome() + "</p>";
    html += "<p class='ui-row-reminder-note'>" + somministrazione.getQuantita().intValue() + " " + udm.getDescrizione().toLowerCase();
    html += " at " + somministrazione.getOrario() + "</p>";
    TouchHTML rowHtml = new TouchHTML(html);
    row.add(rowHtml);
    rowHtml.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        if (!scrollInProgress) {
          getPresenter().goToReminderEditView(somministrazione);
        }
      }
    });
    prevDate.set(curDate);
  }
  
}

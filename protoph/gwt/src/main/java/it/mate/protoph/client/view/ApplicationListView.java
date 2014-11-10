package it.mate.protoph.client.view;

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
import it.mate.protoph.client.view.ApplicationListView.Presenter;
import it.mate.protoph.shared.model.Applicazione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
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

public class ApplicationListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_APPLICAZIONI = "applicazioni";

  public interface Presenter extends BasePresenter {
    public void goToHome();
    public void goToApplicationEditView(Applicazione applicazione);
    public void deleteApplicazioni(List<Applicazione> applicazioni);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ApplicationListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  private List<Applicazione> selectedItems = new ArrayList<Applicazione>();
  
  public ApplicationListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    disableMainScrolling();
    PhgUtils.prepareInnerScrollPanel(resultsPanel, new Delegate<Boolean>() {
      public void execute(Boolean value) {
        scrollInProgress = value;
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
    if (TAG_APPLICAZIONI.equals(tag)) {
      List<Applicazione> results = (List<Applicazione>)model;
      showResults(results);
    }
  }
  
  private void showResults(List<Applicazione> results) {
    resultsPanel.clear();
    if (results == null || results.size() == 0) {
      Label noResultsLbl = new Label("No applications found");
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(results, new Comparator<Applicazione>() {
      public int compare(Applicazione r1, Applicazione r2) {
        return r1.getNome().compareTo(r2.getNome());
      }
    });
    SimpleContainer list = new SimpleContainer();
    for (final Applicazione item : results) {
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      PhCheckBox check = new PhCheckBox();
      check.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (!scrollInProgress) {
            if (event.getValue()) {
              selectedItems.add(item);
            } else {
              selectedItems.remove(item);
            }
          }
        }
      });
      row.add(check);
      TouchHTML rowHtml = new TouchHTML("<p class='ui-row-subject'>" + item.getNome() + "</p>");
      row.add(rowHtml);
      rowHtml.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (!scrollInProgress) {
            getPresenter().goToApplicationEditView(item);
          }
        }
      });
      rowHtml.addLongTapHandler(new LongTapHandler() {
        public void onLongTap(LongTapEvent event) {
          PhgUtils.log("long tapped " + item);
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
  
  @UiHandler ("newBtn")
  public void onNewBtn (TouchEndEvent event) {
    getPresenter().goToApplicationEditView(null);
  }
  
  @UiHandler ("deleteBtn")
  public void onDeleteBtn(TouchEndEvent event) {
    if (selectedItems == null || selectedItems.size() == 0)
      return;
    PhgDialogUtils.showMessageDialog("Confirm delete of selected items?", "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
      public void execute(Integer selectedButton) {
        if (selectedButton == 1) {
          getPresenter().deleteApplicazioni(selectedItems);
        }
      }
    });
  }
  
}

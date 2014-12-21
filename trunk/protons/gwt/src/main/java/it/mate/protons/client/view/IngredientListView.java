package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protons.client.view.IngredientListView.Presenter;
import it.mate.protons.shared.model.Applicazione;
import it.mate.protons.shared.model.PrincipioAttivo;

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

public class IngredientListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_INGREDIENTS = "ingredients";

  public interface Presenter extends BasePresenter {
    public Applicazione getEditingApplicazione();
    public void goToApplicationEditView(Applicazione applicazione);
  }

  public interface ViewUiBinder extends UiBinder<Widget, IngredientListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  private List<PrincipioAttivo> selectedItems = new ArrayList<PrincipioAttivo>();
  
  public IngredientListView() {
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
    if (TAG_INGREDIENTS.equals(tag)) {
      showResults((List<PrincipioAttivo>)model);
    }
  }
  
  private void showResults(List<PrincipioAttivo> results) {
    resultsPanel.clear();
    if (results == null || results.size() == 0) {
      Label noResultsLbl = new Label("No ingredients found");
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(results, new Comparator<PrincipioAttivo>() {
      public int compare(PrincipioAttivo r1, PrincipioAttivo r2) {
        return r1.getId().compareTo(r2.getId());
      }
    });
    SimpleContainer list = new SimpleContainer();
    for (final PrincipioAttivo item : results) {
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
  
  @UiHandler ("doneBtn")
  public void onDoneBtn (TouchEndEvent event) {
    Applicazione editingApplicazione = getPresenter().getEditingApplicazione();
    editingApplicazione.getPrincipiAttivi().addAll(selectedItems);
    getPresenter().goToApplicationEditView(editingApplicazione);
  }
  
}

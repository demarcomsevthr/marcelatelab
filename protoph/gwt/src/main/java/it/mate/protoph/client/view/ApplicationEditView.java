package it.mate.protoph.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protoph.client.view.ApplicationEditView.Presenter;
import it.mate.protoph.shared.model.Applicazione;
import it.mate.protoph.shared.model.PrincipioAttivo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapEvent;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public class ApplicationEditView extends BaseMgwtView <Presenter> {
  
  public final static String TAG_APPLICAZIONE = "applicazione";

  public interface Presenter extends BasePresenter {
    public void goToHome();
    public void saveApplicazione(Applicazione applicazione);
    public void setEditingApplicazione(Applicazione applicazione);
    public void goToIngredientListView();
    public void doApply(Applicazione applicazione);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ApplicationEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField PhTextBox nomeBox;
  @UiField ScrollPanel ingredientsScrollPanel;
  
  private boolean scrollInProgress = false;
  
  private List<PrincipioAttivo> selectedIngredients = new ArrayList<PrincipioAttivo>();
  
  private Applicazione editingApplicazione;
  
  public ApplicationEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }

  @Override
  public void setModel(Object model, String tag) {
    
    if (TAG_APPLICAZIONE.equals(tag)) {
      editingApplicazione = (Applicazione)model;
      nomeBox.setText(editingApplicazione.getNome());
      showIngredients();
      getPresenter().setEditingApplicazione(null);
    }
    
  }
  
  @UiHandler ("saveBtn")
  public void onSaveBtn (TouchEndEvent event) {
    saveApplicazione(flushApplicazione());
  }
  
  @UiHandler ("applyBtn")
  public void onApplyBtn (TouchEndEvent event) {
    getPresenter().doApply(editingApplicazione);
  }
  
  @UiHandler ("addIngredientBtn")
  public void onAddIngredientBtn (TouchEndEvent event) {
    Applicazione applicazione = flushApplicazione();
    if (applicazione != null) {
      getPresenter().setEditingApplicazione(applicazione);
      getPresenter().goToIngredientListView();
    }
  }
  
  @UiHandler ("removeIngredientBtn")
  public void onRemoveIngredientBtn (TouchEndEvent event) {
    if (selectedIngredients.size() > 0) {
      for (PrincipioAttivo selectedPrincipio : selectedIngredients) {
        for (Iterator<PrincipioAttivo> it = editingApplicazione.getPrincipiAttivi().iterator(); it.hasNext();) {
          PrincipioAttivo item = it.next();
          if (item.getId().equals(selectedPrincipio.getId())) {
            it.remove();
          }
        }
      }
      showIngredients();
    }
  }
  
  private Applicazione flushApplicazione() {
    editingApplicazione.setNome(nomeBox.getValue());
    if (editingApplicazione.getNome().trim().length() == 0) {
      PhgDialogUtils.showMessageDialog("Name cannot be empty", "Alert", PhgDialogUtils.BUTTONS_OK);
      return null;
    }
    return editingApplicazione;
  }
  
  private void saveApplicazione(Applicazione applicazione) {
    if (applicazione == null) {
      return;
    }
    getPresenter().saveApplicazione(applicazione);
  }
  
  private void showIngredients() {
    ingredientsScrollPanel.clear();
    Collections.sort(editingApplicazione.getPrincipiAttivi(), new Comparator<PrincipioAttivo>() {
      public int compare(PrincipioAttivo r1, PrincipioAttivo r2) {
        return r1.getId().compareTo(r2.getId());
      }
    });
    SimpleContainer list = new SimpleContainer();
    for (final PrincipioAttivo item : editingApplicazione.getPrincipiAttivi()) {
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      PhCheckBox check = new PhCheckBox();
      check.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (!scrollInProgress) {
            if (event.getValue()) {
              selectedIngredients.add(item);
            } else {
              selectedIngredients.remove(item);
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
    ingredientsScrollPanel.add(list);
    TouchUtils.applyFocusPatch();
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        ingredientsScrollPanel.setHeight("" + (Window.getClientHeight() - ingredientsScrollPanel.getAbsoluteTop()) + "px");
      }
    });
  }
  
}

package it.mate.copymob.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.ui.OnsTextBox;
import it.mate.copymob.client.view.ApplicationEditView.Presenter;
import it.mate.copymob.shared.model.Applicazione;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationEditView extends AbstractBaseView<Presenter> {

  private Applicazione applicazione;
  
  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void saveApplicazione(Applicazione applicazione);
    public void goToApplicationEditView(Applicazione applicazione);
    public void goToIngredientListView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, ApplicationEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label lblTitle;
  @UiField OnsTextBox boxName;
  
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
  public void setModel(Object model, String tag) {
    if (model instanceof Applicazione) {
      applicazione = (Applicazione)model;
      lblTitle.setText("Edit: " + applicazione.getNome());
      boxName.setValue(applicazione.getNome());
    }
  }
  
  @UiHandler("btnSave")
  public void onBtnSave(TapEvent event) {
    applicazione.setNome(boxName.getValue());
    getPresenter().saveApplicazione(applicazione);
    getPresenter().goToApplicationEditView(applicazione);
  }
  
  @UiHandler("btnIngredients")
  public void onBtnIngredients(TapEvent event) {
    getPresenter().goToIngredientListView();
  }
  
}

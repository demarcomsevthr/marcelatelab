package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.view.PatientEditView.Presenter;
import it.mate.therapyreminder.shared.model.Paziente;
import it.mate.therapyreminder.shared.model.impl.PazienteTx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class PatientEditView extends BaseMgwtView <Presenter> implements HasClosingViewHandler  {
  
  public static final String TAG_PATIENT = "patient";

  public interface Presenter extends BasePresenter {
    public void savePaziente(Paziente paziente, Delegate<Paziente> successDelegate);
    public void deletePaziente(Paziente paziente);
  }

  public interface ViewUiBinder extends UiBinder<Widget, PatientEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField PhTextBox nameBox;
  @UiField TouchButton deleteBtn;
  
  Paziente originalModel;
  
  public PatientEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    wrapperPanel.getElement().getStyle().clearHeight();
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_PATIENT.equals(tag)) {
      originalModel = (Paziente)model;
      
      if (originalModel.getId() == null) {
        HorizontalPanel bar = (HorizontalPanel)deleteBtn.getParent();
        bar.remove(deleteBtn);
      }
      
      nameBox.setValue(originalModel.getNome());
      
    }
  }

  @UiHandler ("saveBtn")
  public void onSaveBtn (TouchEndEvent event) {
    Paziente modifiedModel = flushModel(true);
    if (modifiedModel != null && !originalModel.equals(modifiedModel)) {
      getPresenter().savePaziente(modifiedModel, null);
    }
  }
  
  @UiHandler ("deleteBtn")
  public void onDeleteBtn (TouchEndEvent event) {
    if (originalModel.getId() != null) {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.PatientEditView_onDeleteBtn_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer selectedButton) {
          if (selectedButton == 1) {
            getPresenter().deletePaziente(originalModel);
          }
        }
      });
    }
  }
  
  @Override
  public void onClosingView(final ClosingHandler handler) {
    final Paziente modifiedModel = flushModel(false);
    if (modifiedModel == null || originalModel.equals(modifiedModel) || originalModel.getId() == null) {
      handler.doClose();
    } else {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.PatientEditView_onClosingView_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer selectedButton) {
          if (selectedButton == 1) {
            getPresenter().savePaziente(modifiedModel, new Delegate<Paziente>() {
              public void execute(Paziente savedModel) {
                handler.doClose();
              }
            });
          } else {
            handler.doClose();
          }
        }
      });
    }
  }
  
  private Paziente flushModel(boolean validate) {
    Paziente modifiedModel = new PazienteTx(originalModel);
    modifiedModel.setNome(nameBox.getValue());
    if (modifiedModel.getNome().trim().equals("")) {
      if (validate) {
        PhgDialogUtils.showMessageDialog(AppMessages.IMPL.PatientEditView_flushModel_msg1());
        return null;
      }
    }
    return modifiedModel;
  }

}

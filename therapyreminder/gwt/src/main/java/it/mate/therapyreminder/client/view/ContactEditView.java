package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TouchButton;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.utils.KeyboardUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.view.ContactEditView.Presenter;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.impl.ContattoTx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class ContactEditView extends BaseMgwtView <Presenter> implements HasClosingViewHandler  {
  
  public static final String TAG_CONTACT = "contact";

  public interface Presenter extends BasePresenter {
    public void saveContatto(Contatto contatto, Delegate<Contatto> successDelegate);
    public void deleteContatto(Contatto contatto);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ContactEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField HTML header2;
  @UiField PhTextBox nameBox;
  @UiField PhTextBox emailBox;
  @UiField PhTextBox phoneBox;
  @UiField Panel doctorOnlyPanel;
  @UiField PhTextBox addressBox;
  @UiField PhTextBox hoursBox;
  @UiField TouchButton deleteBtn;
  
  Contatto originalModel;
  
  public ContactEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    wrapperPanel.getElement().getStyle().clearHeight();

    //TODO 29/07/2014 - IN TEST
    KeyboardUtils.enableDoneButtonSurrogate();
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_CONTACT.equals(tag)) {
      originalModel = (Contatto)model;
      
      if (originalModel.getId() == null) {
        HorizontalPanel bar = (HorizontalPanel)deleteBtn.getParent();
        bar.remove(deleteBtn);
      }
      
      nameBox.setValue(originalModel.getNome());
      emailBox.setValue(originalModel.getEmail());
      phoneBox.setValue(originalModel.getTelefono());
      
      if (originalModel.getTipo().equals(Contatto.TIPO_MEDICO)) {
        doctorOnlyPanel.setVisible(true);
        addressBox.setValue(originalModel.getIndirizzo());
        hoursBox.setValue(originalModel.getOrari());
      }
      
      if (originalModel.getTipo().equals(Contatto.TIPO_TUTOR)) {
        header2.setHTML(AppMessages.IMPL.ContactEditView_header2_TUTOR_HTML());
      }
      
    }
  }

  @UiHandler ("saveBtn")
  public void onSaveBtn (TouchEndEvent event) {
    Contatto modifiedModel = flushModel(true);
    if (modifiedModel != null && !originalModel.equals(modifiedModel)) {
      getPresenter().saveContatto(modifiedModel, null);
    }
  }
  
  @UiHandler ("deleteBtn")
  public void onDeleteBtn (TouchEndEvent event) {
    if (originalModel.getId() != null) {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.ContactEditView_onDeleteBtn_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer selectedButton) {
          if (selectedButton == 1) {
            getPresenter().deleteContatto(originalModel);
          }
        }
      });
    }
  }
  
  @Override
  public void onClosingView(final ClosingHandler handler) {
    final Contatto modifiedModel = flushModel(false);
    if (modifiedModel == null || originalModel.equals(modifiedModel) || originalModel.getId() == null) {
      handler.doClose();
    } else {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.ContactEditView_onClosingView_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer selectedButton) {
          if (selectedButton == 1) {
            getPresenter().saveContatto(modifiedModel, new Delegate<Contatto>() {
              public void execute(Contatto savedModel) {
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
  
  private Contatto flushModel(boolean validate) {
    Contatto modifiedModel = new ContattoTx(originalModel);
    modifiedModel.setNome(nameBox.getValue());
    modifiedModel.setEmail(emailBox.getValue());
    modifiedModel.setTelefono(phoneBox.getValue());
    modifiedModel.setIndirizzo(addressBox.getValue());
    modifiedModel.setOrari(hoursBox.getValue());
    if (modifiedModel.getNome().trim().equals("")) {
      if (validate) {
        PhgDialogUtils.showMessageDialog(AppMessages.IMPL.ContactEditView_flushModel_msg1());
        return null;
      }
    }
    if (modifiedModel.getEmail().trim().equals("") && modifiedModel.getTelefono().trim().equals("")) {
      if (validate) {
        PhgDialogUtils.showMessageDialog(AppMessages.IMPL.ContactEditView_flushModel_msg2());
        return null;
      }
    }
    return modifiedModel;
  }

}

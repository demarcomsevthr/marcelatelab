package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.NumberUtils;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.Position;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.logic.MainController;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.ReminderEditView.Presenter;
import it.mate.therapyreminder.shared.model.Somministrazione;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class ReminderEditView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_SOMMINISTRAZIONE = "somministrazione";
  public static final String TAG_FARMACO_DA_RIORDINARE = "farmacoDaRiordinare";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    void updateSomministrazione(Somministrazione somministrazione);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ReminderEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField MTextBox titleBox;
  @UiField MTextBox dateBox;
  @UiField MTextBox qtaBox;
  @UiField MTextBox umBox;
  @UiField MTextBox oraBox;
  @UiField Spacer popupRuler;
  
  public ReminderEditView() {
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
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_SOMMINISTRAZIONE.equals(tag)) {
      final Somministrazione somministrazione = (Somministrazione)model;
//    AppClientFactory.IMPL.setEditingSomministrazione(somministrazione);
      titleBox.setValue(somministrazione.getPrescrizione().getNome());
      dateBox.setValue(PhonegapUtils.dateToString(somministrazione.getData()));
      if (NumberUtils.isInteger(somministrazione.getQuantita())) {
        Integer qti = (int)Math.floor(somministrazione.getQuantita());
        qtaBox.setValue(""+qti);
      } else {
        qtaBox.setValue(""+somministrazione.getQuantita());
      }
      oraBox.setValue(somministrazione.getOrario());
      
      if (MainController.isScaduta(somministrazione)) {
        
        PhgDialogUtils.showMessageDialog(AppMessages.IMPL.ReminderEditView_setModel_msg1(), AppMessages.IMPL.ReminderEditView_setModel_title1(), PhgDialogUtils.BUTTONS_YESNO,
            new Position(getPopupTop(), null) /* null */,
            new Delegate<Integer>() {
              public void execute(Integer btnIndex) {
                if (btnIndex == 1) {
                  PhonegapUtils.log("executing " + somministrazione);
                  somministrazione.setEseguita();
                  getPresenter().updateSomministrazione(somministrazione);
                }
                if (btnIndex == 2) {
                  PhonegapUtils.log("canceling " + somministrazione);
                  somministrazione.setAnnullata();
                  getPresenter().updateSomministrazione(somministrazione);
                }
              }
            }
          );
        
      }
      
    } else if (TAG_FARMACO_DA_RIORDINARE.equals(tag)) {
      
      final Somministrazione somministrazione = (Somministrazione)model;
      
      int qtaRimanente = NumberUtils.doubleAsInt(somministrazione.getPrescrizione().getQtaRimanente());
      
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.ReminderEditView_setModel_msg2(qtaRimanente), "Alert", PhgDialogUtils.BUTTONS_OK,
          new Position(getPopupTop(), null) /* null */,
          new Delegate<Integer>() {
            public void execute(Integer btnIndex) {

            }
          }
        );
      
    }
    
  }
  
  private int getPopupTop() {
    int top = popupRuler.getAbsoluteTop();
    if (top > 0) {
      PhonegapUtils.setLocalStorageProperty("ReminderEditView.popupRuler.top", ""+top);
    } else {
      String v = PhonegapUtils.getLocalStorageProperty("ReminderEditView.popupRuler.top");
      if (v != null) {
        top = Integer.parseInt(v);
      } else {
        top = Window.getClientHeight() / 2;
      }
    }
    return top;
  }

}

package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.DosageEditView.Presenter;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.DosaggioEditModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class DosageEditView extends BaseMgwtView <Presenter> {

  public static final String TAG_DOSAGGIO = "dosaggio";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToTherapyEditView(Prescrizione prescrizione);
    public void getUdmDescription(Double qta, final String currentUdmCode, final Delegate<UdM> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, DosageEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label dsgLbl;
  @UiField PhTextBox qtaBox;
  @UiField Label umLabel;
  

  private DosaggioEditModel model;
  
  public DosageEditView() {
    initUI();
  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    wrapperPanel.getElement().getStyle().clearHeight();
  }
    
  private void initProvidedElements() {

  }

  @Override
  public void setModel(Object model, String tag) {
    if (TAG_DOSAGGIO.equals(tag)) {
      TouchUtils.setDisabled(qtaBox.getElement());
      this.model = (DosaggioEditModel)model;
      dsgLbl.setText(AppMessages.IMPL.DosageEditView_setModel_msg1(this.model.getDosaggio().getOrario()));
      qtaBox.setValue(this.model.getDosaggio().getQuantita());
      getPresenter().getUdmDescription(qtaBox.getValueAsDouble(), this.model.getDosaggio().getCodUdM(), new Delegate<UdM>() {
        public void execute(UdM udm) {
          umLabel.setText(udm.getDescrizione());
        }
      });
      TouchUtils.setEnabled(1000, qtaBox.getElement());
    }
  }
  
  @UiHandler ("okBtn")
  public void onOkBtn (TouchEndEvent event) {
    model.getDosaggio().setQuantita(qtaBox.getValueAsDouble());
    getPresenter().goToTherapyEditView(model.getPrescrizione());
  }
  
}

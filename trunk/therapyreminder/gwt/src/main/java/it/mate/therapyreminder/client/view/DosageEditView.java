package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.utils.LogUtil;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.DosageEditView.Presenter;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.UdM;

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
    public void adaptUmDescription(Double qta, final String currentUdmCode, final Delegate<UdM> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, DosageEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label dsgLbl;
  @UiField PhTextBox qtaBox;
  @UiField Label umLabel;
  

  private Dosaggio dosaggio;
  
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
      LogUtil.log("disabling qtaBox element");
      TouchUtils.setDisabled(JQuery.withElement(qtaBox.getElement()));
      this.dosaggio = (Dosaggio)model;
      dsgLbl.setText("Dosaggio delle ore " + dosaggio.getOrario());
      qtaBox.setValue(dosaggio.getQuantita());
      getPresenter().adaptUmDescription(qtaBox.getValueAsDouble(), dosaggio.getCodUdM(), new Delegate<UdM>() {
        public void execute(UdM udm) {
          umLabel.setText(udm.getDescrizione());
        }
      });
      GwtUtils.deferredExecution(1000, new Delegate<Void>() {
        public void execute(Void element) {
          LogUtil.log("enabling qtaBox element");
          TouchUtils.setEnabled(JQuery.withElement(qtaBox.getElement()));
        }
      });
//    TouchUtils.setEnabled(JQuery.withElement(qtaBox.getElement()));
    }
  }
  
  @UiHandler ("okBtn")
  public void onOkBtn (TouchEndEvent event) {
    dosaggio.setQuantita(qtaBox.getValueAsDouble());
    getPresenter().goToTherapyEditView(dosaggio.getPrescrizione());
  }
  
}

package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.ReminderEditView.Presenter;
import it.mate.therapyreminder.shared.model.Somministrazione;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class ReminderEditView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_SOMMINISTRAZIONE = "somministrazione";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, ReminderEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField MTextBox titleBox;
  @UiField MTextBox dateBox;
  @UiField MTextBox qtaBox;
  @UiField MTextBox umBox;
  @UiField MTextBox oraBox;
  
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
  public void onUnload() {
    AppClientFactory.IMPL.setEditingSomministrazione(null);
    super.onUnload();
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_SOMMINISTRAZIONE.equals(tag)) {
      Somministrazione somministrazione = (Somministrazione)model;
      AppClientFactory.IMPL.setEditingSomministrazione(somministrazione);
      titleBox.setValue(somministrazione.getPrescrizione().getNome());
      dateBox.setValue(PhonegapUtils.dateToString(somministrazione.getData()));
      qtaBox.setValue(""+somministrazione.getQuantita());
      oraBox.setValue(somministrazione.getOrario());
    }
  }

}

package it.mate.copymob.client.view;

import it.mate.copymob.client.view.TimbroPreviewView.Presenter;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class TimbroPreviewView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToTimbroEditView(Timbro timbro);
  }

  public interface ViewUiBinder extends UiBinder<Widget, TimbroPreviewView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  Timbro timbro;
  
  public TimbroPreviewView() {
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
    if (model instanceof Timbro) {
      this.timbro = (Timbro)model;
    }
  }
  
  @UiHandler("btnEdit")
  public void onBtnEdit(TapEvent event) {
    getPresenter().goToTimbroEditView(timbro);
  }
}

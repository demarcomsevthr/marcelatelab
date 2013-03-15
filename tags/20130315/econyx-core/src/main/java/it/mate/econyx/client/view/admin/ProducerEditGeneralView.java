package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.ProducerEditor;
import it.mate.econyx.client.view.ProducerEditView;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ProducerEditGeneralView extends AbstractAdminTabPage<ProducerEditView.Presenter> implements ProducerEditView, IsAdminTabPage<ProducerEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProducerEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField ProducerEditor editor;
  
  public ProducerEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Produttore) {
      editor.setModel((Produttore)model);
    }
  }
  
  @Override
  protected void onDetach() {
    super.onDetach();
    System.out.println("detach " + this.getClass());
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {

  }
  
}

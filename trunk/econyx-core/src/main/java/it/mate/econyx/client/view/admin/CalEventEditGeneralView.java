package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.CalEventEditor;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CalEventEditGeneralView extends AbstractAdminTabPage<CalEventEditView.Presenter> implements CalEventEditView, IsAdminTabPage<CalEventEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, CalEventEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField CalEventEditor editor;
  
  public CalEventEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof CalEvent) {
      editor.setModel((CalEvent)model);
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    CalEvent editedInstance = editor.flushModel();
    if (!validateCalEvent(editedInstance)) {
      return;
    }
    CalEvent event = (CalEvent)model;
    event.setName(editedInstance.getName());
    event.setCode(editedInstance.getCode());
    event.setOrderNm(editedInstance.getOrderNm());
    delegate.execute(event);
  }
  
  private boolean validateCalEvent(CalEvent event) {
    if (GwtUtils.isEmpty(event.getTitle())) {
      Window.alert("L'oggetto dell'evento è vuoto");
      return false;
    }
    return true;
  }
  
}

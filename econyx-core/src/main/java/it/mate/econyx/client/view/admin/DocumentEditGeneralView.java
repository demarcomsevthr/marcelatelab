package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.DocumentEditor;
import it.mate.econyx.client.view.DocumentEditView;
import it.mate.econyx.shared.model.Document;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DocumentEditGeneralView extends AbstractAdminTabPage<DocumentEditView.Presenter> implements DocumentEditView, IsAdminTabPage<DocumentEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField DocumentEditor editor;
  
  public DocumentEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Document) {
      editor.setModel((Document)model);
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    Document editedInstance = editor.flushModel();
    if (!validateDocument(editedInstance)) {
      return;
    }
    Document document = (Document)model;
    document.setName(editedInstance.getName());
    document.setCode(editedInstance.getCode());
    document.setOrderNm(editedInstance.getOrderNm());
    document.setCreated(editedInstance.getCreated());
    delegate.execute(document);
  }
  
  private boolean validateDocument(Document document) {
    if (GwtUtils.isEmpty(document.getName())) {
      Window.alert("Il nome del documento è vuoto");
      return false;
    }
    /*
    if (GwtUtils.isEmpty(document.getCode())) {
      Window.alert("Il codice del documento è vuoto");
      return false;
    }
    */
    if (document.getCode() != null && document.getCode().contains(" ")) {
      Window.alert("Il codice del documento non può contenere spazi");
      return false;
    }
    return true;
  }
  
}

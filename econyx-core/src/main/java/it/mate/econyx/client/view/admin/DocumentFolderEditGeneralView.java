package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.DocumentFolderEditor;
import it.mate.econyx.client.view.DocumentFolderEditView;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DocumentFolderEditGeneralView extends AbstractAdminTabPage<DocumentFolderEditView.Presenter> implements DocumentFolderEditView, IsAdminTabPage<DocumentFolderEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentFolderEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField DocumentFolderEditor editor;
  
  public DocumentFolderEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof DocumentFolder) {
      editor.setModel((DocumentFolder)model);
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    DocumentFolder editedInstance = editor.flushModel();
    if (!validateDocumentFolder(editedInstance)) {
      return;
    }
    DocumentFolder folder = (DocumentFolder)model;
    folder.setName(editedInstance.getName());
    folder.setCode(editedInstance.getCode());
    folder.setOrderNm(editedInstance.getOrderNm());
    delegate.execute(folder);
  }
  
  private boolean validateDocumentFolder(DocumentFolder folder) {
    if (GwtUtils.isEmpty(folder.getName())) {
      Window.alert("Il nome della raccolta è vuoto");
      return false;
    }
    /*
    if (GwtUtils.isEmpty(folder.getCode())) {
      Window.alert("Il codice della raccolta è vuoto");
      return false;
    }
    */
    if (folder.getCode() != null && folder.getCode().contains(" ")) {
      Window.alert("Il codice della raccolta non può contenere spazi");
      return false;
    }
    return true;
  }
  
}

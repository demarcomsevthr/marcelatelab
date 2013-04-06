package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.DocumentEditor;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.DocumentEditView;
import it.mate.econyx.shared.model.Document;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

public class DocumentEditGeneralView extends AbstractAdminTabPage<DocumentEditView.Presenter> implements DocumentEditView, IsAdminTabPage<DocumentEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField DocumentEditor editor;
  @UiField Button showFormBtn;
  @UiField Anchor contentAnchor;
  @UiField FormPanel uploadForm;
  @UiField Hidden objId;
  
  private Document document;
  
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
      this.document = (Document)model;
      editor.setModel(document);
      if (document.getId() != null && document.getCode() != null) {
        showFormBtn.setVisible(true);
        contentAnchor.setVisible(true);
        contentAnchor.setHref(UrlUtils.getDocumentContentUrl(document.getCode()));
        contentAnchor.setText(UrlUtils.getDocumentContentUrl(document.getCode()));
      }
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
    if (document.getCode() != null && document.getCode().contains(" ")) {
      Window.alert("Il codice del documento non può contenere spazi");
      return false;
    }
    return true;
  }

  @UiHandler ("showFormBtn")
  public void onShowFormBtn (ClickEvent event) {
    if (document.getId() == null) {
      Window.alert("Devi salvare il nuovo documento prima di poter fare l'upload del file");
      return;
    }
    objId.setValue(document.getId());
    uploadForm.setAction(UrlUtils.getUploadServletUrl());
    uploadForm.setMethod(FormPanel.METHOD_POST);
    uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
    uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      public void onSubmitComplete(SubmitCompleteEvent event) {
        getPresenter().edit(document);
      }
    });
    uploadForm.setVisible(true);
  }
  
  @UiHandler ("submitBtn")
  public void onSubmitBtn(ClickEvent event) {
    uploadForm.submit();
  }
  
}

package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.DocumentFolder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DocumentFolderEditor extends Composite implements Editor<DocumentFolder> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentFolderEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<DocumentFolder, DocumentFolderEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox name;
  @UiField TextBox code;
  @UiField IntegerBox orderNm;
  
  public DocumentFolderEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(DocumentFolder model) {
    driver.edit(model);
  }
  
  public DocumentFolder flushModel() {
    DocumentFolder model = driver.flush();
    return model;
  }
  
}

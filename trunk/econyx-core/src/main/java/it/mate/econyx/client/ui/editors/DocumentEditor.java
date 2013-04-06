package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Document;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DocumentEditor extends Composite implements Editor<Document> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Document, DocumentEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox name;
  @UiField TextBox code;
  @UiField IntegerBox orderNm;
  @UiField DateBox created;
  
  public DocumentEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Document document) {
    driver.edit(document);
  }
  
  public Document flushModel() {
    Document document = driver.flush();
    return document;
  }
  
}

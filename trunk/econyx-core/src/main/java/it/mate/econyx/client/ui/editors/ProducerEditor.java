package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Produttore;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProducerEditor extends Composite implements Editor<Produttore> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, ProducerEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Produttore, ProducerEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox codice;
  @UiField TextBox nome;
  
  public ProducerEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Produttore order) {
    driver.edit(order);
  }
  
  public Produttore flushModel() {
    Produttore model = driver.flush();
    return model;
  }
  
}

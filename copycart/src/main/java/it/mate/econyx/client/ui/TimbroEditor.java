package it.mate.econyx.client.ui;

import it.mate.econyx.client.ui.editors.AbstractArticoloEditor;
import it.mate.econyx.shared.model.Timbro;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Widget;

public class TimbroEditor extends AbstractArticoloEditor<Timbro> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, TimbroEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Timbro, TimbroEditor> { }
  
  protected Driver driver = GWT.create(Driver.class);
  
  public @UiField IntegerBox larghezza;
  public @UiField IntegerBox altezza;
  public @UiField IntegerBox numRighe;
  
  public TimbroEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Timbro timbro) {
    driver.edit(timbro);
  }
  
  public Timbro flushModel() {
    Timbro timbro = (Timbro)driver.flush();
    return timbro;
  }
  
}

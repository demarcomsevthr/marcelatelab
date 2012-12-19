package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Articolo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class ArticoloEditor extends AbstractArticoloEditor<Articolo> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticoloEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Articolo, ArticoloEditor> { }
  
  protected Driver driver = GWT.create(Driver.class);
  
  public ArticoloEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Articolo product) {
    driver.edit(product);
  }
  
  public Articolo flushModel() {
    Articolo product = (Articolo)driver.flush();
    return product;
  }
  
}

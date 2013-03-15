package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImageEditor extends Composite implements Editor<Image> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, ImageEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Image, ImageEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox code;
  
  public ImageEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Image order) {
    driver.edit(order);
  }
  
  public Image flushModel() {
    Image image = driver.flush();
    return image;
  }
  
}

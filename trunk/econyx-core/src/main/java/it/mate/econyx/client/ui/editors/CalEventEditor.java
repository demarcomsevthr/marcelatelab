package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.CalEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CalEventEditor extends Composite implements Editor<CalEvent> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, CalEventEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<CalEvent, CalEventEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox title;
  @UiField TextBox code;
  
  public CalEventEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(CalEvent event) {
    driver.edit(event);
  }
  
  public CalEvent flushModel() {
    CalEvent event = driver.flush();
    return event;
  }
  
}

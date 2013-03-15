package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Order;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class OrderEditor extends Composite implements Editor<Order> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, OrderEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Order, OrderEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox code;
  @UiField TextBox description;
  
  public OrderEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Order order) {
    driver.edit(order);
  }
  
  public Order flushModel() {
    Order order = driver.flush();
    return order;
  }
  
}

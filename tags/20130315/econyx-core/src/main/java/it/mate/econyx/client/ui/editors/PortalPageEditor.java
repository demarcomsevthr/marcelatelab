package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.PortalPage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageEditor extends Composite implements Editor<PortalPage> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<PortalPage, PortalPageEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox name;
  @UiField TextBox code;
  @UiField IntegerBox orderNm;
  @UiField CheckBox visibleInExplorer;
  @UiField CheckBox visibleInMenu;
  @UiField CheckBox homePage;
  
  public PortalPageEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(PortalPage page) {
    driver.edit(page);
  }
  
  public PortalPage flushModel() {
    PortalPage page = driver.flush();
    return page;
  }
  
}

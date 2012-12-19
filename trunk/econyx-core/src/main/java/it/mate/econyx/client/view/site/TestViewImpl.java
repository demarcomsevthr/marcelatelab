package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.TestView;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class TestViewImpl extends AbstractBaseView<TestView.Presenter> implements TestView {

  public interface ViewUiBinder extends UiBinder<Widget, TestViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  
  @UiField Panel roundedPanel;
  

  public TestViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    
    GwtUtils.setStyleAttribute(roundedPanel, "border", "2px solid lightblue");
    GwtUtils.setBorderRadius(roundedPanel, "8px");
    
  }
  
  public void setModel(Object model, String tag) {
    
  }
  
}

package it.mate.protoph.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protoph.client.utils.DndUtils;
import it.mate.protoph.client.view.TestView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class TestView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, TestView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  public TestView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    getScrollPanel().setScrollingEnabledX(false);
    getScrollPanel().setScrollingEnabledY(false);
    
    final MyPopup popup = new MyPopup();
    popup.getElement().getStyle().setBackgroundColor("transparent");
    popup.show();
    popup.getElement().getStyle().setTop(50, Unit.PX);
    
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        doTest();
      }
    });
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  private class MyPopup extends PopupPanel {
    public MyPopup() {
      super();
      String html = "<div id='div1'></div><img id='drag1' src='main/images/ingredient-test.png' width='64' height='64'></img>";
      HTML wid = new HTML(html);
      setWidget(wid);
    }
  }
  
  private void doTest() {
    
    Element dropable = DOM.getElementById("div1");
    dropable.getStyle().setBorderWidth(1, Unit.PX);
    dropable.getStyle().setBorderStyle(BorderStyle.SOLID);
    dropable.getStyle().setBorderColor("red");
    dropable.getStyle().setWidth(200, Unit.PX);
    dropable.getStyle().setHeight(100, Unit.PX);
    
    Element dragable = DOM.getElementById("drag1");
    
    DndUtils.doTest(dropable, dragable);
    
  }

}

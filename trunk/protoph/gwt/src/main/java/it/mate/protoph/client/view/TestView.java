package it.mate.protoph.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protoph.client.utils.DndUtils;
import it.mate.protoph.client.utils.HammerUtils;
import it.mate.protoph.client.utils.HammerUtils.DragEvent;
import it.mate.protoph.client.view.TestView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class TestView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, TestView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTMLPanel wrapperPanel;
  
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
    
//  doTestWithDndUtils();
    doTestWithHammer();
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  private void doTestWithHammer() {
    
    String html = "";
    html += "<img id='dragable1' src='main/images/cherry.png' width='64' height='64'></img>";
    html += "<div id='dropable1'></div>";
    HTML widget = new HTML();
    widget.setHTML(html);
    wrapperPanel.add(widget);
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        
        final Element dropable = DOM.getElementById("dropable1");
        dropable.getStyle().setBorderWidth(1, Unit.PX);
        dropable.getStyle().setBorderStyle(BorderStyle.SOLID);
        dropable.getStyle().setBorderColor("red");
        dropable.getStyle().setWidth(200, Unit.PX);
        dropable.getStyle().setHeight(100, Unit.PX);
        dropable.getStyle().setPosition(Position.ABSOLUTE);
        dropable.getStyle().setTop(100, Unit.PX);
        
        final Element dragable = DOM.getElementById("dragable1");
        dragable.getStyle().setWidth(70, Unit.PX);
        dragable.getStyle().setHeight(70, Unit.PX);
        dragable.getStyle().setPosition(Position.ABSOLUTE);
        
        HammerUtils.initialize(new Delegate<Void>() {
          public void execute(Void element) {
            
            HammerUtils.cloneElementOnPress(dragable, new HammerUtils.ElementCallback() {
              public void handle(Element clonedElement) {
                HammerUtils.makeDraggable(clonedElement, new Delegate<HammerUtils.DragEvent>() {
                  public void execute(DragEvent event) {
                    PhgUtils.log("drag event " + event.getX() + " " + event.getY());
                  }
                });
              }
            });

            /*
            HammerUtils.makeDraggable(dragable, new Delegate<HammerUtils.DragEvent>() {
              public void execute(DragEvent event) {
                PhgUtils.log("drag event " + event.getX() + " " + event.getY());
              }
            });
            */
            
          }
        });

      }
    });
    
  }
  
  private void doTestWithDndUtils() {
    MyPopup popup = new MyPopup();
    popup.getElement().getStyle().setBackgroundColor("transparent");
    popup.show();
    popup.getElement().getStyle().setTop(50, Unit.PX);
//  doTest1(popup);
//  doTest2(popup);
    doTest3(popup);
  }
  
  private class MyPopup extends PopupPanel {
    HTML widget;
    public MyPopup() {
      super();
      widget = new HTML();
      setWidget(widget);
    }
  }
  
  private void doTest1(MyPopup popup) {
    String html = "";
    html += "<div id='div1'></div>";
    html += "<img id='drag1' src='main/images/ingredient-test.png' width='64' height='64'></img>";
    popup.widget.setHTML(html);
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        Element dropable = DOM.getElementById("div1");
        dropable.getStyle().setBorderWidth(1, Unit.PX);
        dropable.getStyle().setBorderStyle(BorderStyle.SOLID);
        dropable.getStyle().setBorderColor("red");
        dropable.getStyle().setWidth(200, Unit.PX);
        dropable.getStyle().setHeight(100, Unit.PX);
        Element dragable = DOM.getElementById("drag1");
        DndUtils.doTest1(dropable, dragable);
      }
    });
  }
  
  private void doTest2(MyPopup popup) {
    String html = "";
    html += "<div id='dropable1'></div>";
    html += "<div id='dragable1'></div>";
    popup.widget.setHTML(html);
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        Element dragable = DOM.getElementById("dragable1");
        dragable.getStyle().setWidth(70, Unit.PX);
        dragable.getStyle().setHeight(70, Unit.PX);
        dragable.getStyle().setBackgroundColor("red");
        dragable.getStyle().setPosition(Position.ABSOLUTE);
        DndUtils.doTest2(dragable);
      }
    });
  }
  
  private void doTest3(MyPopup popup) {
    String html = "";

    /*
    html += "<div id='dragable1'></div>";
    html += "<div id='dropable1'></div>";
    */
    
    html += "<img id='dragable1' src='main/images/cherry.png' width='64' height='64'></img>";
    html += "<div id='dropable1'></div>";
    
    popup.widget.setHTML(html);
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        
        final Element dropable = DOM.getElementById("dropable1");
        dropable.getStyle().setBorderWidth(1, Unit.PX);
        dropable.getStyle().setBorderStyle(BorderStyle.SOLID);
        dropable.getStyle().setBorderColor("red");
        dropable.getStyle().setWidth(200, Unit.PX);
        dropable.getStyle().setHeight(100, Unit.PX);
        dropable.getStyle().setPosition(Position.ABSOLUTE);
        dropable.getStyle().setTop(100, Unit.PX);
        
        Element dragable = DOM.getElementById("dragable1");
        dragable.getStyle().setWidth(70, Unit.PX);
        dragable.getStyle().setHeight(70, Unit.PX);
//      dragable.getStyle().setBackgroundColor("red");
        dragable.getStyle().setPosition(Position.ABSOLUTE);
//      dragable.getStyle().setZIndex(Integer.MAX_VALUE);

        DndUtils.doTest4(dragable, dropable);

      }
    });
  }
  
}

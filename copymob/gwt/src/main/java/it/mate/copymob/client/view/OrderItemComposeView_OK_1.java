package it.mate.copymob.client.view;

import it.mate.copymob.client.view.OrderItemComposeView_OK_1.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.impl.OrderItemRowTx;
import it.mate.copymob.shared.model.impl.OrderItemTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.event.TouchEventUtils;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsHorizontalPanel;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsListItem;
import it.mate.onscommons.client.ui.OnsScroller;
import it.mate.onscommons.client.ui.OnsTextBox;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemComposeView_OK_1 extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void saveCurrentOrderItem(OrderItem item, Delegate<Order> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderItemComposeView_OK_1> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField OnsList rowsPanel;
  @UiField OnsScroller scroller;
  @UiField Panel controlbar;
  @UiField Panel fontsizebar;
  @UiField Spacer viewfinder;
//@UiField OnsButton btnEdtSizeText;
  
  @UiField OnsButton btnFntSize8;
  @UiField OnsButton btnFntSize9;
  @UiField OnsButton btnFntSize10;
  @UiField OnsButton btnFntSize11;
  @UiField OnsButton btnFntSize12;
  
  private OrderItem item;
  
  private boolean controlbarVisible = false;
  
  private Element lastTappedElement;
  
  protected final static double DURATION = 0.2;
  
  private List<OnsTextBox> textboxes = new ArrayList<OnsTextBox>();

  private int selectedRowIndex;
  
  private JavaScriptObject overallEventListener = null;
  
  private static final String CONTROLBAR_VISIBLE_WIDTH = "17.6em";
  private static final String CONTROLBAR_VISIBLE_HEIGHT =  "1.7em";
  private static final int CONTROLBAR_VISIBLE_MARGIN_TOP =  4;

  public OrderItemComposeView_OK_1() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    PhgUtils.ensureId(controlbar.getElement());
    PhgUtils.ensureId(rowsPanel.getElement());
    PhgUtils.ensureId(fontsizebar.getElement());
    PhgUtils.ensureId(viewfinder.getElement());
  }

  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof OrderItem) {
      item = (OrderItem)model;
      PhgUtils.log("editing item " + item);
      for (OrderItemRow row : item.getRows()) {
        PhgUtils.log("   with row " + row);
      }
      if (item.getRows().size() == 0) {
        item.getRows().add(new OrderItemRowTx(""));
      }
      for (int it = 0; it < item.getRows().size(); it++) {
        OrderItemRow row = item.getRows().get(it);
        rowsPanel.add(createRowItem(row.getText()));
      }
    } else {
      // FOR DEBUG
      item = new OrderItemTx();
      item.getRows().add(new OrderItemRowTx(""));
      rowsPanel.add(createRowItem(""));
    }
    rowsPanel.add(createLastRowItem());
  }
  
  private OnsListItem createRowItem(String text) {
    final int index = textboxes.size();
    OnsHorizontalPanel rowpanel = new OnsHorizontalPanel();
    rowpanel.setWidth("100%");
    OnsTextBox textbox = new OnsTextBox();
    textbox.addStyleName("app-edit-text");
    textbox.setText(text);
    rowpanel.add(textbox);
    final OnsButton controlBtn = new OnsButton();
    controlBtn.addStyleName("app-edit-btn-cfg");
    controlBtn.setIcon("fa-bars");
    controlBtn.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        switchControlbar(GwtUtils.getElement(controlBtn), index);
      }
    });
    rowpanel.add(controlBtn);
    textboxes.add(textbox);
    OnsListItem item = new OnsListItem();
    item.add(rowpanel);
    return item;
  }
  
  private OnsListItem createLastRowItem() {
    OnsHorizontalPanel rowpanel = new OnsHorizontalPanel();
    rowpanel.setWidth("100%");
    rowpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    final OnsButton addBtn = new OnsButton();
    addBtn.addStyleName("app-edit-btn-cfg");
    addBtn.setIcon("fa-plus-square");
    addBtn.setText("add");
    addBtn.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        PhgUtils.log("TAPPED");
        item.getRows().add(new OrderItemRowTx(""));
        rowsPanel.insert(createRowItem(""), rowsPanel.getItemCount() - 1);
      }
    });
    rowpanel.add(addBtn);
    OnsListItem item = new OnsListItem();
    item.add(rowpanel);
    return item;
  }
  
  private OrderItem flushModel() {
    for (int it = 0; it < textboxes.size(); it++) {
      OnsTextBox textbox = textboxes.get(it);
      boolean lastRow = it == (textboxes.size() - 1);
      if (!lastRow || textbox.getText().trim().length() > 0) {
        if (item.getRows().size() <= it)
          item.getRows().add(new OrderItemRowTx());
        item.getRows().get(it).setText(textbox.getText());
      }
    }
    for (int it = item.getRows().size() - 1; it > 0; it--) {
      OrderItemRow row = item.getRows().get(it);
      if (row.getText().trim().length() == 0) {
        item.getRows().remove(it);
      } else {
        break;
      }
    }
    return item;
  }

  @UiHandler("btnSave")
  public void onBtnSave(TapEvent event) {
    getPresenter().saveCurrentOrderItem(flushModel(), new Delegate<Order>() {
      public void execute(Order element) {
        getPresenter().goToPrevious();
      }
    });
  }

  @UiHandler("btnEdtBold")
  public void onBtnEdtBold(TapEvent event) {
    showTargetPosition(event.getTargetElement());
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    row.setBold(!row.isBold());
    if (row.isBold()) {
      textboxes.get(selectedRowIndex).getElement().addClassName("app-bold");
    } else {
      textboxes.get(selectedRowIndex).getElement().replaceClassName("app-bold", "");
    }
  }
  
  @UiHandler("btnEdtSize")
  public void onBtnEdtSize(TapEvent event) {
    showTargetPosition(event.getTargetElement());
    showControlbar(GwtUtils.getElement(fontsizebar), 
        getTargetX(event), getTargetY(event), getTargetX(event), getTargetY(event), "2.05em", "10em");
  }
  
  @UiHandler({"btnFntSize8", "btnFntSize9", "btnFntSize10", "btnFntSize11", "btnFntSize12"})
  public void onBtnFntSize(TapEvent event) {
    setItemSize(Integer.parseInt(event.getTargetElement().getInnerText().trim()));
  }
  private void setItemSize(double size) {
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    row.setSize((int)size);
    textboxes.get(selectedRowIndex).getElement().getStyle().setFontSize(size / 10, Unit.EM);
//  btnEdtSizeText.setText(""+((int)size));
    /*
    btnFntSize8.addStyleName("");
    btnFntSize8.removeStyleName("");
    */
  }
  
  private int getTargetX(TapEvent event) {
    return event.getTargetElement().getAbsoluteLeft();
  }
  
  private int getTargetY(TapEvent event) {
    return event.getTargetElement().getAbsoluteTop();
  }
  
  
  private void showTargetPosition(Element targetElement) {
    int tx = targetElement.getAbsoluteLeft();
    int ty = targetElement.getAbsoluteTop();
    PhgUtils.log("target.left = " + tx + " target.top = " + ty);
    GwtUtils.getElement(viewfinder).getStyle().setLeft(tx, Unit.PX);
    GwtUtils.getElement(viewfinder).getStyle().setTop(ty, Unit.PX);
    GwtUtils.getElement(viewfinder).getStyle().setZIndex(99);
//  viewfinder.setVisible(true);
  }
  
  private void switchControlbar(final Element tappedElement, int index) {
    PhgUtils.log(GwtUtils.getElementOffset(tappedElement));
    int x0 = GwtUtils.getElementOffsetLeft(tappedElement);
    int y0 = GwtUtils.getElementOffsetTop(tappedElement) - CONTROLBAR_VISIBLE_MARGIN_TOP;
    int x1 = 5;
    int y1 = y0;
    if (controlbarVisible && isLastTappedElement(tappedElement)) {
      PhgUtils.log("hiding crontrolbar");
      GwtUtils.getElement(fontsizebar).getStyle().setDisplay(Display.NONE);
      hideControlbar(GwtUtils.getElement(controlbar), x0, y0, x1, y1, CONTROLBAR_VISIBLE_WIDTH, CONTROLBAR_VISIBLE_HEIGHT);
      if (overallEventListener != null) {
        TouchEventUtils.removeEventListener(overallEventListener);
        overallEventListener = null;
      }
    } else {
      selectedRowIndex = index;
      PhgUtils.log("showing crontrolbar");
      showControlbar(GwtUtils.getElement(controlbar), x0, y0, x1, y1, CONTROLBAR_VISIBLE_WIDTH, CONTROLBAR_VISIBLE_HEIGHT);
      lastTappedElement = tappedElement;
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          overallEventListener = TouchEventUtils.addOverallEventListener(new Delegate<Element>() {
            public void execute(Element element) {
              if (TouchEventUtils.isContained(element, controlbar.getElement().getId())) {
                //nothing
              } else if (TouchEventUtils.isContained(element, fontsizebar.getElement().getId())) {
                GwtUtils.getElement(fontsizebar).getStyle().setDisplay(Display.NONE);
              } else {
                switchControlbar(tappedElement, -1);
              }
            }
          });
        }
      });
    }
    controlbarVisible = !controlbarVisible;
  }
  
  private boolean isLastTappedElement(Element tappedElement) {
    return lastTappedElement == null || lastTappedElement.getId().equals(tappedElement.getId());
  }
  
  protected static native JavaScriptObject hiddenStyle(int x0, int y0) /*-{
    return { 
      left: x0+'px',
      top: y0+'px',
      width: 0,
      height: 0
    };
  }-*/;
  
  protected static native JavaScriptObject visibleStyle(int x1, int y1, String width, String height) /*-{
    return { 
      left: x1+'px',
      top: y1+'px',
      width: width,
      height: height
    };
  }-*/;
  
  protected final native void showControlbar(Element elem, int x0, int y0, int x1, int y1, String width, String height) /*-{
    $wnd.animit(elem)
      .queue({
        css: {
          display: 'initial'
        },
        duration: 0
      })
      .queue({
        css: @it.mate.copymob.client.view.OrderItemComposeView::hiddenStyle(II)(x0, y0) ,
        duration: 0
      })
      .queue({
        css: @it.mate.copymob.client.view.OrderItemComposeView::visibleStyle(IILjava/lang/String;Ljava/lang/String;)(x1, y1, width, height),
        duration: @it.mate.copymob.client.view.OrderItemComposeView::DURATION
      })
      .play();
  }-*/;
  
  protected final native void hideControlbar(Element elem, int x0, int y0, int x1, int y1, String width, String height) /*-{
    $wnd.animit(elem)
      .queue({
//      css: @it.mate.copymob.client.view.OrderItemComposeView::visibleStyle(II)(x1, y1),
        css: @it.mate.copymob.client.view.OrderItemComposeView::visibleStyle(IILjava/lang/String;Ljava/lang/String;)(x1, y1, width, height),
        duration: 0
      })
      .queue({
        css: @it.mate.copymob.client.view.OrderItemComposeView::hiddenStyle(II)(x0, y0) ,
        duration: @it.mate.copymob.client.view.OrderItemComposeView::DURATION
      })
      .queue({
        css: {
          display: 'none'
        },
        duration: 0
      })
      .play();
  }-*/;

}

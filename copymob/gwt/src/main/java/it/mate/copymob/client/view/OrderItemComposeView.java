package it.mate.copymob.client.view;

import it.mate.copymob.client.view.OrderItemComposeView.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.impl.OrderItemRowTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.event.TouchEventUtils;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsHorizontalPanel;
import it.mate.onscommons.client.ui.OnsTextBox;
import it.mate.onscommons.client.ui.OnsVerticalPanel;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemComposeView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void saveCurrentOrderItem(OrderItem item, Delegate<Order> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderItemComposeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsVerticalPanel rowsPanel;

//@UiField HTML controlbar;
  @UiField Panel controlbar;
  
  private OrderItem item;
  
  private boolean controlbarVisible = false;
  
  private Element lastTappedElement;
  
  protected final static double DURATION = 0.3;
  
  private List<OnsTextBox> textboxes = new ArrayList<OnsTextBox>();

  private int selectedRowIndex;
  
  private JavaScriptObject overallEventListener = null;

  public OrderItemComposeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    PhgUtils.ensureId(controlbar.getElement());
    PhgUtils.ensureId(rowsPanel.getElement());
    rowsPanel.setVisible(false);
  }

  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof OrderItem) {
      item = (OrderItem)model;
      for (int it = 0; it < item.getRows().size(); it++) {
        OrderItemRow row = item.getRows().get(it);
        rowsPanel.add(createRowPanel(row.getText()));
      }
    }
    rowsPanel.add(createRowPanel(""));
    GwtUtils.deferredExecution(200, new Delegate<Void>() {
      public void execute(Void element) {
        rowsPanel.setVisible(true);
      }
    });
  }
  
  private HorizontalPanel createRowPanel(String text) {
    final int index = textboxes.size();
    OnsHorizontalPanel rowpanel = new OnsHorizontalPanel();
    rowpanel.setAddDirect(rowsPanel.isAddDirect());
    OnsTextBox textbox = new OnsTextBox();
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
    return rowpanel;
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
    PhgUtils.log("bold on row " + selectedRowIndex);
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    row.setBold(!row.isBold());
    if (row.isBold()) {
      textboxes.get(selectedRowIndex).getElement().addClassName("app-bold");
    } else {
      textboxes.get(selectedRowIndex).getElement().replaceClassName("app-bold", "");
    }
  }
  
  private void switchControlbar(final Element tappedElement, int index) {
    PhgUtils.log(GwtUtils.getElementOffset(tappedElement));
    int x0 = GwtUtils.getElementOffsetLeft(tappedElement);
    int y0 = GwtUtils.getElementOffsetTop(tappedElement) - 10;
    int x1 = 20;
    int y1 = y0;
    if (controlbarVisible && isLastTappedElement(tappedElement)) {
      hideControlbar(GwtUtils.getElement(controlbar), x0, y0, x1, y1);
      if (overallEventListener != null) {
        TouchEventUtils.removeEventListener(overallEventListener);
        overallEventListener = null;
      }
    } else {
      selectedRowIndex = index;
      showControlbar(GwtUtils.getElement(controlbar), x0, y0, x1, y1);
      lastTappedElement = tappedElement;
      overallEventListener = TouchEventUtils.addOverallEventListener(new Delegate<Element>() {
        public void execute(Element element) {
          if (!TouchEventUtils.isContained(element, controlbar.getElement().getId())) {
            switchControlbar(tappedElement, -1);
          }
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
  
  protected static native JavaScriptObject visibleStyle(int x1, int y1) /*-{
    return { 
      left: x1+'px',
      top: y1+'px',
      width: '14em',
      height: '1.8em'
    };
  }-*/;
  
  protected final native void showControlbar(Element elem, int x0, int y0, int x1, int y1) /*-{
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
        css: @it.mate.copymob.client.view.OrderItemComposeView::visibleStyle(II)(x1, y1),
        duration: @it.mate.copymob.client.view.OrderItemComposeView::DURATION
      })
      .play();
  }-*/;
  
  protected final native void hideControlbar(Element elem, int x0, int y0, int x1, int y1) /*-{
    $wnd.animit(elem)
      .queue({
        css: @it.mate.copymob.client.view.OrderItemComposeView::visibleStyle(II)(x1, y1),
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

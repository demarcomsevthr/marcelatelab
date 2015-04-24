package it.mate.copymob.client.view;

import it.mate.copymob.client.utils.AnimitUtils;
import it.mate.copymob.client.view.OrderItemComposeView.Presenter;
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
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsDialog;
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
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemComposeView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void saveOrderItemOnDevice(OrderItem item, Delegate<Order> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderItemComposeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField OnsList rowsPanel;
  @UiField OnsScroller scroller;
  @UiField Panel controlbar;
  @UiField Spacer viewfinder;
  @UiField OnsDialog fontSizeDialog;
  @UiField OnsDialog fontFamilyDialog;
  
  private OrderItem item;
  
  private boolean controlbarVisible = false;
  
  private Element lastTappedElement;
  
  private List<OnsTextBox> textboxes = new ArrayList<OnsTextBox>();

  private int selectedRowIndex;
  
  private JavaScriptObject overallEventListener = null;
  
  private static final String CONTROLBAR_VISIBLE_WIDTH = "17.6em";
  private static final String CONTROLBAR_VISIBLE_HEIGHT =  "2.2em";
  private static final int CONTROLBAR_VISIBLE_MARGIN_TOP =  4;
  
  public OrderItemComposeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    OnsenUi.ensureId(controlbar.getElement());
    OnsenUi.ensureId(rowsPanel.getElement());
    OnsenUi.ensureId(viewfinder.getElement());
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
      item = new OrderItemTx(null);
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
    getPresenter().saveOrderItemOnDevice(flushModel(), new Delegate<Order>() {
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
    fontSizeDialog.show();
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        setItemSizeChecked();
      }
    });
  }
  
  private void setItemSizeChecked() {
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    int size = row.getSize();
    if (size >= 8) {
      String btnFontSizeId = "btnFntSize" + size;
      setRadioChecked(btnFontSizeId);
    } else {
      setRadioChecked("");
    }
  }
  
  private void setRadioChecked(String id) {
    NodeList<Element> elements = GwtUtils.getElementsByTagName("input");
    for (int it = 0; it < elements.getLength(); it++) {
      Element inputElem = elements.getItem(it);
      if ("radio".equalsIgnoreCase(inputElem.getAttribute("type"))) {
        if (id.equals(inputElem.getId())) {
          inputElem.setAttribute("checked", "true");
        } else {
          inputElem.removeAttribute("checked");
        }
      }
    }
  }
  
  @UiHandler("fontSizeCancelBtn")
  public void onFontSizeCancelBtn(TapEvent event) {
    fontSizeDialog.hide();
  }
  
  @UiHandler({"btnFntSize8", "btnFntSize9", "btnFntSize10", "btnFntSize11", "btnFntSize12"})
  public void onBtnFntSize(TapEvent event) {
    setItemSize(Integer.parseInt(((OnsListItem)event.getTargetWidget()).getValue()));
    fontSizeDialog.hide();
  }
  
  private void setItemSize(double size) {
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    row.setSize((int)size);
    textboxes.get(selectedRowIndex).getElement().getStyle().setFontSize(size / 10, Unit.EM);
  }
  
  @UiHandler("btnEdtFont")
  public void onBtnEdtFont(TapEvent event) {
    fontFamilyDialog.show();
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        setFontFamilyChecked();
      }
    });
  }
  
  private void setFontFamilyChecked() {
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    String family = row.getFontFamily();
    if (family == null) {
      setRadioChecked("");
    } else if (family.toLowerCase().contains("georgia")) {
      setRadioChecked("btnFntFamGeorgia");
    } else if (family.toLowerCase().contains("palatino")) {
      setRadioChecked("btnFntFamPalatino");
    } else if (family.toLowerCase().contains("times")) {
      setRadioChecked("btnFntFamTimes");
    } else if (family.toLowerCase().contains("arial")) {
      setRadioChecked("btnFntFamArial");
    }
  }
  
  @UiHandler("fontFamCancelBtn")
  public void onFontFamCancelBtn(TapEvent event) {
    fontFamilyDialog.hide();
  }
  
  @UiHandler({"btnFntFamGeorgia", "btnFntFamPalatino", "btnFntFamTimes"})
  public void onBtnFntFamily(TapEvent event) {
    setFontFamily(((OnsListItem)event.getTargetWidget()).getValue());
    fontFamilyDialog.hide();
  }
  
  private void setFontFamily(String value) {
    OrderItemRow row = item.getRows().get(selectedRowIndex);
    row.setFontFamily(value);
    GwtUtils.setJsPropertyString(textboxes.get(selectedRowIndex).getElement().getStyle(), "fontFamily", value);
  }
  
  /*
  private int getTargetX(TapEvent event) {
    return event.getTargetElement().getAbsoluteLeft();
  }
  
  private int getTargetY(TapEvent event) {
    return event.getTargetElement().getAbsoluteTop();
  }
  */
  
  private void showTargetPosition(Element targetElement) {
    int tx = targetElement.getAbsoluteLeft();
    int ty = targetElement.getAbsoluteTop();
    PhgUtils.log("target.left = " + tx + " target.top = " + ty);
    GwtUtils.getElement(viewfinder).getStyle().setLeft(tx, Unit.PX);
    GwtUtils.getElement(viewfinder).getStyle().setTop(ty, Unit.PX);
    GwtUtils.getElement(viewfinder).getStyle().setZIndex(99);
  }
  
  private void switchControlbar(final Element tappedElement, int index) {
    PhgUtils.log(GwtUtils.getElementOffset(tappedElement));
    int x0 = GwtUtils.getElementOffsetLeft(tappedElement);
    int y0 = GwtUtils.getElementOffsetTop(tappedElement) - CONTROLBAR_VISIBLE_MARGIN_TOP;
    int x1 = 5;
    int y1 = y0;
    if (controlbarVisible && isLastTappedElement(tappedElement)) {
      PhgUtils.log("hiding crontrolbar");
      AnimitUtils.hideComposeControlbar(GwtUtils.getElement(controlbar), x0, y0, x1, y1, CONTROLBAR_VISIBLE_WIDTH, CONTROLBAR_VISIBLE_HEIGHT);
      if (overallEventListener != null) {
        TouchEventUtils.removeEventListener(overallEventListener);
        overallEventListener = null;
      }
    } else {
      selectedRowIndex = index;
      PhgUtils.log("showing crontrolbar");
      AnimitUtils.showComposeControlbar(GwtUtils.getElement(controlbar), x0, y0, x1, y1, CONTROLBAR_VISIBLE_WIDTH, CONTROLBAR_VISIBLE_HEIGHT);
      lastTappedElement = tappedElement;
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          overallEventListener = TouchEventUtils.addOverallEventListener(new Delegate<Element>() {
            public void execute(Element element) {
              if (TouchEventUtils.isContained(element, controlbar.getElement().getId())) {
                //nothing
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

}

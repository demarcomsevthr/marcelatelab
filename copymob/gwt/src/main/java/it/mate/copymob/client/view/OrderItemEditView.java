package it.mate.copymob.client.view;

import it.mate.copymob.client.view.OrderItemEditView.Presenter;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.utils.RenderUtils;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsLabel;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemEditView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToTimbroComposeView(Timbro timbro);
    public void addItemToCart(OrderItem orderItem);
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderItemEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField HTML previewPanel;
  
  @UiField OnsLabel lbNome;
  @UiField OnsLabel lbDimensioni;
  
  @UiField OnsButton btnCart;
  
  OrderItem orderItem;
  
  public OrderItemEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    PhgUtils.ensureId(previewPanel.getElement());
  }

  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof OrderItem) {
      this.orderItem = (OrderItem)model;
    }
    String previewId = previewPanel.getElement().getId();
    GwtUtils.onAvailable(previewId, new Delegate<Element>() {
      public void execute(Element previewElement) {
        
        Timbro timbro = orderItem.getTimbro();
        
        int left = GwtUtils.getElementOffsetLeft(previewElement);
        int top = GwtUtils.getElementOffsetTop(previewElement);
        int width = GwtUtils.getElementOffsetWidth(previewElement);
        int height = GwtUtils.getElementOffsetHeight(previewElement);
        PhgUtils.log("PREVIEW ORIGINAL left = " + left + " top = " + top + " width = " + width + " height = " + height);
        
        double scaleFactor = width / timbro.getWidth();
        PhgUtils.log("SCALE FACTOR = " + scaleFactor);
        
        int newHeight = (int)(timbro.getHeight() * scaleFactor);
        PhgUtils.log("PREVIEW WIDTH = " + width);
        PhgUtils.log("PREVIEW HEIGHT = " + newHeight);
        previewElement.getStyle().setHeight(newHeight, Unit.PX);
        
        if (orderItem.getRows().size() > 0) {
          previewElement.removeAllChildren();
        } else {
          btnCart.setDisabled(true);
        }

        int rowTop = top;
        for (OrderItemRow row : orderItem.getRows()) {
          
          // TODO: estrapolare il rendering in un metodo a parte in modo da poterlo riutilizzare anche lato server

          /*
          Element span = DOM.createSpan();
          span.getStyle().setPosition(Position.FIXED);
          span.getStyle().setTop(rowTop, Unit.PX);
          span.getStyle().setLeft(left, Unit.PX);
          span.setInnerHTML(row.getText());
          span.getStyle().setFontSize(row.getSize(), Unit.PX); 
          GwtUtils.setJsPropertyString(span.getStyle(), "fontFamily", row.getFontFamily()); 
          span.getStyle().setFontWeight(row.isBold() ? FontWeight.BOLD : FontWeight.NORMAL);
          rowTop += row.getSize() + 10;
          */
          
          Element span = RenderUtils.renderAsSpan(row, rowTop, left, 1.4);
          rowTop += span.getPropertyInt("height");
          
          previewElement.appendChild(span);
        }
        
        lbNome.setText(timbro.getNome());
        lbDimensioni.setText("" + timbro.getWidth() + " x " + timbro.getHeight());
        
        
      }
    });
  }
  
  @UiHandler("btnEdit")
  public void onBtnEdit(TapEvent event) {
    getPresenter().goToTimbroComposeView(orderItem.getTimbro());
  }
  
  @UiHandler("btnCart")
  public void onBtnCart(TapEvent event) {
    getPresenter().addItemToCart(orderItem);
  }
  
}

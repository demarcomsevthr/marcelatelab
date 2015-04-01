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
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsLabel;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsListItem;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class OrderItemEditView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToTimbroComposeView(Timbro timbro);
    public void addOrderItemToCart(OrderItem orderItem);
    public void goToMessageListView(OrderItem orderItem);
    public void goToCartListView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, OrderItemEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField HTML previewPanel;
  @UiField OnsLabel lbNome;
  @UiField OnsLabel lbDimensioni;
  @UiField OnsList list;
  
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
      
      if (!orderItem.isInCart()) {
        
        OnsListItem itemCompose = new OnsListItem();
        OnsButton btnCompose = new OnsButton();
        btnCompose.setTextWhenAvailable("Componi il tuo timbro");
        btnCompose.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            getPresenter().goToTimbroComposeView(orderItem.getTimbro());
          }
        });
        itemCompose.add(btnCompose);
        list.add(itemCompose);
        
        if (orderItem.getRows().size() > 0) {
          OnsListItem itemAddToCart = new OnsListItem();
          OnsButton btnAddToCart = new OnsButton();
          btnAddToCart.setTextWhenAvailable("Aggiungi al carrello");
          btnAddToCart.addTapHandler(new TapHandler() {
            public void onTap(TapEvent event) {
              getPresenter().addOrderItemToCart(orderItem);
            }
          });
          itemAddToCart.add(btnAddToCart);
          list.add(itemAddToCart);
        }
        
      } else {
        
        OnsListItem itemCompose = new OnsListItem();
        OnsButton btnCompose = new OnsButton();
        btnCompose.setTextWhenAvailable("Modifica il tuo timbro");
        btnCompose.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            getPresenter().goToTimbroComposeView(orderItem.getTimbro());
          }
        });
        itemCompose.add(btnCompose);
        list.add(itemCompose);
        
        OnsListItem itemMessages = new OnsListItem();
        OnsButton btnMessages = new OnsButton();
        if (orderItem.getMessages() == null || orderItem.getMessages().size() == 0) {
          btnMessages.setTextWhenAvailable("Aggiungi messaggio");
        } else {
          btnMessages.setTextWhenAvailable("Messaggi");
        }
        btnMessages.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            getPresenter().goToMessageListView(orderItem);
          }
        });
        itemMessages.add(btnMessages);
        list.add(itemMessages);
        
        OnsListItem itemBack = new OnsListItem();
        OnsButton btnBack = new OnsButton();
        btnBack.setTextWhenAvailable("Indietro");
        btnBack.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            if (orderItem.getRemoteId() == null) {
              getPresenter().goToCartListView();
            } else {
              //TODO: GO TO ORDER LIST VIEW
            }
          }
        });
        itemBack.add(btnBack);
        list.add(itemBack);
        
      }
      
    }
    
    if (orderItem != null) {
      
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
          }

          int rowTop = top;
          for (OrderItemRow row : orderItem.getRows()) {
            Element span = RenderUtils.renderAsSpan(row, rowTop, left, 1.4);
            rowTop += span.getPropertyInt("height");
            previewElement.appendChild(span);
          }
          
          lbNome.setText(timbro.getNome());
          lbDimensioni.setText("" + timbro.getWidth() + " x " + timbro.getHeight());
          
          
        }
      });
      
    }
  }

  /*
  @UiHandler("btnEdit")
  public void onBtnEdit(TapEvent event) {
    getPresenter().goToTimbroComposeView(orderItem.getTimbro());
  }
  */

  /*
  @UiHandler("btnCart")
  public void onBtnCart(TapEvent event) {
    getPresenter().addOrderItemToCart(orderItem);
  }
  */
  
}

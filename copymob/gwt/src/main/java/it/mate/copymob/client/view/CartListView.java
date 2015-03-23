package it.mate.copymob.client.view;

import it.mate.copymob.client.view.CartListView.Presenter;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsButton;
import it.mate.onscommons.client.ui.OnsHorizontalPanel;
import it.mate.onscommons.client.ui.OnsLabel;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsListItem;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class CartListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, CartListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsList cartList;
  
  private Order order;
  
  public CartListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void setModel(Object model, String tag) {
    
    if (model == null) {
      
      cartList.add(new OnsLabel("Il carrello è vuoto"));
      
    } else if (model instanceof Order) {
      
      this.order = (Order)model;
      populateList(order.getItems().iterator());
      
    }

  }
  
  private void populateList(final Iterator<OrderItem> it) {
    
    while (it.hasNext()) {
      
      final OrderItem orderItem = it.next();
      
      Timbro timbro = orderItem.getTimbro();
      
      OnsListItem listItem = new OnsListItem();
      
      OnsHorizontalPanel rowPanel = new OnsHorizontalPanel();
      
      String html = "<img src='"+ timbro.getImageData() +"' class='app-cart-item-img'/>";
      HTML img = new HTML(html);
      rowPanel.add(img);
      
      OnsLabel nameLbl = new OnsLabel(timbro.getNome());
      rowPanel.add(nameLbl);
      
      OnsHorizontalPanel qtaPanel = new OnsHorizontalPanel();
      qtaPanel.getElement().getStyle().setPaddingLeft(2, Unit.EM);

      final OnsLabel qtaFld = new OnsLabel();
      setQtaLbl(qtaFld, orderItem.getQuantity());
      
      OnsButton plusBtn = new OnsButton();
      plusBtn.getElement().removeClassName("ons-button");
      plusBtn.addStyleName("app-cart-btn-plus");
      plusBtn.setIcon("fa-plus-circle");
      plusBtn.setModifier("quiet");
      plusBtn.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          double qta = orderItem.getQuantity();
          qta ++;
          orderItem.setQuantity(qta);
          setQtaLbl(qtaFld, orderItem.getQuantity());
        }
      });
      OnsButton minusBtn = new OnsButton();
      minusBtn.getElement().removeClassName("ons-button");
      minusBtn.addStyleName("app-cart-btn-minus");
      minusBtn.setIcon("fa-minus-circle");
      minusBtn.setModifier("quiet");
      minusBtn.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          double qta = orderItem.getQuantity();
          if (qta > 0) {
            qta --;
            orderItem.setQuantity(qta);
            setQtaLbl(qtaFld, orderItem.getQuantity());
          }
        }
      });
      
      qtaPanel.add(plusBtn);
      qtaPanel.add(qtaFld);
      qtaPanel.add(minusBtn);
      
      rowPanel.add(qtaPanel);
      
      listItem.add(rowPanel);
      
      cartList.add(listItem);

      // devo fare cosi' altrimenti alcune volte non fa il rendering del bottone!
      OnsenUi.onAvailableElement(plusBtn.getElement(), new Delegate<Element>() {
        public void execute(Element element) {
          populateList(it);
        }
      });
      
    }
    
  }
  
  private void setQtaLbl(OnsLabel lbl, double qta) {
    lbl.setText(GwtUtils.formatDecimal(qta, 0));
  }
  
  
}

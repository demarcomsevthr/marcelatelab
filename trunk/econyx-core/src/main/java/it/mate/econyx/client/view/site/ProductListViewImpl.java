package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.client.view.custom.ProductListViewCustomizer;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProductListViewImpl extends AbstractBaseView<ProductListView.Presenter> implements ProductListView {

  public interface ViewUiBinder extends UiBinder<Widget, ProductListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private static final int NUMERO_COLONNE = 1;
  
  private ProductListViewCustomizer customizer = AppClientFactory.Customizer.cast().getProductListViewCustomizer();

  private class ProductRow {
    int index;
    Articolo product;
    public ProductRow(int index, Articolo product) {
      super();
      this.index = index;
      this.product = product;
    }
  }
  
  private List<ProductRow> productRows = new ArrayList<ProductListViewImpl.ProductRow>();

  int ordinatiColIndex = 0;
  
  int ordinaColIndex = 0;
  
  @UiField Label headerLabel;
  @UiField FlexTable pTable;
  
  public ProductListViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    if (model instanceof String) {
      headerLabel.setText("Elenco prodotti " + model);
    } else if (model instanceof PortalSessionState) {
      //
    } else if (model instanceof List) {
      pTable.removeAllRows();
      int row = 0;
      int col = 0;
      if (customizer.showImage())
        pTable.setText(row, col++, "");
      pTable.setText(row, col++, "Prodotto");
      if (customizer.showPrezzo())
        pTable.setText(row, col++, "Prezzo");
      if (customizer.showUdM())
        pTable.setText(row, col++, "U.d.M.");
      if (customizer.showConfezione())
        pTable.setText(row, col++, "Conf.");
      if (customizer.showPezziOrdinati())
        pTable.setText(row, col++, "Ordinati");
      if (model instanceof ProductPageList) {
        ProductPageList productPageList = (ProductPageList)model;
        for (int it = 0; it < productPageList.size(); it++) {
          addProduct(it, productPageList.get(it).getEntity(), productPageList.get(it));
        }
      } else {
        List<Articolo> products = (List<Articolo>)model;
        for (int it = 0; it < products.size(); it++) {
          addProduct(it, products.get(it), null);
        }
      }
    } else if (model instanceof Order) {
      Order order = (Order)model;
      for (OrderItem item : order.getItems()) {
        for (ProductRow productRow : productRows) {
          if (productRow.product.equals(item.getProduct())) {
            if (customizer.showPezziOrdinati())
              pTable.setText(productRow.index, ordinatiColIndex, GwtUtils.formatCurrency(item.getQuantity()));
          }
        }
      }
    }
  }
  
  private void addProduct(int it, final Articolo product, final ProductPage page) {
    int row = it / NUMERO_COLONNE + 1;
    int col = it % NUMERO_COLONNE;
    
    if (customizer.showImage()) {
      Image smallImage = new Image(UrlUtils.getProductImageUrl(product.getId(), "small"));
      smallImage.setWidth("42px");
      smallImage.setHeight("42px");
      pTable.setWidget(row, col++, smallImage);
      GwtUtils.setFlexCellHeight(pTable, row, col - 1, "45px");
    }
    
    Anchor screenNameAnchor = new Anchor(product.getName());
    screenNameAnchor.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (page != null) {
          getPresenter().goToPage(page);
        } else {
          getPresenter().show(product);
        }
      }
    });
    pTable.setWidget(row, col++, screenNameAnchor);
    
    if (customizer.showPrezzo())
      pTable.setText(row, col++, GwtUtils.formatCurrency(product.getPrezzo() != null ? product.getPrezzo() : 0));
    if (customizer.showUdM())
      pTable.setText(row, col++, product.getUnitaDiMisura() != null ? product.getUnitaDiMisura().getNome() : "");
    if (customizer.showConfezione())
      pTable.setText(row, col++, product.getConfezione() != null ? product.getConfezione().toString() : "");
    
    ordinatiColIndex = col++;
    if (customizer.showPezziOrdinati())
      pTable.setText(row, ordinatiColIndex, "");
    
    ordinaColIndex = col++;
    
    /*
    ordinaColIndex = col++;
    if (portalSessionState != null && portalSessionState.getLoggedUser() != null && portalSessionState.getCustomer() != null) {
      addOrdinaAnchor(product, row, ordinaColIndex);
    }
    */

    productRows.add(new ProductRow(row, product));
    
  }
  
  private void addOrdinaAnchor(final Articolo product, int row, int col) {
    Anchor anchor = new Anchor("ordina");
    anchor.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        getPresenter().show(product);
      }
    });
    pTable.setWidget(row, col, anchor);
  }
  
}

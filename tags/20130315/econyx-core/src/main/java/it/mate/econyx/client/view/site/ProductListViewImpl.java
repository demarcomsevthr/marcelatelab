package it.mate.econyx.client.view.site;

import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

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
      String headerText = (String)model;
      if (headerText.equals("")) {
        headerLabel.setVisible(false);
        headerLabel.setHeight("0px");
      } else {
        headerLabel.setText("Elenco prodotti " + model);
      }
    } else if (model instanceof PortalSessionState) {
      //
    } else if (model instanceof List) {
      pTable.removeAllRows();
      int row = 0;
      int col = 0;
      if (PropertiesHolder.getBoolean("client.ProductListView.showImage", true))
        pTable.setText(row, col++, "");
      if (!PropertiesHolder.getString("client.ProductListView.prodottoHeader", "Prodotto").equals(""))
        pTable.setText(row, col++, PropertiesHolder.getString("client.ProductListView.prodottoHeader", "Prodotto"));
      if (PropertiesHolder.getBoolean("client.ProductListView.showPrezzo", true))
        pTable.setText(row, col++, "Prezzo");
      if (PropertiesHolder.getBoolean("client.ProductListView.showUdM", true))
        pTable.setText(row, col++, "U.d.M.");
      if (PropertiesHolder.getBoolean("client.ProductListView.showConfezione", true))
        pTable.setText(row, col++, "Conf.");
      if (PropertiesHolder.getBoolean("client.ProductListView.showPezziOrdinati", true))
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
            if (PropertiesHolder.getBoolean("client.ProductListView.showPezziOrdinati", true))
              pTable.setText(productRow.index, ordinatiColIndex, GwtUtils.formatCurrency(item.getQuantity()));
          }
        }
      }
    }
  }
  
  private void addProduct(int it, final Articolo product, final ProductPage page) {
    int row = it / NUMERO_COLONNE + 1;
    int col = it % NUMERO_COLONNE;
    
    if (PropertiesHolder.getBoolean("client.ProductListView.showImage", true)) {
      Image smallImage = new Image(UrlUtils.getProductImageUrl(product.getId(), "small"));
      /*
      smallImage.setWidth("42px");
      smallImage.setHeight("42px");
      */
      pTable.setWidget(row, col++, smallImage);
      /*
      GwtUtils.setFlexCellHeight(pTable, row, col - 1, "45px");
      */
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
    
    if (PropertiesHolder.getBoolean("client.ProductListView.showPrezzo", true))
      pTable.setText(row, col++, GwtUtils.formatCurrency(product.getPrezzo() != null ? product.getPrezzo() : 0));
    if (PropertiesHolder.getBoolean("client.ProductListView.showUdM", true))
      pTable.setText(row, col++, product.getUnitaDiMisura() != null ? product.getUnitaDiMisura().getNome() : "");
    if (PropertiesHolder.getBoolean("client.ProductListView.showConfezione", true))
      pTable.setText(row, col++, product.getConfezione() != null ? product.getConfezione().toString() : "");
    
    ordinatiColIndex = col++;
    if (PropertiesHolder.getBoolean("client.ProductListView.showPezziOrdinati", true))
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

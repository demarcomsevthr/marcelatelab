package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

public class PortalPageEditProductListView extends AbstractAdminTabPage<PortalPageEditView.Presenter> implements PortalPageEditView, IsAdminTabPage<PortalPageEditView.Presenter> {
  
  private ProductListView productListView;
  
  public PortalPageEditProductListView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(productListView.asWidget());
  }
  
  protected void initProvided() {
    productListView = new ProductListViewImpl("640px", "400px");
//  productListView = new ProductListViewImpl();
    
    productListView.setPresenter(new ProductListView.Presenter() {
      public BaseView getView() {
        return null;
      }
      public void goToPrevious() { }
      public void edit(Articolo product) {
        GwtUtils.log(getClass(), "edit", "pressed new product");
      }
      public void show(Articolo product) { }
      public void goToPage(ProductPage page) { }
    });
    
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof ProductFolderPage) {
      ProductFolderPage productFolderPage = (ProductFolderPage)model;
      if (productFolderPage.getChildreen() != null) {
        List<Articolo> products = new ArrayList<Articolo>();
        for (PortalPage childPage : productFolderPage.getChildreen()) {
          if (childPage instanceof ProductPage) {
            ProductPage productPage = (ProductPage)childPage;
            products.add(productPage.getEntity());
          }
        }
        productListView.setModel(products, null);
      }
    }
  }
  
  @Override
  protected void onDetach() {
    super.onDetach();
  }

  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    delegate.execute(model);
  }
  
}

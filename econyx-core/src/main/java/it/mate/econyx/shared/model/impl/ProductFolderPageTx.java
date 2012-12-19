package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.gwtcommons.shared.model.CloneableBean;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.ProductFolderPageDs")
public class ProductFolderPageTx extends PortalFolderPageTx implements ProductFolderPage {

  /*
  
  List<? extends ProductPage> products = new ArrayList<ProductPageTx>();

  private boolean productsResolved = false;
  
  
  public List<ProductPage> getProducts() {
    return (List<ProductPage>)products;
  }

  @CloneableProperty (targetClass=ProductPageTx.class)
  public void setProducts(List<ProductPage> products) {
    this.products = products;
  }
  
  public boolean getProductsResolved() {
    return productsResolved;
  }
  
  public void setProductsResolved(boolean productsResolved) {
    this.productsResolved = productsResolved;
  }
  
  */
  
  
}

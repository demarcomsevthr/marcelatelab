package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.impl.ProductFolderPageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.shared.model.CloneableBean;

import javax.jdo.annotations.PersistenceCapable;

@CacheableEntity (txClass=ProductFolderPageTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.ProductFolderPageTx")
public class ProductFolderPageDs extends AbstractPortalFolderPageDs implements ProductFolderPage {

  /*
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> productsKeys;
  @UnownedRelationship (key="productsKeys", itemClass=ProductPageDs.class)
  transient List<ProductPageDs> products;
  transient private boolean productsResolved = false;
  
  @Override
  public void onBeforeResolveRelationships() {
    super.onBeforeResolveRelationships();
    if (!productsResolved) {
      productsKeys = null;
    }
  }

  public void resolveProducts () {
    CollectionUtils.traverseCollection(productsKeys);
    productsResolved = true;
  }
  
  public boolean getProductsResolved() {
    return productsResolved;
  }
  
  public List<ProductPage> getProducts() {
    return products != null ? new ArrayList<ProductPage>(products) : null;
  }

  @CloneableProperty (targetClass=ProductPageDs.class)
  public void setProducts(List<ProductPage> products) {
    this.products = new ArrayList<ProductPageDs>();
    this.productsKeys = new ArrayList<Key>();
    if (products != null) {
      for (ProductPage product : products) {
        if (product instanceof ProductPageDs) {
          this.products.add((ProductPageDs)product);
          this.productsKeys.add(((ProductPageDs)product).getKey());
        } else {
          throw new IllegalArgumentException("cannot add item of type " + product.getClass() + ", you have to use CloneableProperty");
        }
      }
    }
  }
  
  */
  
}

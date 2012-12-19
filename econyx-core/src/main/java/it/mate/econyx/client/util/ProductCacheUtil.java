package it.mate.econyx.client.util;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.services.ProductServiceAsync;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProductCacheUtil {

  private static Map<String, Articolo> productsCache;
  
  private static ProductServiceAsync productService = AppClientFactory.IMPL.getGinjector().getProductService();
  
  public static void fetchHtmls(Articolo product, final Delegate<Articolo> delegate) {
    Articolo cachedProduct = getFromCache(product.getId());
    if (cachedProduct != null) {
      delegate.execute(cachedProduct);
    } else {
      productService.fetchHtmls(product, new AsyncCallback<Articolo>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Articolo product) {
          putInCache(product);
          delegate.execute(product);
        }
      });
    }
  }
  
  @SuppressWarnings("unchecked")
  private static void ensureCache() {
    String cacheAttrName = ProductCacheUtil.class.getName()+".productsCache";
    productsCache = (Map<String, Articolo>) GwtUtils.getClientAttribute(cacheAttrName);
    if (productsCache == null) {
      productsCache = new HashMap<String, Articolo>();
      GwtUtils.setClientAttribute(cacheAttrName, ProductCacheUtil.productsCache);
    }
  }
  
  private static void putInCache(Articolo articolo) {
    ensureCache();
    if (articolo == null)
      return;
    if (AppClientFactory.isSiteModule) {
      productsCache.put(articolo.getId(), articolo);
    }
  }
  
  private static Articolo getFromCache(String id) {
    ensureCache();
    if (id == null)
      return null;
    if (AppClientFactory.isSiteModule) {
      return productsCache.get(id);
    } else {
      return null;
    }
  }
  
}

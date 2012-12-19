package it.mate.econyx.client.view.site.custom;

import it.mate.econyx.client.view.custom.ProductListViewCustomizerDefaultImpl;

public class ProductListViewCustomizerImpl extends ProductListViewCustomizerDefaultImpl {

  public boolean showImage() {
    return true;
  }
  
  public boolean showPrezzo() {
    return false;
  }

  public boolean showUdM() {
    return false;
  }

  public boolean showConfezione() {
    return false;
  }

  public boolean showPezziOrdinati() {
    return false;
  }
  
  
}

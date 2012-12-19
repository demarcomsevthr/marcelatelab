package it.mate.econyx.client.view.custom;

public class ProductListViewCustomizerDefaultImpl implements ProductListViewCustomizer {

  public boolean showImage() {
    return false;
  }
  
  public boolean showPrezzo() {
    return true;
  }

  public boolean showUdM() {
    return true;
  }

  public boolean showConfezione() {
    return true;
  }

  public boolean showPezziOrdinati() {
    return true;
  }
  
}

package it.mate.onscommons.client.view;


public interface HasClosingViewHandler {
  
  public interface ClosingHandler {
    public void doClose();
  }
  
  public void onClosingView(ClosingHandler handler);

}

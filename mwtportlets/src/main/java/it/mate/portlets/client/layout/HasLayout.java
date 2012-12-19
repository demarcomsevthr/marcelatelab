package it.mate.portlets.client.layout;

public interface HasLayout extends Container {

  void setLayout(Layout layout);
  
  void doLayout(); 
  
}

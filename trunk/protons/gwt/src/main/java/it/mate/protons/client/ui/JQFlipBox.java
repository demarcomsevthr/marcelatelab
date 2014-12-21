package it.mate.protons.client.ui;

public class JQFlipBox extends JQDateBox {

  public JQFlipBox() {
    super();
  }
  
  @Override
  protected void initDataOptions() {
    super.initDataOptions();
    setDataOption("mode", "flipbox");
  }

}

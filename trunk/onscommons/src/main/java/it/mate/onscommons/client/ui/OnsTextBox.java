package it.mate.onscommons.client.ui;



public class OnsTextBox extends OnsTextBoxBase {
  
  public OnsTextBox() {
    super("text");
    addStyleName("text-input");
  }
  
  protected OnsTextBox(String type) {
    super(type);
  }

}

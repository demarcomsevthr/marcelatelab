package it.mate.gendtest.endpoints;

public class VoidResult {
  
  public final static VoidResult VOID = new VoidResult();
  
  private int dummy;

  public int getDummy() {
    return dummy;
  }

  public void setDummy(int dummy) {
    this.dummy = dummy;
  }
  
}

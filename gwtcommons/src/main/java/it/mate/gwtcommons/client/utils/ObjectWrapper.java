package it.mate.gwtcommons.client.utils;

public class ObjectWrapper <T> {
  
  T object;

  public T get() {
    return object;
  }

  public void set(T object) {
    this.object = object;
  }

}

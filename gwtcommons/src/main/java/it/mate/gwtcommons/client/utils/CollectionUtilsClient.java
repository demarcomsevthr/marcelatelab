package it.mate.gwtcommons.client.utils;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

public class CollectionUtilsClient {

  /**
   * ATTENZIONE
   * 
   * Il parametro itemInterfaceClass anche se potrebbe non essere dichiarato, serve altrimenti
   * il generatore gwt-rpc da problemi del tipo
   * Checking type argument 0 of type 'java.util.Arrays.ArrayList<E>'
   * 
   * SERVE ANCHE FARE IL CAST IN USCITA DEL METODO CHIAMANTE
   * 
   * vedi ArticleTx e ArticleFolderTx
   * 
   * (19/03/2013)
   */
  
  @SuppressWarnings("unchecked")
  public static <I extends Serializable, D extends I> List<I> wrapConcreteList(Class<I> itemInterfaceClass, final List<D> items, final Class<D> descendantClass) {
    if (items == null)
      return null;
    return new AbstractList<I>() {
      public int size() {
        return items.size();
      }
      public I get(int index) {
        return items.get(index);
      }
      public I set(int index, I element) {
        if (element == null) {
          throw new IllegalArgumentException("null");
        }
        if (isInstanceOf(element, descendantClass)) {
          return items.set(index, (D)element);
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(I element) {
        if (element == null) {
          throw new IllegalArgumentException("null");
        }
        if (isInstanceOf(element, descendantClass)) {
          return items.add((D)element);
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return items.remove(o);
      }
      public I remove(int index) {
        return items.remove(index);
      }
    };
  }
  
  @SuppressWarnings("rawtypes")
  private static boolean isInstanceOf(Object inst, Class<?> type) {
    if (inst.getClass().equals(type)) {
      return true;
    }
    Class supercl = inst.getClass().getSuperclass();
    while (supercl != null) {
      if (supercl.equals(type)) {
        return true;
      }
      supercl = supercl.getSuperclass();
      if (Object.class.equals(supercl))
        supercl = null;
    }
    return false;
  }
  
}

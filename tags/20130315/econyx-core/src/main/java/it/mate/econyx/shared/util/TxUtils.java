package it.mate.econyx.shared.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class TxUtils {
  
  public static <I, T extends I> List<I> getSuperclassList(final List<T> items) {
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
        return items.set(index, (T)element);
      }
      public boolean add(I element) {
        if (element == null) {
          throw new IllegalArgumentException("null");
        }
        return items.add((T)element);
      }
      public boolean remove(Object o) {
        return items.remove(o);
      }
      public I remove(int index) {
        return items.remove(index);
      }
    };
  }
  
  public static <I, T extends I> List<T> getSubclassList(List<I> items, Class<T> itemClass) {
    List<T> results = new ArrayList<T>();
    if (items != null) {
      for (I item : items) {
        try {
          results.add((T)item);
        } catch (ClassCastException ex) {
          throw new IllegalArgumentException("cannot add item of type " + item.getClass() + ", maybe missing CloneableProperty annotation");
        }
      }
    }
    return results;
  }
  

}

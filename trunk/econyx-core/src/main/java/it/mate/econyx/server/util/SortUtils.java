package it.mate.econyx.server.util;

import it.mate.econyx.shared.model.Articolo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtils {
  
  public static List<? extends Articolo> sortProductsByOrderNm(List<? extends Articolo> list) {
    if (list == null)
      return null;
    Collections.sort(list, new Comparator<Articolo>() {
      public int compare(Articolo o1, Articolo o2) {
        if (o1.getOrderNm() == null)
          return -1;
        return o1.getOrderNm().compareTo(o2.getOrderNm());
      }
    });
    return list;
  }

}

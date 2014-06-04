package it.mate.phgcommons.client.utils;

import java.util.Iterator;
import java.util.List;

public class IterationUtil <T> {
  
  private Iterator<T> it;
  private ItemDelegate<T> itemDelegate;
  private FinishDelegate finishDelegate;
  
  public IterationUtil(List<T> list, ItemDelegate<T> itemDelegate, FinishDelegate finishDelegate) {
    if (list == null)
      return;
    this.it = list.iterator();
    this.itemDelegate = itemDelegate;
    this.finishDelegate = finishDelegate;
    next();
  }

  public void next() {
    if (it.hasNext()) {
      T item = it.next();
      itemDelegate.handleItem(item, this);
    } else {
      finishDelegate.doFinish();
    }
  }
  
  public interface ItemDelegate <T> {
    public void handleItem(T item, final IterationUtil<T> iteration);
  }
  
  public interface FinishDelegate {
    public void doFinish();
  }
 

}

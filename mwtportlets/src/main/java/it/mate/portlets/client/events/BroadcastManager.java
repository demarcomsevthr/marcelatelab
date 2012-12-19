package it.mate.portlets.client.events;

import java.util.HashSet;
import java.util.Set;

public class BroadcastManager {

  private static BroadcastManager instance;
  private Set<BroadcastListener> listeners = new HashSet<BroadcastListener>();
  public static BroadcastManager get() {
      if (instance == null) {
          instance = new BroadcastManager();
      }
      return instance;
  }
  private BroadcastManager() {  }
  
  public void addBroadcastListener(BroadcastListener l) {
      if (listeners.contains(l)) {
          listeners.remove(l);
      }
      listeners.add(l);
  }
  
  public void removeBroadcastListener(BroadcastListener l) {
      listeners.remove(l);
  }
  
  public void broadcast(PageTemplateEvent ev) {
      for (BroadcastListener listener : listeners) {
          listener.onBroadcast(ev);
      }
  }
  
}

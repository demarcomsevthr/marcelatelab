package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.Produttore;

import java.util.List;

public interface ReportAdapter {

  public byte[] printOrder (Order order);
  
  public byte[] printOrdersToProducer (Produttore producer, List<Order> orders);
  
}

package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.Produttore;

import java.io.IOException;
import java.util.List;

public interface ExcelAdapter {
  
  public byte[] printOrdersToProducer (Produttore producer, List<Order> orders) throws IOException;

}

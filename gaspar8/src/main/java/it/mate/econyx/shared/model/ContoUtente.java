package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.List;

public interface ContoUtente extends Serializable {
  
  public String getId();

  public void setId(String id);

  public Customer getCustomer();
  
  public void setCustomer(Customer customer);
  
  public Double getSaldo();
  
  public void setSaldo(Double valore);
 
  public List<ContoUtenteMovimento> getMovimenti();
  
  public void setMovimenti(List<ContoUtenteMovimento> movimenti);
  
}

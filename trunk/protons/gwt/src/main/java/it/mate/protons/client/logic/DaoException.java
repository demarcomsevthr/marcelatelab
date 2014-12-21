package it.mate.protons.client.logic;

@SuppressWarnings("serial")
public class DaoException extends RuntimeException {

  public DaoException(String message) {
    super(message);
  }

  public DaoException(Throwable cause) {
    super(cause);
  }

  
}

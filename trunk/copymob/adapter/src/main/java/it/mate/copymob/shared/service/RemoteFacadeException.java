package it.mate.copymob.shared.service;

@SuppressWarnings("serial")
public class RemoteFacadeException extends Exception {

  public RemoteFacadeException() {
    super();
  }

  public RemoteFacadeException(String message, Throwable cause) {
    super(message, cause);
  }

  public RemoteFacadeException(String message) {
    super(message);
  }

}

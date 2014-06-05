package it.mate.therapyreminder.server.services;

@SuppressWarnings("serial")
public class RemoteAdapterException extends Exception {

  public RemoteAdapterException() {
    super();
  }

  public RemoteAdapterException(String message, Throwable cause) {
    super(message, cause);
  }

  public RemoteAdapterException(String message) {
    super(message);
  }

  public RemoteAdapterException(Throwable cause) {
    super(cause);
  }

}

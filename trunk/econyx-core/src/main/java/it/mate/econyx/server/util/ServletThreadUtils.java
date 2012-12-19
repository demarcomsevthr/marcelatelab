package it.mate.econyx.server.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletThreadUtils {
  
  private static ThreadLocal<HttpServletRequest> perThreadRequest;
  private static ThreadLocal<HttpServletResponse> perThreadResponse;
  
  private static void ensureThreadLocalData() {
    if (perThreadRequest == null) {
      perThreadRequest = new ThreadLocal<HttpServletRequest>();
    }
    if (perThreadResponse == null) {
      perThreadResponse = new ThreadLocal<HttpServletResponse>();
    }
  }
  
  public static synchronized void set(HttpServletRequest request, HttpServletResponse response) {
    ensureThreadLocalData();
    perThreadRequest.set(request);
    perThreadResponse.set(response);
  }

  public static synchronized void remove() {
    ensureThreadLocalData();
    perThreadRequest.remove();
    perThreadResponse.remove();
  }

  public static synchronized HttpServletRequest getRequest() {
    ensureThreadLocalData();
    return perThreadRequest.get();
  }

  public static synchronized HttpServletResponse getResponse() {
    ensureThreadLocalData();
    return perThreadResponse.get();
  }

}

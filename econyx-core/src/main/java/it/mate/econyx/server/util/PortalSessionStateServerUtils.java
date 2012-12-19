package it.mate.econyx.server.util;

import it.mate.econyx.shared.model.PortalSessionState;

import javax.servlet.http.HttpServletRequest;

public class PortalSessionStateServerUtils {
  
  private static ThreadLocal<PortalSessionState> perThreadPortalSessionState;
  
  private static final String PORTAL_SESSION_STATE_KEY = "PORTAL_SESSION_STATE";
  
  private static void ensureThreadLocalData() {
    if (perThreadPortalSessionState == null) {
      perThreadPortalSessionState = new ThreadLocal<PortalSessionState>();
    }
  }
  
  public static synchronized void setInThread(PortalSessionState state) {
    ensureThreadLocalData();
    perThreadPortalSessionState.set(state);
  }

  public static synchronized PortalSessionState getFromThread() {
    ensureThreadLocalData();
    return perThreadPortalSessionState.get();
  }
  
  public static PortalSessionState getFromSession(HttpServletRequest request) {
    return (PortalSessionState)request.getSession().getAttribute(PORTAL_SESSION_STATE_KEY);
  }

  public static void setInSession(HttpServletRequest request, PortalSessionState state) {
    request.getSession().setAttribute(PORTAL_SESSION_STATE_KEY, state);
  }

}

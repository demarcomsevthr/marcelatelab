package it.mate.phgcommons.client.plugins;

import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * 
 * SEE https://github.com/EddyVerbruggen/Calendar-PhoneGap-Plugin
 * 
 *
 */


public class CalendarPlugin {
  
  public static class Event {
    private String title;
    private String location = "";
    private String notes = "";
    private Date startDate;
    private Date endDate;
    @Override
    public String toString() {
      return "Event [title=" + title + ", location=" + location + ", notes=" + notes + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
    public String getLocation() {
      return location;
    }
    public void setLocation(String location) {
      this.location = location;
    }
    public String getNotes() {
      return notes;
    }
    public void setNotes(String notes) {
      this.notes = notes;
    }
    public Date getStartDate() {
      return startDate;
    }
    public void setStartDate(Date startDate) {
      this.startDate = startDate;
    }
    public Date getEndDate() {
      return endDate;
    }
    public void setEndDate(Date endDate) {
      this.endDate = endDate;
    }
  }
  
  
  public static void createEvent(Event event) {
    
    double startTime = event.getStartDate().getTime();
    double endTime = event.getEndDate().getTime();
    
    JSONUtils.ensureStringify();
    PhonegapUtils.log("creating event " + event);
    createEventImpl(event.getTitle(), event.getLocation(), event.getNotes(), startTime, endTime, new JSOCallback() {
      public void handleEvent(JavaScriptObject jso) {
        PhonegapUtils.log("Success - jso = " + JSONUtils.stringify(jso));
      }
    }, new JSOCallback() {
      public void handleEvent(JavaScriptObject jso) {
        PhonegapUtils.log("Failure - jso = " + JSONUtils.stringify(jso));
      }
    });
    
  }
  
  
  protected static interface JSOCallback {
    public void handleEvent(JavaScriptObject jso);
  }

  private static native void createEventImpl (String title, String location, String notes, double startTime, double endTime, JSOCallback success, JSOCallback failure) /*-{
    var jsSuccess = $entry(function(message) {
      success.@it.mate.phgcommons.client.plugins.CalendarPlugin.JSOCallback::handleEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(message);
    });
    var jsFailure = $entry(function(message) {
      failure.@it.mate.phgcommons.client.plugins.CalendarPlugin.JSOCallback::handleEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(message);
    });
    $wnd.cordova.exec(jsSuccess, jsFailure, "Calendar", "createEvent", [{
      "title": title,
      "location": location,
      "notes": notes,
      "startTime": startTime,
      "endTime": endTime
    }])
  }-*/;

}

package it.mate.therapyreminder.shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EndpointResponse implements Serializable {
  
  public final static int STATUS_CODE_OK = 1;
  
  public final static int STATUS_CODE_ERROR = -1;
  
  public final static EndpointResponse OK = new EndpointResponse(STATUS_CODE_OK);
  
  public final static EndpointResponse ERROR = new EndpointResponse(STATUS_CODE_ERROR);
  
  private int statusCode;
  
  private String payload;
  
  private String buildNumber;
  
  public EndpointResponse() {  }

  public EndpointResponse(int statusCode) {
    this.statusCode = statusCode;
  }

  public EndpointResponse(int statusCode, String payload) {
    this.statusCode = statusCode;
    this.payload = payload;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }
  
  public EndpointResponse setBuildNumber(String buildNumber) {
    this.buildNumber = buildNumber;
    return this;
  }
  
  public String getBuildNumber() {
    return buildNumber;
  }
  
}

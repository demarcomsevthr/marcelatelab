package it.mate.econyx.client.util;

import com.google.gwt.core.client.GWT;

public class UrlUtils extends it.mate.gwtcommons.client.utils.UrlUtils {
  
  private static long getVer() {
    return (System.currentTimeMillis() % 999999l);
  }
  
  public static String getUploadServletUrl() {
    return GWT.getHostPageBaseURL()+".upload";
  }
  
  public static String getProductImageUrl(String id, String size) {
    return GWT.getHostPageBaseURL()+".resource?op=PRI&productId="+id+"&imageSize="+size;
  }
  
  public static String getImageUrl(String code) {
    return GWT.getHostPageBaseURL()+getRelativeImageUrl(code);
  }
  
  public static String getRelativeImageUrl(String code) {
    return "re/im/"+code;
  }
  
  public static String getDocumentContentUrl(String code) {
    return "re/doc/"+code;
  }
  
}

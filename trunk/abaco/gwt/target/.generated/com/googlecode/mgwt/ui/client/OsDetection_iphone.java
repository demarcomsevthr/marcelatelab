package com.googlecode.mgwt.ui.client;

import com.googlecode.mgwt.ui.client.OsDetection;

public class OsDetection_iphone implements com.googlecode.mgwt.ui.client.OsDetection {
  public boolean isAndroid() {
  return isAndroidTablet() || isAndroidPhone();
  }
  public boolean isIPhone() {
  return true || false;
  }
  public boolean isIPad() {
  return false || false;
  }
  public boolean isIOs() {
  return isIPhone() || isIPad();
  }
  public boolean isDesktop() {
  return false;
  }
  public boolean isBlackBerry() {
  return false;
  }
  public boolean isTablet() {
  return isDesktop() || isIPad() || isAndroidTablet();
  }
  public boolean isPhone() {
  return isIPhone() || isAndroidPhone() || isBlackBerry();
  }
  public boolean isAndroidTablet() {
  return false;
  }
  public boolean isAndroidPhone() {
  return false;
  }
  public boolean isRetina() {
  return false;
  }
  public boolean isIPadRetina() {
  return false;
  }
}

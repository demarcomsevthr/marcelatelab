package it.mate.stickmail.shared.model;


import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface MyAutoBeanFactory extends AutoBeanFactory {

  AutoBean<StickMail> getStickMail();
  AutoBean<StickMail> getStickMail(StickMail towrap);
  
  AutoBean<ServerTime> getServerTime();
  AutoBean<ServerTime> getServerTime(ServerTime towrap);
  
}

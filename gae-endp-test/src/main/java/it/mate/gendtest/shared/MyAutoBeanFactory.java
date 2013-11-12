package it.mate.gendtest.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface MyAutoBeanFactory extends AutoBeanFactory {

  AutoBean<TestCommand> getTestCommand();
  AutoBean<TestCommand> getTestCommand(TestCommand towrap);
  
}

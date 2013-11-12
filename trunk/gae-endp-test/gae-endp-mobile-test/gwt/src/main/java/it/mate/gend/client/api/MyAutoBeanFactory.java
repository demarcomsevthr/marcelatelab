package it.mate.gend.client.api;

import it.mate.gendtest.shared.model.TestCommand;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface MyAutoBeanFactory extends AutoBeanFactory {

  AutoBean<TestCommand> getTestCommand();
  AutoBean<TestCommand> getTestCommand(TestCommand towrap);
  
}

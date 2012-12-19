package it.mate.portlets.server.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class PortletsNamespaceHandler extends NamespaceHandlerSupport {

  @Override
  public void init() {
    registerBeanDefinitionParser("factory", new FactoryBeanDefinitionParser());
    registerBeanDefinitionParser("constraint", new ConstraintBeanDefinitionParser());
    registerBeanDefinitionParser("layout", new LayoutBeanDefinitionParser());
  }
  
}

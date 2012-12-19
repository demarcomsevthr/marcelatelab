package it.mate.portlets.server.xml;

import it.mate.portlets.client.layout.LayoutConstraint;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ConstraintBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
  
  private static final String PARENT_ATTRIBUTE = "parent";
  
  private static final String SIZE_ATTRIBUTE = "size";
  
  private static final String OVERFLOW_ATTRIBUTE = "overflow";
  
  private boolean shouldGenerateId = false;
  
  @Override
  protected Class getBeanClass(Element element) {
    return LayoutConstraint.class;
  }
  
  @Override
  protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext)
      throws BeanDefinitionStoreException {
    this.shouldGenerateId = !element.hasAttribute("id");
    return super.resolveId(element, definition, parserContext);
  }
  
  @Override
  protected boolean shouldGenerateId() {
    return shouldGenerateId;
  }
  
  @Override
  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    if (element.hasAttribute(PARENT_ATTRIBUTE)) {
      String parentName = element.getAttribute(PARENT_ATTRIBUTE);
      builder.setParentName(parentName);
    }
    builder.addPropertyValue(SIZE_ATTRIBUTE, element.getAttribute(SIZE_ATTRIBUTE));
    builder.addPropertyValue(OVERFLOW_ATTRIBUTE, element.getAttribute(OVERFLOW_ATTRIBUTE));
  }
  
}

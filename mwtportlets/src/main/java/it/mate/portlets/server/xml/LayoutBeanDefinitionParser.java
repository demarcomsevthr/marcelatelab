package it.mate.portlets.server.xml;

import it.mate.portlets.client.layout.Layout;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class LayoutBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
  
  private static final String PARENT_ATTRIBUTE = "parent";
  
  private static final String VERTICAL_ATTRIBUTE = "vertical";
  
  private static final String WIDTH_ATTRIBUTE = "width";
  
  private static final String HEIGHT_ATTRIBUTE = "height";
  
  private static final String STYLENAME_ATTRIBUTE = "stylename";
  
  private static final String STYLE_ATTRIBUTE = "style";
  
  private boolean shouldGenerateId = false;
  
  @Override
  protected Class getBeanClass(Element element) {
    return Layout.class;
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
    builder.addPropertyValue(VERTICAL_ATTRIBUTE, Boolean.valueOf(element.getAttribute(VERTICAL_ATTRIBUTE)));
    builder.addPropertyValue(WIDTH_ATTRIBUTE, element.getAttribute(WIDTH_ATTRIBUTE));
    builder.addPropertyValue(HEIGHT_ATTRIBUTE, element.getAttribute(HEIGHT_ATTRIBUTE));
    builder.addPropertyValue(STYLENAME_ATTRIBUTE, element.getAttribute(STYLENAME_ATTRIBUTE));
    builder.addPropertyValue(STYLE_ATTRIBUTE, element.getAttribute(STYLE_ATTRIBUTE));
  }

}

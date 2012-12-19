package it.mate.portlets.server.xml;

import it.mate.portlets.client.AbstractWidgetFactory;
import it.mate.portlets.client.layout.LayoutConstraint;
import it.mate.portlets.server.util.WebClassUtils;
import it.mate.portlets.shared.util.StringUtils;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@SuppressWarnings("rawtypes")
public class FactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
  
  private static Logger logger = Logger.getLogger(FactoryBeanDefinitionParser.class);
  
  private static final String PARENT_ATTRIBUTE = "parent";
  
  private static final String STYLENAME_ATTRIBUTE = "stylename";
  
  private static final String TITLE_ATTRIBUTE = "title";
  
  private static final String LAYOUT_ELEMENT = "layout";
  
  private static final String WIDGETS_ELEMENT = "widgets";
  
  private static final String FACTORY_ELEMENT = "factory";
  
  private static final String CONSTRAINTS_ELEMENT = "constraints";
  
  private static final String CONSTRAINT_ELEMENT = "constraint";
  
  private boolean shouldGenerateId = false;
  
  protected Class getBeanClass(Element element) {
    String type = null;
    String parent = null;
    try {
      type = element.getAttribute("type");
      parent = element.getAttribute("parent");
      if (StringUtils.isSet(parent) && !StringUtils.isSet(type)) {
        return null;
      }
      for (ClassLoader classLoader : WebClassUtils.getWebClassLoaders()) {
        try {
          return ClassUtils.forName(type, classLoader);
        } catch (ClassNotFoundException ex) { }
      }
      for (Class wfClass : WebClassUtils.getWidgetFactoryClasses()) {
        if (wfClass.getName().equals(type)) {
          return wfClass;
        }
      }
      return ClassUtils.forName(type, PortletsNamespaceHandler.class.getClassLoader());
    } catch (ClassNotFoundException ex) {
      logger.error("error getting bean for type " + type, ex);
      return null;
    }
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
  protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
    if (element.hasAttribute(PARENT_ATTRIBUTE)) {
      String parentName = element.getAttribute(PARENT_ATTRIBUTE);
      builder.setParentName(parentName);
    }
    builder.addPropertyValue(STYLENAME_ATTRIBUTE, element.getAttribute(STYLENAME_ATTRIBUTE));
    if (element.hasAttribute(TITLE_ATTRIBUTE)) {
      builder.addPropertyValue(TITLE_ATTRIBUTE, element.getAttribute(TITLE_ATTRIBUTE));
    }
    parseLayoutElement(element, parserContext, builder);
    parseConstraintsElement(element, parserContext, builder);
    parseWidgetsElement(element, parserContext, builder);
    parserContext.getDelegate().parsePropertyElements(element, builder.getBeanDefinition());
  }
  
  private void parseLayoutElement(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
    LayoutBeanDefinitionParser layoutParser = new LayoutBeanDefinitionParser();
    Element layoutElem = DomUtils.getChildElementByTagName(element, LAYOUT_ELEMENT);
    if (layoutElem != null) {
      BeanDefinition layout = layoutParser.parse(layoutElem, parserContext);
      builder.addPropertyValue(LAYOUT_ELEMENT, layout);
    }
  }
  
  private void parseWidgetsElement(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
    FactoryBeanDefinitionParser nestedFactoryParser = new FactoryBeanDefinitionParser();
    Element widgetsElem = DomUtils.getChildElementByTagName(element, WIDGETS_ELEMENT);
    if (widgetsElem != null) {
      List<Element> factoryElements = DomUtils.getChildElementsByTagName(widgetsElem, FACTORY_ELEMENT);
      if (factoryElements != null) {
        builder.addPropertyValue(WIDGETS_ELEMENT, parseFactoryElementList(nestedFactoryParser, factoryElements, parserContext));
      }
    }
  }
  
  private Object parseFactoryElementList(FactoryBeanDefinitionParser parser, List<Element> factoryElements, ParserContext parserContext) {
    String elementType = AbstractWidgetFactory.class.getName();
    ManagedArray factoriesDefArray = new ManagedArray(elementType, factoryElements.size());
    factoriesDefArray.setElementTypeName(elementType);
    factoriesDefArray.setMergeEnabled(false);
    factoriesDefArray.setMergeEnabled(true);
    for (int it = 0; it < factoryElements.size(); it++) {
      Element factoryElem = factoryElements.get(it);
      factoriesDefArray.add(parser.parse(factoryElem, parserContext));
    }
    return factoriesDefArray;
  }
  
  private void parseConstraintsElement(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
    Element constraintsElem = DomUtils.getChildElementByTagName(element, CONSTRAINTS_ELEMENT);
    if (constraintsElem != null) {
      ConstraintBeanDefinitionParser parser = new ConstraintBeanDefinitionParser();
      List<Element> constraintElements = DomUtils.getChildElementsByTagName(constraintsElem, CONSTRAINT_ELEMENT);
      if (constraintElements != null) {
        builder.addPropertyValue(CONSTRAINTS_ELEMENT, parseConstraintElementList(parser, constraintElements, parserContext));
      }
    }
  }
  
  private Object parseConstraintElementList(ConstraintBeanDefinitionParser parser, List<Element> constraintElements, ParserContext parserContext) {
    String elementType = LayoutConstraint.class.getName();
    ManagedArray constraintsDefArray = new ManagedArray(elementType, constraintElements.size());
    constraintsDefArray.setElementTypeName(elementType);
    constraintsDefArray.setMergeEnabled(false);
    for (int it = 0; it < constraintElements.size(); it++) {
      Element constraintElem = constraintElements.get(it);
      constraintsDefArray.add(parser.parse(constraintElem, parserContext));
    }
    return constraintsDefArray;
  }
  
}

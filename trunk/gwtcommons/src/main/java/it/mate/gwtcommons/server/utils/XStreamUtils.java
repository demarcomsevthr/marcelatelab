package it.mate.gwtcommons.server.utils;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

public class XStreamUtils {
  
  private XStreamAppengine xstream;
  
  private static XStreamUtils INSTANCE = null;
  
  private static XStreamUtils getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new XStreamUtils();
      INSTANCE.xstream = new XStreamAppengine(new PureJavaReflectionProvider());
//    INSTANCE.xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
    }
    return INSTANCE;
  }
  
  public static XStream getXStream() {
    return getInstance().xstream;
  }
  
  public static String parseGraph(Object graph) {
    String result = getInstance().xstream.toXML(graph);
    return result;
  }
  
  public static Object buildGraph(File xmlFile) {
    Object graph = getInstance().xstream.fromXML(xmlFile);
    return graph;
  }
  
  public static Object buildGraph(String xml) {
    Object graph = getInstance().xstream.fromXML(xml);
    return graph;
  }
  
  public static void registerConverter(Converter converter) {
    getInstance().xstream.registerConverter(converter);
  }

}

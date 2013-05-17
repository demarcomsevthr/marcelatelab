package it.mate.gwtcommons.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;

public class XStreamUtils {
  
  private XStreamAppengine xstream;
  
  private static XStreamUtils INSTANCE = null;
  
  private static HierarchicalStreamDriver driver;
  
  public static void setDriver(HierarchicalStreamDriver driver) {
    XStreamUtils.driver = driver;
  }
  
  private static XStreamUtils getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new XStreamUtils();
      
      if (driver != null) {
        INSTANCE.xstream = new XStreamAppengine(new PureJavaReflectionProvider(), driver);
      } else {
        INSTANCE.xstream = new XStreamAppengine(new PureJavaReflectionProvider());
      }
      
    }
    return INSTANCE;
  }
  
  public static XStream getXStream() {
    return getInstance().xstream;
  }
  
  public static String parseGraph(Object graph) {
    return parseGraph(graph, null);
  }
  
  public static String parseGraph(Object graph, String encoding) {
    String xml = null;
    if (encoding != null) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
        Writer writer = new OutputStreamWriter(baos, encoding);
        getInstance().xstream.toXML(graph, writer);
        xml = baos.toString(encoding);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      xml = getInstance().xstream.toXML(graph);
    }
    return xml;
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

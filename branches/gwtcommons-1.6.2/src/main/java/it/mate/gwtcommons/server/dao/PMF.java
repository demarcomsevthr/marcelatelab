package it.mate.gwtcommons.server.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF {

  private static final PersistenceManagerFactory instance;
  
  static {
    instance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
//  instance = JDOHelper.getPersistenceManagerFactory("transactions-optional", PMF.class.getClassLoader());
  }
  
  private PMF() {}
  
  public static PersistenceManagerFactory get() {
    return instance;
  }
  
}

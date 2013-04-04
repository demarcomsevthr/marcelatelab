package it.mate.gwtcommons.server.dao;

public class ParameterDefinition {
  protected Class<?> type;
  protected String name;
  public ParameterDefinition(Class<?> type, String name) {
    super();
    this.type = type;
    this.name = name;
  }
}

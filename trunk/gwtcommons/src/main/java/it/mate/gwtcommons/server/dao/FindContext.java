package it.mate.gwtcommons.server.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FindContext <E extends Serializable> {
  private Class<E> entityClass;
  private boolean resultAsList = false;
  private Serializable id;
  private String filter;
  private String parameters;
  private Object[] paramValues;
  private FindCallback<E> callback;
  private boolean cacheDisabled = false;
  private List<String> includedFields = new ArrayList<String>();
  private List<String> excludedFields = new ArrayList<String>();
  private boolean relationshipsResolutionDisabled = false;
  private String whereClause;
  
  private boolean keysOnly = false;
  
  public FindContext(Class<E> entityClass) {
    super();
    this.entityClass = entityClass;
  }
  
  public FindContext<E> setEntityClass(Class<E> entityClass) {
    this.entityClass = entityClass;
    return this;
  }
  public Class<E> getEntityClass() {
    return entityClass;
  }
  public boolean isResultAsList() {
    return resultAsList;
  }
  public FindContext<E> setResultAsList(boolean resultAsList) {
    this.resultAsList = resultAsList;
    return this;
  }
  public Serializable getId() {
    return id;
  }
  public FindContext<E> setId(Serializable id) {
    this.id = id;
    return this;
  }
  public String getFilter() {
    return filter;
  }
  public FindContext<E> setFilter(String filter) {
    this.filter = filter;
    return this;
  }
  public String getParameters() {
    return parameters;
  }
  public FindContext<E> setParameters(String parameters) {
    this.parameters = parameters;
    return this;
  }
  public Object[] getParamValues() {
    return paramValues;
  }
  public FindContext<E> setParamValues(Object[] paramValues) {
    this.paramValues = paramValues;
    return this;
  }
  public FindCallback<E> getCallback() {
    return callback;
  }
  public FindContext<E> setCallback(FindCallback<E> callback) {
    this.callback = callback;
    return this;
  }
  public boolean cacheDisabled() {
    return cacheDisabled;
  }
  public FindContext<E> setCacheDisabled(boolean cacheDisabled) {
    this.cacheDisabled = cacheDisabled;
    return this;
  }
  public FindContext<E> includedField(String name) {
    this.includedFields.add(name);
    return this;
  }
  public List<String> getIncludedFields() {
    return includedFields;
  }
  public FindContext<E> excludedField(String name) {
    this.excludedFields.add(name);
    return this;
  }
  public List<String> getExcludedFields() {
    return excludedFields;
  }
  public boolean isRelationshipsResolutionDisabled() {
    return relationshipsResolutionDisabled;
  }
  public FindContext<E> setRelationshipsResolutionDisabled(boolean disabledRelationshipsResolution) {
    this.relationshipsResolutionDisabled = disabledRelationshipsResolution;
    return this;
  }
  public FindContext<E> setKeysOnly(boolean keysOnly) {
    this.keysOnly = keysOnly;
    return this;
  }
  public boolean keysOnly() {
    return keysOnly;
  }
  public String getWhereClause() {
    return whereClause;
  }
  public FindContext<E> whereClause(String whereClause) {
    this.whereClause = whereClause;
    return this;
  }
}

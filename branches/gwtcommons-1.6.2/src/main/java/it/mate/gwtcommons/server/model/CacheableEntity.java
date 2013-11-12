package it.mate.gwtcommons.server.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target (ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheableEntity {
  
  public Class txClass();
  
  public boolean instanceCache() default false;
  
}
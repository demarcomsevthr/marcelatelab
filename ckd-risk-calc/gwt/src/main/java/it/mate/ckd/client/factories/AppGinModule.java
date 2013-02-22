package it.mate.ckd.client.factories;

import it.mate.ckd.client.activities.mapper.MainActivityMapper;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class AppGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    
  }
  
  @Provides
  MainActivityMapper getMainActivityMapper() {
    return new MainActivityMapper(AppClientFactory.IMPL);
  }
  
}

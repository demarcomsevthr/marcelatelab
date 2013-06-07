package it.mate.quilook.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AdminGinModule.class, AppGinModule.class})
public interface AdminGinjector extends AppGinjector {

}

package it.mate.postscriptum.client.factories;

import com.google.gwt.core.client.GWT;

public class AppGinjectorImpl implements it.mate.postscriptum.client.factories.AppGinjector {
  public com.google.web.bindery.event.shared.EventBus getBinderyEventBus() {
    return get_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$();
  }
  
  public com.google.gwt.event.shared.EventBus getEventBus() {
    return get_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$();
  }
  
  public it.mate.postscriptum.client.view.HomeView getHomeView() {
    return get_Key$type$it$mate$postscriptum$client$view$HomeView$_annotation$$none$$();
  }
  
  public it.mate.postscriptum.client.view.MailListView getMailListView() {
    return get_Key$type$it$mate$postscriptum$client$view$MailListView$_annotation$$none$$();
  }
  
  public it.mate.postscriptum.client.activities.mapper.MainActivityMapper getMainActivityMapper() {
    return get_Key$type$it$mate$postscriptum$client$activities$mapper$MainActivityMapper$_annotation$$none$$();
  }
  
  public it.mate.postscriptum.client.view.NewMailView getNewMailView() {
    return get_Key$type$it$mate$postscriptum$client$view$NewMailView$_annotation$$none$$();
  }
  
  public com.google.gwt.place.shared.PlaceController getPlaceController() {
    return get_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$();
  }
  
  public it.mate.postscriptum.shared.service.StickFacadeAsync getStickFacade() {
    return get_Key$type$it$mate$postscriptum$shared$service$StickFacadeAsync$_annotation$$none$$();
  }
  
  
  /**
   * Binding for com.google.gwt.event.shared.EventBus declared at:
   *   it.mate.gwtcommons.client.factories.CommonGinModule.configure(CommonGinModule.java:15)
   */
  private com.google.gwt.event.shared.EventBus create_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$() {
    return get_Key$type$it$mate$gwtcommons$client$history$LoggedSimpleEventBus$_annotation$$none$$();
  }
  
  private com.google.gwt.event.shared.EventBus singleton_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$ = null;
  
  
  /**
   * Singleton bound at:
   *   it.mate.gwtcommons.client.factories.CommonGinModule.configure(CommonGinModule.java:15)
   */
  private com.google.gwt.event.shared.EventBus get_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$() {
    if (singleton_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$ == null) {
      singleton_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$ = create_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$();
    }
    return singleton_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$;
  }
  
  
  /**
   * Binding for it.mate.postscriptum.client.view.NewMailView declared at:
   *   Implicit binding for Key[type=it.mate.postscriptum.client.view.NewMailView, annotation=[none]]
   */
  private void memberInject_Key$type$it$mate$postscriptum$client$view$NewMailView$_annotation$$none$$(it.mate.postscriptum.client.view.NewMailView injectee) {
    
  }
  
  private it.mate.postscriptum.client.view.NewMailView create_Key$type$it$mate$postscriptum$client$view$NewMailView$_annotation$$none$$() {
    Object created = GWT.create(it.mate.postscriptum.client.view.NewMailView.class);
    assert created instanceof it.mate.postscriptum.client.view.NewMailView;
    it.mate.postscriptum.client.view.NewMailView result = (it.mate.postscriptum.client.view.NewMailView) created;
    
    memberInject_Key$type$it$mate$postscriptum$client$view$NewMailView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for it.mate.postscriptum.client.view.NewMailView declared at:
   *   Implicit binding for Key[type=it.mate.postscriptum.client.view.NewMailView, annotation=[none]]
   */
  private it.mate.postscriptum.client.view.NewMailView get_Key$type$it$mate$postscriptum$client$view$NewMailView$_annotation$$none$$() {
    return create_Key$type$it$mate$postscriptum$client$view$NewMailView$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for it.mate.gwtcommons.client.history.LoggedSimpleEventBus declared at:
   *   Implicit binding for Key[type=it.mate.gwtcommons.client.history.LoggedSimpleEventBus, annotation=[none]]
   */
  private void memberInject_Key$type$it$mate$gwtcommons$client$history$LoggedSimpleEventBus$_annotation$$none$$(it.mate.gwtcommons.client.history.LoggedSimpleEventBus injectee) {
    
  }
  
  private it.mate.gwtcommons.client.history.LoggedSimpleEventBus create_Key$type$it$mate$gwtcommons$client$history$LoggedSimpleEventBus$_annotation$$none$$() {
    Object created = GWT.create(it.mate.gwtcommons.client.history.LoggedSimpleEventBus.class);
    assert created instanceof it.mate.gwtcommons.client.history.LoggedSimpleEventBus;
    it.mate.gwtcommons.client.history.LoggedSimpleEventBus result = (it.mate.gwtcommons.client.history.LoggedSimpleEventBus) created;
    
    memberInject_Key$type$it$mate$gwtcommons$client$history$LoggedSimpleEventBus$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for it.mate.gwtcommons.client.history.LoggedSimpleEventBus declared at:
   *   Implicit binding for Key[type=it.mate.gwtcommons.client.history.LoggedSimpleEventBus, annotation=[none]]
   */
  private it.mate.gwtcommons.client.history.LoggedSimpleEventBus get_Key$type$it$mate$gwtcommons$client$history$LoggedSimpleEventBus$_annotation$$none$$() {
    return create_Key$type$it$mate$gwtcommons$client$history$LoggedSimpleEventBus$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.google.web.bindery.event.shared.SimpleEventBus declared at:
   *   Implicit binding for Key[type=com.google.web.bindery.event.shared.SimpleEventBus, annotation=[none]]
   */
  private void memberInject_Key$type$com$google$web$bindery$event$shared$SimpleEventBus$_annotation$$none$$(com.google.web.bindery.event.shared.SimpleEventBus injectee) {
    
  }
  
  private com.google.web.bindery.event.shared.SimpleEventBus create_Key$type$com$google$web$bindery$event$shared$SimpleEventBus$_annotation$$none$$() {
    Object created = GWT.create(com.google.web.bindery.event.shared.SimpleEventBus.class);
    assert created instanceof com.google.web.bindery.event.shared.SimpleEventBus;
    com.google.web.bindery.event.shared.SimpleEventBus result = (com.google.web.bindery.event.shared.SimpleEventBus) created;
    
    memberInject_Key$type$com$google$web$bindery$event$shared$SimpleEventBus$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.google.web.bindery.event.shared.SimpleEventBus declared at:
   *   Implicit binding for Key[type=com.google.web.bindery.event.shared.SimpleEventBus, annotation=[none]]
   */
  private com.google.web.bindery.event.shared.SimpleEventBus get_Key$type$com$google$web$bindery$event$shared$SimpleEventBus$_annotation$$none$$() {
    return create_Key$type$com$google$web$bindery$event$shared$SimpleEventBus$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for it.mate.postscriptum.client.activities.mapper.MainActivityMapper declared at:
   *   it.mate.postscriptum.client.activities.mapper.MainActivityMapper it.mate.postscriptum.client.factories.AppGinModule.getMainActivityMapper()
   */
  private native it.mate.postscriptum.client.activities.mapper.MainActivityMapper it$mate$postscriptum$client$factories$AppGinModule_getMainActivityMapper_methodInjection(it.mate.postscriptum.client.factories.AppGinModule injectee) /*-{
    return injectee.@it.mate.postscriptum.client.factories.AppGinModule::getMainActivityMapper()();
  }-*/;
  
  private it.mate.postscriptum.client.activities.mapper.MainActivityMapper create_Key$type$it$mate$postscriptum$client$activities$mapper$MainActivityMapper$_annotation$$none$$() {
    return it$mate$postscriptum$client$factories$AppGinModule_getMainActivityMapper_methodInjection(new it.mate.postscriptum.client.factories.AppGinModule());
  }
  
  
  /**
   * Binding for it.mate.postscriptum.client.activities.mapper.MainActivityMapper declared at:
   *   it.mate.postscriptum.client.activities.mapper.MainActivityMapper it.mate.postscriptum.client.factories.AppGinModule.getMainActivityMapper()
   */
  private it.mate.postscriptum.client.activities.mapper.MainActivityMapper get_Key$type$it$mate$postscriptum$client$activities$mapper$MainActivityMapper$_annotation$$none$$() {
    return create_Key$type$it$mate$postscriptum$client$activities$mapper$MainActivityMapper$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for it.mate.postscriptum.client.view.MailListView declared at:
   *   Implicit binding for Key[type=it.mate.postscriptum.client.view.MailListView, annotation=[none]]
   */
  private void memberInject_Key$type$it$mate$postscriptum$client$view$MailListView$_annotation$$none$$(it.mate.postscriptum.client.view.MailListView injectee) {
    
  }
  
  private it.mate.postscriptum.client.view.MailListView create_Key$type$it$mate$postscriptum$client$view$MailListView$_annotation$$none$$() {
    Object created = GWT.create(it.mate.postscriptum.client.view.MailListView.class);
    assert created instanceof it.mate.postscriptum.client.view.MailListView;
    it.mate.postscriptum.client.view.MailListView result = (it.mate.postscriptum.client.view.MailListView) created;
    
    memberInject_Key$type$it$mate$postscriptum$client$view$MailListView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for it.mate.postscriptum.client.view.MailListView declared at:
   *   Implicit binding for Key[type=it.mate.postscriptum.client.view.MailListView, annotation=[none]]
   */
  private it.mate.postscriptum.client.view.MailListView get_Key$type$it$mate$postscriptum$client$view$MailListView$_annotation$$none$$() {
    return create_Key$type$it$mate$postscriptum$client$view$MailListView$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.google.web.bindery.event.shared.EventBus declared at:
   *   it.mate.gwtcommons.client.factories.CommonGinModule.configure(CommonGinModule.java:19)
   */
  private com.google.web.bindery.event.shared.EventBus create_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$() {
    return get_Key$type$com$google$web$bindery$event$shared$SimpleEventBus$_annotation$$none$$();
  }
  
  private com.google.web.bindery.event.shared.EventBus singleton_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$ = null;
  
  
  /**
   * Singleton bound at:
   *   it.mate.gwtcommons.client.factories.CommonGinModule.configure(CommonGinModule.java:19)
   */
  private com.google.web.bindery.event.shared.EventBus get_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$() {
    if (singleton_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$ == null) {
      singleton_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$ = create_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$();
    }
    return singleton_Key$type$com$google$web$bindery$event$shared$EventBus$_annotation$$none$$;
  }
  
  
  /**
   * Binding for it.mate.postscriptum.shared.service.StickFacadeAsync declared at:
   *   public it.mate.postscriptum.shared.service.StickFacadeAsync it.mate.postscriptum.client.factories.AppGinModule.getStickFacade()
   */
  private it.mate.postscriptum.shared.service.StickFacadeAsync it$mate$postscriptum$client$factories$AppGinModule_getStickFacade_methodInjection(it.mate.postscriptum.client.factories.AppGinModule injectee) {
    return injectee.getStickFacade();
  }
  
  private it.mate.postscriptum.shared.service.StickFacadeAsync create_Key$type$it$mate$postscriptum$shared$service$StickFacadeAsync$_annotation$$none$$() {
    return it$mate$postscriptum$client$factories$AppGinModule_getStickFacade_methodInjection(new it.mate.postscriptum.client.factories.AppGinModule());
  }
  
  
  /**
   * Binding for it.mate.postscriptum.shared.service.StickFacadeAsync declared at:
   *   public it.mate.postscriptum.shared.service.StickFacadeAsync it.mate.postscriptum.client.factories.AppGinModule.getStickFacade()
   */
  private it.mate.postscriptum.shared.service.StickFacadeAsync get_Key$type$it$mate$postscriptum$shared$service$StickFacadeAsync$_annotation$$none$$() {
    return create_Key$type$it$mate$postscriptum$shared$service$StickFacadeAsync$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for it.mate.postscriptum.client.view.HomeView declared at:
   *   Implicit binding for Key[type=it.mate.postscriptum.client.view.HomeView, annotation=[none]]
   */
  private void memberInject_Key$type$it$mate$postscriptum$client$view$HomeView$_annotation$$none$$(it.mate.postscriptum.client.view.HomeView injectee) {
    
  }
  
  private it.mate.postscriptum.client.view.HomeView create_Key$type$it$mate$postscriptum$client$view$HomeView$_annotation$$none$$() {
    Object created = GWT.create(it.mate.postscriptum.client.view.HomeView.class);
    assert created instanceof it.mate.postscriptum.client.view.HomeView;
    it.mate.postscriptum.client.view.HomeView result = (it.mate.postscriptum.client.view.HomeView) created;
    
    memberInject_Key$type$it$mate$postscriptum$client$view$HomeView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for it.mate.postscriptum.client.view.HomeView declared at:
   *   Implicit binding for Key[type=it.mate.postscriptum.client.view.HomeView, annotation=[none]]
   */
  private it.mate.postscriptum.client.view.HomeView get_Key$type$it$mate$postscriptum$client$view$HomeView$_annotation$$none$$() {
    return create_Key$type$it$mate$postscriptum$client$view$HomeView$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.google.gwt.place.shared.PlaceController declared at:
   *   it.mate.gwtcommons.client.factories.CommonGinModule.configure(CommonGinModule.java:17)
   */
  private com.google.gwt.place.shared.PlaceController create_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$() {
    return get_Key$type$it$mate$gwtcommons$client$factories$InjectedPlaceController$_annotation$$none$$();
  }
  
  private com.google.gwt.place.shared.PlaceController singleton_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$ = null;
  
  
  /**
   * Singleton bound at:
   *   it.mate.gwtcommons.client.factories.CommonGinModule.configure(CommonGinModule.java:17)
   */
  private com.google.gwt.place.shared.PlaceController get_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$() {
    if (singleton_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$ == null) {
      singleton_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$ = create_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$();
    }
    return singleton_Key$type$com$google$gwt$place$shared$PlaceController$_annotation$$none$$;
  }
  
  
  /**
   * Binding for it.mate.gwtcommons.client.factories.InjectedPlaceController declared at:
   *   Implicit binding for Key[type=it.mate.gwtcommons.client.factories.InjectedPlaceController, annotation=[none]]
   */
  private void memberInject_Key$type$it$mate$gwtcommons$client$factories$InjectedPlaceController$_annotation$$none$$(it.mate.gwtcommons.client.factories.InjectedPlaceController injectee) {
    
  }
  
  private it.mate.gwtcommons.client.factories.InjectedPlaceController it$mate$gwtcommons$client$factories$InjectedPlaceController_InjectedPlaceController_methodInjection(com.google.gwt.event.shared.EventBus _0) {
    return new it.mate.gwtcommons.client.factories.InjectedPlaceController(_0);
  }
  
  private it.mate.gwtcommons.client.factories.InjectedPlaceController create_Key$type$it$mate$gwtcommons$client$factories$InjectedPlaceController$_annotation$$none$$() {
    it.mate.gwtcommons.client.factories.InjectedPlaceController result = it$mate$gwtcommons$client$factories$InjectedPlaceController_InjectedPlaceController_methodInjection(get_Key$type$com$google$gwt$event$shared$EventBus$_annotation$$none$$());
    memberInject_Key$type$it$mate$gwtcommons$client$factories$InjectedPlaceController$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for it.mate.gwtcommons.client.factories.InjectedPlaceController declared at:
   *   Implicit binding for Key[type=it.mate.gwtcommons.client.factories.InjectedPlaceController, annotation=[none]]
   */
  private it.mate.gwtcommons.client.factories.InjectedPlaceController get_Key$type$it$mate$gwtcommons$client$factories$InjectedPlaceController$_annotation$$none$$() {
    return create_Key$type$it$mate$gwtcommons$client$factories$InjectedPlaceController$_annotation$$none$$();
  }
  
  
  public AppGinjectorImpl() {
    
  }
  
}

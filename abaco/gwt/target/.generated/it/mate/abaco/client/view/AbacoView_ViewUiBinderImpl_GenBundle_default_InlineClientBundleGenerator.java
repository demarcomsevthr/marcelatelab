package it.mate.abaco.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class AbacoView_ViewUiBinderImpl_GenBundle_default_InlineClientBundleGenerator implements it.mate.abaco.client.view.AbacoView_ViewUiBinderImpl_GenBundle {
  private static AbacoView_ViewUiBinderImpl_GenBundle_default_InlineClientBundleGenerator _instance0 = new AbacoView_ViewUiBinderImpl_GenBundle_default_InlineClientBundleGenerator();
  private void styleInitializer() {
    style = new it.mate.abaco.client.view.AbacoView_ViewUiBinderImpl_GenCss_style() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "style";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GOE-WOTBCR{padding-top:" + ("1em")  + ";padding-right:" + ("1em")  + ";background-color:" + ("C0C0FE")  + ";}.GOE-WOTBCR *{font-size:" + ("16px")  + ";vertical-align:" + ("middle")  + ";}.GOE-WOTBCR td{padding-top:" + ("0.1em")  + ";}.GOE-WOTBCR button{font-size:" + ("16px")  + ";font-weight:" + ("bolder")  + ";}.boxSpacer{width:" + ("0.5em")  + " !important;}.GOE-WOTBDR{background-color:" + ("red")  + ";}.GOE-WOTBER{font-size:") + (("8px")  + ";}")) : ((".GOE-WOTBCR{padding-top:" + ("1em")  + ";padding-left:" + ("1em")  + ";background-color:" + ("C0C0FE")  + ";}.GOE-WOTBCR *{font-size:" + ("16px")  + ";vertical-align:" + ("middle")  + ";}.GOE-WOTBCR td{padding-top:" + ("0.1em")  + ";}.GOE-WOTBCR button{font-size:" + ("16px")  + ";font-weight:" + ("bolder")  + ";}.boxSpacer{width:" + ("0.5em")  + " !important;}.GOE-WOTBDR{background-color:" + ("red")  + ";}.GOE-WOTBER{font-size:") + (("8px")  + ";}"));
      }
      public java.lang.String boxSpacer(){
        return "boxSpacer";
      }
      public java.lang.String homePanel(){
        return "GOE-WOTBCR";
      }
      public java.lang.String playBtn(){
        return "GOE-WOTBDR";
      }
      public java.lang.String verLbl(){
        return "GOE-WOTBER";
      }
    }
    ;
  }
  private static class styleInitializer {
    static {
      _instance0.styleInitializer();
    }
    static it.mate.abaco.client.view.AbacoView_ViewUiBinderImpl_GenCss_style get() {
      return style;
    }
  }
  public it.mate.abaco.client.view.AbacoView_ViewUiBinderImpl_GenCss_style style() {
    return styleInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static it.mate.abaco.client.view.AbacoView_ViewUiBinderImpl_GenCss_style style;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      style(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("style", style());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'style': return this.@it.mate.abaco.client.view.AbacoView_ViewUiBinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

package it.mate.phgcommons.client.plugins;

public class KeyboardPlugin {
  
  public static native boolean isInstalled () /*-{
    return typeof ($wnd.Keyboard) != 'undefined';
  }-*/;
  
  protected static void hideFormAccessoryBar(boolean flag) {
    hideFormAccessoryBarImpl(flag);
  }
  
  protected static native void hideFormAccessoryBarImpl(boolean flag) /*-{
    $wnd.Keyboard.hideFormAccessoryBar(flag);
  }-*/;

}

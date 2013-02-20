package it.mate.gpg.client.utils;


public class IPhoneScrollPatch {
  
  public static void apply() {
    applyImpl();
  }
  
  private static native void applyImpl() /*-{
    $wnd.$(':input').bind("blur", function(e) {
      $wnd.scrollTo(0,0);
      $doc.scrollTop(0);
    });;
  }-*/;
  
  
  /*
      $wnd.alert('blur');

   */

}

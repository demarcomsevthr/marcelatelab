package it.mate.phgcommons.client.plugins;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;


public class PushPlugin {
  
  /**
   *  DOCUMENTATION
   *  

      > PLUGIN >> https://github.com/phonegap/phonegap-plugin-push
      
        >> IDS PER GCM: si ottengono dalla Google Developer Console
            >> Sender ID = Project ID
            >> Server Key
      
        > WORKFLOW GCM:
          1 > device si registra su GCM inviando Sender ID (project id)
          2 > GCM invia a device Registration ID (onNotification callback)
          3 > device invia al server il RegId, il server lo memorizza nei dati account
          4 > server invia messaggio a GCM con RegId e Server Key
          5 > device riceve notifica in onNotification callback
      
        > ESEMPIO COMPLETO
          > http://www.androidhive.info/2012/10/android-push-notifications-using-google-cloud-messaging-gcm-php-and-mysql/  (OLD)
          > http://www.androidhive.info/2016/02/android-push-notifications-using-gcm-php-mysql-realtime-chat-app-part-2/  (NEW)
            >>> esempio completo in android / php (http)
            >>> come configurare Android Emulator (in fondo)

        > DOCUMENTAZIONE GCM:
          > GCM Overview >> https://developers.google.com/cloud-messaging/gcm
          > GCM Getting Started >> https://developers.google.com/cloud-messaging/android/start
          > GCM Implementing Server >> https://developers.google.com/cloud-messaging/server
          > GCM HTTP Server Implementation (esempio con appengine) >> https://developers.google.com/cloud-messaging/http
          

   *  
   *  
   */
  
  public static native boolean isInstalled () /*-{
    return typeof ($wnd.PushNotification) != 'undefined';
  }-*/;

  public static void register(String senderId, final Delegate<PushNotification> delegate) {
    if (OsDetectionUtils.isAndroid()) {
      PhgUtils.log("Push Plugin - registering android with " + senderId);
      registerAndroidImpl(senderId, new JSOCallback() {
        public void handle(JavaScriptObject e) {
          PhgUtils.log("Push Plugin - Registration callback receive: " + JSONUtils.stringify(e));
          delegate.execute(parseNotificationEvent(PushNotification.REGISTRATION_EVENT_NAME, e));
        } 
      }, new JSOCallback() {
        public void handle(JavaScriptObject e) {
          PhgUtils.log("Push Plugin - Notification callback receive: " + JSONUtils.stringify(e));
          delegate.execute(parseNotificationEvent(PushNotification.NOTIFICATION_EVENT_NAME, e));
        }
      }, new JSOCallback() {
        public void handle(JavaScriptObject e) {
          PhgUtils.log("Push Plugin - Error callback receive: " + JSONUtils.stringify(e));
          delegate.execute(parseNotificationEvent(PushNotification.ERROR_EVENT_NAME, e));
        }
      });
    } else {
      PhgUtils.log("Push Plugin - registering ios");
      registerIosImpl();
    }
  }
  
  private static PushNotification parseNotificationEvent(String eventName,JavaScriptObject data) {
    PushNotification notification = new PushNotification();
    notification.setEventName(eventName);
    if (notification.isRegistrationEvent()) {
      String regId = GwtUtils.getJsPropertyString(data, "registrationId");
      notification.setRegId(regId);
    } else if (notification.isNotificationEvent()) {
      String message = GwtUtils.getJsPropertyString(data, "message");
      notification.setMessage(message);
    } else if (notification.isErrorEvent()) {
      String message = GwtUtils.getJsPropertyString(data, "message");
      notification.setMessage(message);
    }
    return notification;
  }
  
  private static native void registerAndroidImpl(String senderId, JSOCallback registrationCallback, JSOCallback notificationCallback, JSOCallback errorCallback) /*-{
    $wnd._push = $wnd.PushNotification.init({
        android: {
            senderID: senderId
        },
        ios: {},
        windows: {}
    });
    var jsRegistrationHandler = $entry(function(data) {
      registrationCallback.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(data);
    });
    var jsNotificationHandler = $entry(function(data) {
      notificationCallback.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(data);
    });
    var jsErrorHandler = $entry(function(e) {
      errorCallback.@it.mate.phgcommons.client.utils.callbacks.JSOSuccess::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
    });
    
    $wnd._push.on('error', jsErrorHandler);
    $wnd._push.on('registration', jsRegistrationHandler);
    $wnd._push.on('notification', jsNotificationHandler);
    
  }-*/;
  
  private static native void registerIosImpl() /*-{
    
  }-*/;
  
}

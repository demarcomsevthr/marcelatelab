package it.mate.gwtcommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.user.client.ui.Widget;

public class MessageBoxUtils {
  
  public static void popupOkCancel (String title, Widget bodyWidget, String width, final Delegate<MessageBox.Callbacks> delegate) {
    MessageBox.create(new MessageBox.Configuration()
    .setCaptionText(title)
    .setButtonType(MessageBox.BUTTONS_OKCANCEL)
    .setIconType(MessageBox.ICON_INFO)
    .setBodyWidget(bodyWidget)
    .setBodyWidth(width)
    .setCallbacks(new MessageBox.Callbacks() {
      public void onOk() {
        delegate.execute(this);
      }
    })
  );
  }

}

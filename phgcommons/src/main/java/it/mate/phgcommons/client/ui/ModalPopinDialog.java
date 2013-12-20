package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.EventUtils;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Panel;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.theme.base.DialogCss;

public class ModalPopinDialog extends PopinDialog {

  private HandlerRegistration modalHandlerRegistration;
  
  public ModalPopinDialog() {
    super();
  }

  public ModalPopinDialog(DialogCss css) {
    super(css);
  }
  
  @Override
  public void show() {
    super.show();
    makeModal();
  }
  
  @Override
  public void hide() {
    super.hide();
    EventUtils.removeModalHandler(modalHandlerRegistration);
  }
  
  private void makeModal() {
    if (modalHandlerRegistration == null) {
      EventUtils.createModalHandler(new EventUtils.PanelGetter() {
        public Panel getPanel() {
          return ModalPopinDialog.this.container;
        }
      }, new Delegate<HandlerRegistration>() {
        public void execute(HandlerRegistration registration) {
          modalHandlerRegistration = registration;
        }
      });
    }
  }

}

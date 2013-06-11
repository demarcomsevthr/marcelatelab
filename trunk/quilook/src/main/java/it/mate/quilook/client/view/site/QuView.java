package it.mate.quilook.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.ui.IFrame;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.quilook.client.factories.SiteGinjector;
import it.mate.quilook.shared.services.QuServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class QuView extends Composite {

  public interface ViewUiBinder extends UiBinder<Widget, QuView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  QuServiceAsync quService = AppClientFactory.IMPL.castGinjector(SiteGinjector.class).getQuService();
  
  public QuView() {
    initUI();
  }

  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  
  @UiHandler ("rpcTestBtn")
  public void rpcTestBtn(ClickEvent event) {
    quService.getQuMessage(new AsyncCallback<String>() {
      public void onSuccess(String result) {
        GwtUtils.messageBox(result);
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }
  
  @UiHandler ("restTestBtn")
  public void restTestBtn(ClickEvent event) {
    if (getWidget() instanceof Panel) {
      Panel panel = (Panel)getWidget();
      panel.add(new IFrame(UriUtils.fromTrustedString("/re/quTest")));
    }
  }
  
}

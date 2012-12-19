package it.mate.econyx.client.factories;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.client.view.PortalUserEditView.Presenter;
import it.mate.econyx.client.view.admin.PortalUserEditContoView;
import it.mate.econyx.client.view.site.ContoUtenteView;
import it.mate.econyx.client.view.site.PortalPageMenuViewCustomer;
import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.services.CustomService;
import it.mate.econyx.shared.services.CustomServiceAsync;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


@SuppressWarnings("serial")
public class CustomClientFactory extends DefaultCustomClientFactory {
  
  private transient CustomServiceAsync customService = null;
  
  public String getCustomName() {
    return "gaspar8";
  }
  
  @Override
  public PortalPageMenuViewCustomer getPortalPageMenuViewCustomizer() {
    return new PortalPageMenuViewCustomer();
  }

  @Override
  public AdminTabPanel.Section<PortalUserEditView.Presenter> getCustomPortalUserEditSection() {
    return new AdminTabPanel.Section<Presenter>()
        .setText("Prepagato")
        .setView(new PortalUserEditContoView());
  }

  public void findContoUtenteByPortalUser(String portalUserId, final Delegate<ContoUtente> delegate) {
    getCustomService().findContoUtenteByPortalUser(portalUserId, new AsyncCallback<ContoUtente>() {
      public void onSuccess(ContoUtente contoUtente) {
        delegate.execute(contoUtente);
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }
  
  public void updateContoUtente(ContoUtente contoUtente, final Delegate<ContoUtente> delegate) {
    getCustomService().updateContoUtente(contoUtente, new AsyncCallback<ContoUtente>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(ContoUtente result) {
        delegate.execute(result);
      }
    });
  }
  
  private CustomServiceAsync getCustomService() {
    if (this.customService == null) {
      this.customService = GWT.create(CustomService.class);
      // TODO:
      // devo rispecificare qui la url del servizio altrimenti quella cazzo 
      // di RemoteServiceRelativePath ci mette il path del modulo gwt davanti !!!! 
      ((ServiceDefTarget)this.customService).setServiceEntryPoint("/springGwtServices/customService");
    }
    return customService;
  }
  
  @Override
  public AbstractBaseView getCustomProfileView1() {
    return new ContoUtenteView("500px");
  }

}

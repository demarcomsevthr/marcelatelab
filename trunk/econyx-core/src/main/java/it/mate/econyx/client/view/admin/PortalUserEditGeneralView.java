package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.PortalUserEditor;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserEditGeneralView extends AbstractAdminTabPage<PortalUserEditView.Presenter> implements PortalUserEditView, IsAdminTabPage<PortalUserEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField PortalUserEditor editor;
  
  private PortalUser user;
  
  public PortalUserEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
  }
  
  public void setModel(Object user, String tag) {
    this.user = (PortalUser)user;
    editor.setModel(this.user);
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    PortalUser editedInstance = editor.flushModel();
    PortalUser user = (PortalUser)model;
    user.setScreenName(editedInstance.getScreenName());
    user.setEmailAddress(editedInstance.getEmailAddress());
    user.setBillingAccount(editedInstance.getBillingAccount());
    delegate.execute(user);
  }

  @UiHandler ("registrationMailBtn")
  public void onRegistrationMailBtn (ClickEvent event) {
    getPresenter().sendActivationMail(user);
  }
  
}

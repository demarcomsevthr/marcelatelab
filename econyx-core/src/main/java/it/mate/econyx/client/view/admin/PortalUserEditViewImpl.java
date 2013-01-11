package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserEditViewImpl extends AbstractBaseView<PortalUserEditView.Presenter> implements PortalUserEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) AdminTabPanel<PortalUserEditView.Presenter> adminTab;
  
  private PortalUser portalUser;
  
  public PortalUserEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new PortalUserEditGeneralView()));
    
    AdminTabPanel.Section<PortalUserEditView.Presenter> customSection = AppClientFactory.Customizer.cast().getCustomPortalUserEditSection();
    if (customSection != null) {
      sections.add(customSection);
    }
    
    if (PropertiesHolder.getStringList("shared.portalUserAdmins", "[\\|,;]").contains(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser().getEmailAddress())) {
      adminTab.addButton(new Button("Cambio password utente", new ClickHandler() {
        public void onClick(ClickEvent event) {
          onCambioPasswordBtn(event);
        }
      }));
    }
    
    adminTab.setSections(sections);
  }
  
  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) { 
        getPresenter().update((PortalUser)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof PortalUser) {
      this.portalUser = (PortalUser)model;
    }
    adminTab.setModel(model, null);
  }
  
  
  private void onCambioPasswordBtn (ClickEvent event) {
    
    VerticalPanel popupPanel = new VerticalPanel();
    
    final PasswordTextBox passwordAttualeBox = new PasswordTextBox();
    popupPanel.add(createPopupPanelItem("Password attuale:", passwordAttualeBox));
    
    final PasswordTextBox nuovaPasswordBox = new PasswordTextBox();
    popupPanel.add(createPopupPanelItem("Nuova password:", nuovaPasswordBox));
    
    final PasswordTextBox confermaPasswordBox = new PasswordTextBox();
    popupPanel.add(createPopupPanelItem("Conferma password:", confermaPasswordBox));
    
    MessageBoxUtils.popupOkCancel("Inserire gli estremi del movimento", popupPanel, "400px", new Delegate<MessageBox.Callbacks> () {
      public void execute(MessageBox.Callbacks callbacks) {
        getPresenter().updatePassword(portalUser, passwordAttualeBox.getValue(), nuovaPasswordBox.getValue(), confermaPasswordBox.getValue());
      }
    });

  }
  
  private Panel createPopupPanelItem(String labelText, Widget box) {
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(new Spacer("1px", "2em"));
    Label label = new Label(labelText);
    label.setWidth("6em");
    panel.add(label);
    panel.add(box);
    return panel;
  }
  
}

package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.PortalUserListView;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.econyx.shared.model.impl.IndirizzoSpedizioneTx;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserListViewImpl extends AbstractBaseView<PortalUserListView.Presenter> implements PortalUserListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<PortalUserListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public PortalUserListViewImpl() {
    this(null, null);
  }
  
  public PortalUserListViewImpl(String width, String height) {
    super();
    this.width = width;
    this.height = height;
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new PortalUserListGeneralView()));
    adminTab.setSections(sections);
  }
  
  public class PortalUserInsertDialog {
    TextBox cognome = new TextBox();
    TextBox nome = new TextBox();
    TextBox email = new TextBox();
    VerticalPanel table = new VerticalPanel();
    public PortalUserInsertDialog(final Delegate<PortalUser> portalUserDelegate) {
      addRow("Cognome:", cognome);
      addRow("Nome:", nome);
      addRow("Email:", email);
      MessageBoxUtils.popupOkCancel("Nuovo Utente", table, "400px", new Delegate<MessageBox.Callbacks>() {
        public void execute(Callbacks callbacks) {
          Customer customer = new CustomerTx();
          customer.setIndirizzoSpedizione(new IndirizzoSpedizioneTx());
          customer.getIndirizzoSpedizione().setCognome(cognome.getText());
          customer.getIndirizzoSpedizione().setNome(nome.getText());
          PortalUser portalUser = new PortalUserTx();
          portalUser.setScreenName(cognome.getText()+" "+nome.getText());
          portalUser.setEmailAddress(email.getText());
          portalUser.setPassword("12345678");
          portalUser.setPasswordEncrypted(false);
          customer.setPortalUser(portalUser);
          getPresenter().createCustomer(customer, new Delegate<Customer>() {
            public void execute(Customer createdCustomer) {
              portalUserDelegate.execute(createdCustomer.getPortalUser());
            }
          });
        }
      }, new Delegate<DialogBox>() {
        public void execute(DialogBox element) {
          cognome.setFocus(true);
        }
      });
    }
    private void addRow(String text, Widget w) {
      HorizontalPanel row;
      row = new HorizontalPanel();
      row.add(new Spacer("1px", "2em"));
      Label label = new Label(text);
      label.setWidth("6em");
      row.add(label);
      row.add(w);
      table.add(row);
    }
  }
  
  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
        // 15/05/2013
        // getPresenter().edit(new PortalUserTx());
        new PortalUserInsertDialog(new Delegate<PortalUser>() {
          public void execute(PortalUser portalUser) {
            getPresenter().edit(portalUser);
          }
        });
      }
    };
    
    Button button = AppClientFactory.Customizer.cast().getPortalUserEditListCustomButton();
    if (button != null) {
      adminTab.addButton(button);
    }
    
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    adminTab.setModel(model, null);
  }
  
}

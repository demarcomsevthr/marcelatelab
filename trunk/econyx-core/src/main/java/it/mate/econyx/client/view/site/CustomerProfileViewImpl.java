package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.CustomerProfileView;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CustomerProfileViewImpl extends AbstractBaseView<CustomerProfileView.Presenter> implements CustomerProfileView {

  public interface ViewUiBinder extends UiBinder<Widget, CustomerProfileViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel updateAddressPanel;
  @UiField Panel customProfileView1Panel;
  @UiField Anchor customProfileView1Btn;
  @UiField Panel inlineProfileView;
  
  public CustomerProfileViewImpl() {
    super();
    initUI();
  }

  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    if (PropertiesHolder.getBoolean("client.CustomerProfileView.customProfileView1Panel.visible")) {
      customProfileView1Panel.setVisible(true);
      customProfileView1Btn.setText(PropertiesHolder.getString("client.CustomerProfileView.customProfileView1Btn.text"));
    }
    if (!PropertiesHolder.getBoolean("client.CustomerProfileView.updateAddressPanel.visible", true)) {
      updateAddressPanel.setVisible(false);
    }
  }

  @Override
  public void setModel(Object model, String tag) {

  }

  @UiHandler ("shoppingCartBtn")
  public void onShoppingCartBtn(ClickEvent event) {
    getPresenter().goToShoppingCartView();
  }
  
  @UiHandler ("updateBtn")
  public void onUpdateBtn(ClickEvent event) {
    getPresenter().goToUpdateCustomerInformations();
  }
  
  @UiHandler ("viewOrderBtn")
  public void onViewOrderBtn(ClickEvent event) {
//  getPresenter().goToListOrderView();
    inlineProfileView.clear();
    SimplePanel panel = new SimplePanel();
    inlineProfileView.add(panel);
    getPresenter().createOrderListView(panel);
  }

  @UiHandler ("customProfileView1Btn")
  public void onCustomProfileView1Btn (ClickEvent event) {
    AbstractBaseView view = AppClientFactory.Customizer.cast().getCustomProfileView1();
    if (view != null) {
      view.setModel(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
      inlineProfileView.clear();
      inlineProfileView.add(view);
    }
  }
  
}

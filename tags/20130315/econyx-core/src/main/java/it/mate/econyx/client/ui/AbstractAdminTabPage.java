package it.mate.econyx.client.ui;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;

public abstract class AbstractAdminTabPage <P extends BasePresenter> extends AbstractBaseView<P> implements IsAdminTabPage<P> {
  
  private AdminTabPanel adminTabPanel;
  
  public Object getSelectedObject() {
    return null;
  }
  
  public void setAdminTabPanel(AdminTabPanel adminTabPanel) {
    this.adminTabPanel = adminTabPanel;
  }
  
  protected AdminTabPanel getAdminTabPanel() {
    return adminTabPanel;
  }

}

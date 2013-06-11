package it.mate.quilook.client.gwtp.factories;

import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.client.view.PortalPageSummaryView;
import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.client.view.PortalUserListView;
import it.mate.econyx.client.view.PortalUserView;
import it.mate.econyx.client.view.admin.GeneralConfigView;
import it.mate.econyx.client.view.site.PortalPageMenuViewImpl;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.econyx.shared.services.PortalUserServiceAsync;
import it.mate.gwtcommons.client.factories.CommonGinjector;

public interface AppGinjector extends CommonGinjector {
  
  public GeneralServiceAsync getGeneralService();
  public PortalPageServiceAsync getPortalPageService();
  public PortalUserServiceAsync getPortalUserService();
  
  public PortalPageMenuViewImpl getPortalPageListMenuView();
  public PortalPageEditView getPortalPageEditView();
  public PortalPageListView getPortalPageListView();
  public PortalPageView getPortalPageView();
  public PortalPageExplorerView getPortalPageExplorerView();
  public PortalPageSummaryView getPortalPageSummaryView();
  
  public PortalUserView getPortalUserView();
  public PortalUserListView getPortalUserListView();
  public PortalUserEditView getPortalUserEditView();
  
  public GeneralConfigView getGeneralConfigView();
  
}

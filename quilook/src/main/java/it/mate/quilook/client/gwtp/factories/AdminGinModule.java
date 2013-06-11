package it.mate.quilook.client.gwtp.factories;

import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.client.view.PortalPageSummaryView;
import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.client.view.PortalUserListView;
import it.mate.econyx.client.view.PortalUserView;
import it.mate.econyx.client.view.admin.PortalPageEditViewImpl;
import it.mate.econyx.client.view.admin.PortalPageListViewImpl;
import it.mate.econyx.client.view.admin.PortalUserEditViewImpl;
import it.mate.econyx.client.view.admin.PortalUserListViewImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class AdminGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    
    bind(PortalPageEditView.class).to(PortalPageEditViewImpl.class);
    bind(PortalPageListView.class).to(PortalPageListViewImpl.class);
    bind(PortalPageView.class).to(PortalPageView.NotImpl.class);
    bind(PortalPageExplorerView.class).to(PortalPageExplorerView.NotImpl.class);
    bind(PortalPageSummaryView.class).to(PortalPageSummaryView.NotImpl.class);
    
    bind(PortalUserView.class).to(PortalUserView.NotImpl.class);
    bind(PortalUserListView.class).to(PortalUserListViewImpl.class);
    bind(PortalUserEditView.class).to(PortalUserEditViewImpl.class);
    
  }
  
}

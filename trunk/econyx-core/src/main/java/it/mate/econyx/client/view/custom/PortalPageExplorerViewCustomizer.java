package it.mate.econyx.client.view.custom;

import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageExplorerView.TreeModel;

import com.google.gwt.user.client.ui.Panel;

public interface PortalPageExplorerViewCustomizer {
  
  public void setPresenter(PortalPageExplorerView.Presenter presenter);
  
  public void applyModelOnPanel (TreeModel treeModel, Panel panel);
  
}
